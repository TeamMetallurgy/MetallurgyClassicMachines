package com.teammetallurgy.metallurgycm.handler;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{

    private static Configuration config;

    private static String[] abstractorTypes = { "Prometheum", "Deep Iron", "Black Steel", "Oureclase", "Mithril", "Haderoth", "Orichalcum", "Adamantine", "Atlarus", "Tartarite" };
    public static double[] abstractorSpeedMultipliers = {};
    public static double[] abstractorFuelEfficiencyMultipliers = {};

    private static String[] crusherTypes = { "Stone", "Copper", "Bronze", "Iron", "Steel" };
    public static double[] crusherSpeedMultipliers = {};
    public static double[] crusherFuelEfficiencyMultipliers = {};

    private static String[] furnaceTypes = { "Copper", "Bronze", "Iron", "Steel" };
    public static double[] furnaceSpeedMultipliers = {};
    public static double[] furnaceFuelEfficiencyMultipliers = {};

    private static String[] smelterTypes = { "Ignatius", "Shadow Iron", "Shadow Steel", "Vyroxeres", "Inolashite", "Kalendrite", "Vulcanite", "Sanguinite" };
    public static double[] smelterSpeedMultipliers = {};
    public static double[] smelterFuelEfficiencyMultipliers = {};

    public static void setConfig(Configuration configuration)
    {
        config = configuration;
    }

    public static void loadConfig()
    {
        config.load();

        if (config.hasChanged())
        {
            config.save();
        }
    }
}
