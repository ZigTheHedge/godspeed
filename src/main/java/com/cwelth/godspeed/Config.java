package com.cwelth.godspeed;

import com.cwelth.godspeed.proxy.CommonProxy;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

public class Config {
    public static HashMap<String, Double> blocksAffected = new HashMap<>();

    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            blocksAffected.put("minecraft:grass_path", 0.15D);
            initGeneralConfig(cfg);
        } catch (Exception e1) {
            ModMain.logger.log(Level.WARNING, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
        ConfigCategory general = cfg.getCategory(CATEGORY_GENERAL);
        for(Map.Entry<String, Property> entry : general.entrySet())
        {
            if(!blocksAffected.containsKey(entry.getKey()))
                blocksAffected.put(entry.getKey(), entry.getValue().getDouble());
        }
        for(Map.Entry<String, Double> entry : blocksAffected.entrySet())
        {
            cfg.get(CATEGORY_GENERAL, entry.getKey(), entry.getValue()).getDouble();
        }

    }
}
