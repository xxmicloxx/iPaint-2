package com.xxmicloxx.ipaint.arena;

import com.xxmicloxx.ipaint.IPaint;
import com.xxmicloxx.ipaint.IPaintPlayer;
import com.xxmicloxx.ipaint.MainPlugin;
import org.bukkit.entity.Player;
import org.sk89q.Command;
import org.sk89q.CommandContext;
import org.sk89q.CommandPermissions;

/**
 * Created with IntelliJ IDEA.
 * User: Mic
 * Date: 15.11.13
 * Time: 16:44
 */
public class ArenaCommandListener {
    @Command(aliases = {"create"}, desc = "Creates an arena", max = 0)
    @CommandPermissions("iPaint.arenas.create")
    public void onCreateArena(CommandContext context, Player p) {
        IPaintPlayer ipp = IPaintPlayer.getPlayer(p);
        if (ipp.isCreatingArena()) {
            ipp.sendI18nMessage("arena.creator.isalreadycreating");
            return;
        }
        ipp.createArena();
    }

    @Command(aliases = {"cancelcreation"}, desc = "Cancels the creation", max = 0)
    public void onCancelCreation(CommandContext context, Player p) {
        IPaintPlayer ipp = IPaintPlayer.getPlayer(p);
        if (!ipp.isCreatingArena()) {
            ipp.sendI18nMessage("arena.creator.isnotcreating");
            return;
        }
        ipp.getArenaCreator().cancel();
    }

    @Command(aliases = {"enable"}, desc = "Enables the arena", max = 1, min = 1)
    @CommandPermissions("iPaint.arenas.enable")
    public void onEnableArena(CommandContext context, Player p) {
        IPaintPlayer ipp = IPaintPlayer.getPlayer(p);
        String arenaStr = context.getString(0);
        Arena arena = MainPlugin.getInstance().getConfig().getArena(arenaStr);
        if (arena == null) {
            ipp.sendI18nMessage("arena.notexists");
            return;
        }
        arena.setEnabled(true);
        MainPlugin.getInstance().saveConfig();
        ipp.sendI18nMessage("arena.enabled");
    }

    @Command(aliases = {"disable"}, desc = "Disables the arena", max = 1, min = 1)
    @CommandPermissions("iPaint.arenas.disable")
    public void onDisableArena(CommandContext context, Player p) {
        IPaintPlayer ipp = IPaintPlayer.getPlayer(p);
        String arenaStr = context.getString(0);
        Arena arena = MainPlugin.getInstance().getConfig().getArena(arenaStr);
        if (arena == null) {
            ipp.sendI18nMessage("arena.notexists");
            return;
        }
        arena.setEnabled(false);
        MainPlugin.getInstance().saveConfig();
        ipp.sendI18nMessage("arena.disabled");
    }

    @Command(aliases = {"delete", "remove"}, desc = "Deletes the arena", max = 1, min = 1)
    @CommandPermissions("iPaint.arenas.delete")
    public void onDeleteArena(CommandContext context, Player p) {
        IPaintPlayer ipp = IPaintPlayer.getPlayer(p);
        String arenaStr = context.getString(0);
        Arena arena = MainPlugin.getInstance().getConfig().getArena(arenaStr);
        if (arena == null) {
            ipp.sendI18nMessage("arena.notexists");
            return;
        }
        arena.setEnabled(false);
        MainPlugin.getInstance().getConfig().getArenas().remove(arena);
        MainPlugin.getInstance().saveConfig();
        ipp.sendI18nMessage("arena.deleted");
    }
}
