package com.xxmicloxx.ipaint;

import com.luronix.core.Core;
import com.xxmicloxx.ipaint.arena.ArenaCommandListener;
import org.bukkit.entity.Player;
import org.sk89q.Command;
import org.sk89q.CommandContext;
import org.sk89q.NestedCommand;

/**
 * Created with IntelliJ IDEA.
 * User: Mic
 * Date: 15.11.13
 * Time: 15:32
 */
public class IPaintCommandHandler {
    @Command(aliases = {"arenas", "arena"}, desc  = "Arenas subcommand")
    @NestedCommand(value = ArenaCommandListener.class)
    public void handleArenasCommand(CommandContext context, Player p) {}
}
