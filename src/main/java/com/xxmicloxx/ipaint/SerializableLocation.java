package com.xxmicloxx.ipaint;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ml
 * Date: 21.12.13
 * Time: 21:10
 */
public class SerializableLocation implements ConfigurationSerializable {
    private double x,y,z;
    private String world;

    public SerializableLocation(Location loc) {
        x=loc.getX();
        y=loc.getY();
        z=loc.getZ();
        world=loc.getWorld().getName();
    }

    public SerializableLocation(Map<String, Object> myMap) {
        x = (Double) myMap.get("x");
        y = (Double) myMap.get("y");
        z = (Double) myMap.get("z");
        world = (String) myMap.get("world");
    }

    public Location getLocation() {
        World w = Bukkit.getWorld(world);
        if(w==null)
            return null;
        Location toRet = new Location(w,x,y,z);
        return toRet;
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("x", x);
        result.put("y", y);
        result.put("z", z);
        result.put("world", world);
        return result;
    }
}
