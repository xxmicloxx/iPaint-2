package com.xxmicloxx.ipaint;

import com.xxmicloxx.ipaint.arena.Arena;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mic
 * Date: 14.11.13
 * Time: 21:03
 */
public class Config implements ConfigurationSerializable {
    private HashSet<Arena> arenas = new HashSet<Arena>();
    private String language = "en_US";

    public Config() {
    }

    public Config(Map<String, Object> map) {
        language = (String) map.get("language");
        //noinspection unchecked
        ArrayList<Object> objects = (ArrayList<Object>) map.get("arenas");
        for (Object o : objects) {
            arenas.add((Arena) o);
        }
    }

    public boolean hasArena(String name) {
        for (Arena a : arenas) {
            if (a.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public Arena getArena(String name) {
        for (Arena a : arenas) {
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        return null;
    }

    public HashSet<Arena> getArenas() {
        return arenas;
    }

    public void setArenas(HashSet<Arena> arenas) {
        this.arenas = arenas;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("arenas", new ArrayList<Arena>(arenas));
        result.put("language", language);
        return result;
    }
}
