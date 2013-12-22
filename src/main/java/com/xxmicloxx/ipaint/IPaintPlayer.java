package com.xxmicloxx.ipaint;

import com.luronix.core.Core;
import com.xxmicloxx.ipaint.arena.ArenaCreator;
import com.xxmicloxx.ipaint.i180n.I18n;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Mic
 * Date: 15.11.13
 * Time: 14:51
 */
public class IPaintPlayer {
    private static HashMap<Player, IPaintPlayer> players = new HashMap<Player, IPaintPlayer>();

    public static IPaintPlayer getPlayer(Player p) {
        return players.get(p);
    }

    public static void addPlayer(Player p) {
        if (players.containsKey(p)) {
            return;
        }
        players.put(p, new IPaintPlayer(p));
    }

    public static void removePlayer(Player p) {
        if (!players.containsKey(p)) {
            return;
        }
        players.remove(p);
    }

    private String player;
    private ArenaCreator arenaCreator = null;

    public IPaintPlayer(Player p) {
        this.player = p.getName();
    }

    public boolean isCreatingArena() {
        return arenaCreator != null;
    }

    public ArenaCreator getArenaCreator() {
        return arenaCreator;
    }

    public void createArena() {
        arenaCreator = new ArenaCreator(this);
    }

    public Player getPlayer() {
        return Bukkit.getPlayerExact(player);
    }

    public void sendI18nMessage(String i18n) {
        Core.broadcastPlayer(getPlayer(), I18n.i18n().get(i18n));
    }

    public void setArenaCreator(ArenaCreator arenaCreator) {
        this.arenaCreator = arenaCreator;
    }
}
