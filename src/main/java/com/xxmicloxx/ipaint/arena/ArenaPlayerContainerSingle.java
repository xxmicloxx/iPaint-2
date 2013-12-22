package com.xxmicloxx.ipaint.arena;

import com.xxmicloxx.ipaint.IPaintPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mic
 * Date: 19.11.13
 * Time: 23:09
 */
public class ArenaPlayerContainerSingle extends ArenaPlayerContainer {
    private transient HashSet<IPaintPlayer> players = new HashSet<IPaintPlayer>();

    public ArenaPlayerContainerSingle() {
    }

    public ArenaPlayerContainerSingle(Map<String, Object> map) {
    }

    @Override
    public ArenaType getType() {
        return ArenaType.SINGLE;
    }

    @Override
    public Map<String, Object> serialize() {
        return new HashMap<String, Object>();
    }
}
