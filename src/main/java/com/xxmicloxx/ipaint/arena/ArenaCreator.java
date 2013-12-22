package com.xxmicloxx.ipaint.arena;

import com.luronix.core.Core;
import com.xxmicloxx.ipaint.IPaint;
import com.xxmicloxx.ipaint.IPaintPlayer;
import com.xxmicloxx.ipaint.MainPlugin;
import com.xxmicloxx.ipaint.i180n.I18n;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Mic
 * Date: 15.11.13
 * Time: 16:28
 */
public class ArenaCreator implements Listener {
    private IPaintPlayer player;
    private int currentStep = 0;
    private HashSet<Block> blinkingBlocks = new HashSet<Block>();
    private Block firstBlock = null;
    private int currentBlinkIndex = 0;
    private int blinkSchedulerId = -1;
    private Block secondBlock;
    private Block lesserBlock;
    private Block greaterBlock;
    private String name;

    public ArenaCreator(IPaintPlayer player) {
        this.player = player;
        Bukkit.getPluginManager().registerEvents(this, IPaint.getInstance());
        start();
    }

    private void refreshBlinkStatus() {
        Player p = player.getPlayer();
        for (Block b : blinkingBlocks) {
            if (b.getY() % 10 == currentBlinkIndex) {
                p.sendBlockChange(b.getLocation(), Material.REDSTONE_LAMP_OFF, (byte) 0);
            } else {
                p.sendBlockChange(b.getLocation(), Material.GLASS, (byte) 0);
            }
        }
    }

