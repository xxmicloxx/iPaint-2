package com.xxmicloxx.ipaint.arena;

import com.xxmicloxx.ipaint.IPaintPlayer;
import com.xxmicloxx.ipaint.SerializableLocation;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mic
 * Date: 15.11.13
 * Time: 18:49
 */
public class Arena implements ConfigurationSerializable {
    private SerializableLocation firstBlock;
    private SerializableLocation lastBlock;
    private ArenaPlayerContainer container;
    private transient HashSet<IPaintPlayer> viewers = new HashSet<IPaintPlayer>();
    private String name;
    private boolean enabled = true;

    public Arena() {
    }

    public Arena(Map<String, Object> map) {
        firstBlock = (SerializableLocation) map.get("firstBlock");
        lastBlock = (SerializableLocation) map.get("lastBlock");
        container = (ArenaPlayerContainer) map.get("container");
        name = (String) map.get("name");
        enabled = (Boolean) map.get("enabled");
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getFirstBlock() {
        return firstBlock.getLocation();
    }

    public void setFirstBlock(Location firstBlock) {
        this.firstBlock = new SerializableLocation(firstBlock);
    }

    public Location getLastBlock() {
        return lastBlock.getLocation();
    }

    public void setLastBlock(Location lastBlock) {
        this.lastBlock = new SerializableLocation(lastBlock);
    }

    public ArenaPlayerContainer getContainer() {
        return container;
    }

    public void setContainer(ArenaPlayerContainer container) {
        this.container = container;
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("firstBlock", firstBlock);
        result.put("lastBlock", lastBlock);
        result.put("container", container);
        result.put("name", name);
        result.put("enabled", enabled);
        return result;
    }
}
