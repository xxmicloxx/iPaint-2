package com.xxmicloxx.ipaint;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created with IntelliJ IDEA.
 * User: Mic
 * Date: 14.11.13
 * Time: 20:51
 */
public class IPaint extends JavaPlugin {
    private static IPaint instance;
    public static IPaint getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        MainPlugin.getInstance().stop();
        instance = null;
    }

    @Override
    public void onEnable() {
        instance = this;
        MainPlugin.getInstance().start();
    }
}
