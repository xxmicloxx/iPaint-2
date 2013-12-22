package com.xxmicloxx.ipaint.arena;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * Created with IntelliJ IDEA.
 * User: Mic
 * Date: 19.11.13
 * Time: 23:07
 */
public abstract class ArenaPlayerContainer implements ConfigurationSerializable {
    public abstract ArenaType getType();
}
