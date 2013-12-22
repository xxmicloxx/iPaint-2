package com.xxmicloxx.ipaint;

import com.luronix.core.Core;
import org.bukkit.entity.Player;
import org.sk89q.Command;
import org.sk89q.CommandContext;
import org.sk89q.NestedCommand;

/**
 * Created with IntelliJ IDEA.
 * User: Mic
 * Date: 14.11.13
 * Time: 20:54
 */
public class MainCommandHandler {
    @Command(aliases = {"ipaint", "pb", "paintball"}, desc = "Main iPaint command")
    @NestedCommand(IPaintCommandHandler.class)
    public void handleIPaintCommand(CommandContext context, Player p) {}
}
