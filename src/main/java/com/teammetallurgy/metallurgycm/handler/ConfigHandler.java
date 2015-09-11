package com.teammetallurgy.metallurgycm.handler;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{

    private static Configuration config;

    public static void setConfig(Configuration configuration)
    {
        config = configuration;
    }

    public static void loadConfig()
    {
        config.load();

        if (config.hasChanged()) config.save();
    }
}