    private void start() {
        player.sendI18nMessage("arena.creator.start");
        blinkSchedulerId = Bukkit.getScheduler().scheduleSyncRepeatingTask(IPaint.getInstance(), new Runnable() {
            @Override
            public void run() {
                Player p = player.getPlayer();
                int lastBlinkIndex = currentBlinkIndex-1;
                if (lastBlinkIndex < 0) {
                    lastBlinkIndex += 10;
                }
                for (Block b : blinkingBlocks) {
                    if (b.getY() % 10 == currentBlinkIndex) {
                        p.sendBlockChange(b.getLocation(), Material.STAINED_CLAY, (byte) 14);
                    } else if (b.getY() % 10 == lastBlinkIndex) {
                        p.sendBlockChange(b.getLocation(), Material.GLASS, (byte) 0);
                    }
                }
                currentBlinkIndex++;
                if (currentBlinkIndex >= 10) {
                    currentBlinkIndex -= 10;
                }
            }
        }, 5, 5);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getPlayer() != player.getPlayer()) {
            return;
        }
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
        if (currentStep == 0) {
            World world = null;
            if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                firstBlock = e.getClickedBlock();
                world = firstBlock.getWorld();
            } else {
                secondBlock = e.getClickedBlock();
                world = secondBlock.getWorld();
            }
            e.setCancelled(true);
            clearBlocks();
            if (firstBlock != null && secondBlock != null) {
                lesserBlock = world.getBlockAt(Math.min(firstBlock.getX(), secondBlock.getX()),
                        Math.min(firstBlock.getY(), secondBlock.getY()),
                        Math.min(firstBlock.getZ(), secondBlock.getZ()));
                greaterBlock = world.getBlockAt(Math.max(firstBlock.getX(), secondBlock.getX()),
                        Math.max(firstBlock.getY(), secondBlock.getY()),
                        Math.max(firstBlock.getZ(), secondBlock.getZ()));
            }
            for (int y = 0; y <= world.getMaxHeight(); y++) {
                if (firstBlock != null && secondBlock != null) {
                    blinkingBlocks.add(world.getBlockAt(lesserBlock.getX(), y, lesserBlock.getZ()));
                    blinkingBlocks.add(world.getBlockAt(greaterBlock.getX(), y, lesserBlock.getZ()));
                    blinkingBlocks.add(world.getBlockAt(greaterBlock.getX(), y, greaterBlock.getZ()));
                    blinkingBlocks.add(world.getBlockAt(lesserBlock.getX(), y, greaterBlock.getZ()));
                } else if (firstBlock != null) {
                    blinkingBlocks.add(world.getBlockAt(firstBlock.getX(), y, firstBlock.getZ()));
                } else if (secondBlock != null) {
                    blinkingBlocks.add(world.getBlockAt(secondBlock.getX(), y, secondBlock.getZ()));
                }
            }
            refreshBlinkStatus();
        }
    }

    private void clearBlocks() {
        Player p = player.getPlayer();
        for (Block b : blinkingBlocks) {
            p.sendBlockChange(b.getLocation(), b.getType(), b.getData());
        }
        blinkingBlocks.clear();
    }

    @EventHandler
    public void onPlayerChatStep1(PlayerChatEvent e) {
        if (currentStep != 0) {
            return;
        }
        if (e.getPlayer() != player.getPlayer()) {
            return;
        }
        if (e.getMessage().startsWith("/")) {
            return;
        }
        e.setCancelled(true);
        name = e.getMessage();
        if (name.contains(" ")) {
            //error
            player.sendI18nMessage("arena.creator.error.nospaces");
            return;
        }
        if (MainPlugin.getInstance().getConfig().hasArena(name)) {
            player.sendI18nMessage("arena.creator.error.nameset");
            return;
        }
        String message = I18n.i18n().get("arena.creator.nameset", new String[]{"{name}", name});
        Core.broadcastPlayer(player.getPlayer(), message);
        currentStep = 1;
        player.sendI18nMessage("arena.creator.step2");
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerChatStep2(PlayerChatEvent e) {
        if (currentStep != 1) {
            return;
        }
        if (e.getPlayer() != player.getPlayer()) {
            return;
        }
        if (e.getMessage().startsWith("/")) {
            return;
        }
        e.setCancelled(true);
        String entered = e.getMessage();
        String shouldBe = I18n.i18n().get("arena.creator.createData");
        if (entered.equalsIgnoreCase(shouldBe)) {
            if (MainPlugin.getInstance().getConfig().hasArena(name)) {
                player.sendI18nMessage("arena.creator.error.nameset");
                cancel();
                return;
            }
            Arena arena = new Arena();
            arena.setFirstBlock(lesserBlock.getLocation());
            arena.setLastBlock(greaterBlock.getLocation());
            arena.setContainer(new ArenaPlayerContainerSingle());
            arena.setName(name);
            MainPlugin.getInstance().getConfig().getArenas().add(arena);
            player.sendI18nMessage("arena.creator.finished");
            MainPlugin.getInstance().saveConfig();
            HandlerList.unregisterAll(this);
            Bukkit.getScheduler().cancelTask(blinkSchedulerId);
            Player p = player.getPlayer();
            for (Block b : blinkingBlocks) {
                p.sendBlockChange(b.getLocation(), b.getType(), b.getData());
            }
            player.setArenaCreator(null);
        } else {
            player.sendI18nMessage("arena.creator.wrongnamedata");
            cancel();
        }
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        if (e.getPlayer() != player.getPlayer()) {
            return;
        }
        cancel();
    }

    @EventHandler
    public void onChangeDimension(PlayerChangedWorldEvent e) {
        if (e.getPlayer() != player.getPlayer()) {
            return;
        }
        cancel();
    }

    @EventHandler
    public void onKicked(PlayerKickEvent e) {
        if (e.getPlayer() != player.getPlayer()) {
            return;
        }
        cancel();
    }

    public void cancel() {
        HandlerList.unregisterAll(this);
        Bukkit.getScheduler().cancelTask(blinkSchedulerId);
        Player p = player.getPlayer();
        for (Block b : blinkingBlocks) {
            p.sendBlockChange(b.getLocation(), b.getType(), b.getData());
        }
        player.setArenaCreator(null);
        player.sendI18nMessage("arena.creator.cancelled");
    }
}
