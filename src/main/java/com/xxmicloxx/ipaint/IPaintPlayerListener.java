package com.xxmicloxx.ipaint;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Mic
 * Date: 15.11.13
 * Time: 15:01
 */
public class IPaintPlayerListener implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        IPaintPlayer.removePlayer(e.getPlayer());
    }

    @EventHandler
    public void onPlayerKicked(PlayerKickEvent e) {
        IPaintPlayer.removePlayer(e.getPlayer());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        IPaintPlayer.addPlayer(e.getPlayer());
    }
}
