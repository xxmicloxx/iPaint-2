package com.xxmicloxx.ipaint;

import com.luronix.core.Core;
import com.xxmicloxx.ipaint.arena.Arena;
import com.xxmicloxx.ipaint.arena.ArenaPlayerContainer;
import com.xxmicloxx.ipaint.arena.ArenaPlayerContainerSingle;
import com.xxmicloxx.ipaint.i180n.I18n;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import java.io.*;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: Mic
 * Date: 14.11.13
 * Time: 20:59
 */
public class MainPlugin {
    private static MainPlugin instance;
    public static MainPlugin getInstance() {
        if (instance == null) {
            instance = new MainPlugin();
        }
        return instance;
    }

    private Config config;

    public void start() {
        if (Bukkit.getPluginManager().getPlugin("hCore") == null) {
            // not found
            Bukkit.getLogger().log(Level.WARNING, "No hCore found, downloading for you now...");
            //TODO download
        }
        registerConfig();
        Core.registerCommands(MainCommandHandler.class);
        Bukkit.getPluginManager().registerEvents(new IPaintPlayerListener(), IPaint.getInstance());
        checkConfig();
        I18n.i18n().reloadLanguage(config.getLanguage());
    }

    @SuppressWarnings("unchecked")
    private void registerConfig() {
        Class<? extends ConfigurationSerializable> classes[] = new Class[]{Config.class, Arena.class,
                ArenaPlayerContainerSingle.class, SerializableLocation.class};
        for (Class<? extends ConfigurationSerializable> c : classes) {
            ConfigurationSerialization.registerClass(c);
        }
    }

    private void checkConfig() {
        if (!IPaint.getInstance().getConfig().isSet("config")) {
            System.out.println("Creating config...");
            config = new Config();
        } else {
            try {
                config = (Config) IPaint.getInstance().getConfig().get("config");
            } catch (Exception e) {
                IPaint.getInstance().getLogger().log(Level.SEVERE, "You have an invalid config file, recreating...");
                config = new Config();
            }
        }
        saveConfig();
    }

    public void saveConfig() {
        IPaint.getInstance().getConfig().set("config", config);
        IPaint.getInstance().saveConfig();
    }

    public void stop() {
    }

    public Config getConfig() {
        return config;
    }
}
