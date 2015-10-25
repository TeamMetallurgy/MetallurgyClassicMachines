package com.teammetallurgy.metallurgycm.handler;

import java.util.Locale;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler
{

    private static Configuration config;

    private static String[] abstractorTypes = { "Prometheum", "Deep Iron", "Black Steel", "Oureclase", "Mithril", "Haderoth", "Orichalcum", "Adamantine", "Atlarus", "Tartarite" };
    public static double[] abstractorSpeedMultipliers = { 0.5, 1.0, 1.3, 1.5, 1.7, 2.0, 2.2, 2.4, 2.6, 2.8, 3.0 };
    public static double[] abstractorFuelEfficiencyMultipliers = { 3.0, 2.8, 2.6, 2.4, 2.2, 2.0, 1.7, 1.5, 1.3, 1.0, 0.5 };

    private static String[] crusherTypes = { "Stone", "Copper", "Bronze", "Iron", "Steel" };
    public static double[] crusherSpeedMultipliers = { 1.0, 1.5, 2.0, 2.5, 3.0 };
    public static double[] crusherFuelEfficiencyMultipliers = { 3.0, 2.5, 2.0, 1.5, 1.0 };

    private static String[] furnaceTypes = { "Copper", "Bronze", "Iron", "Steel" };
    public static double[] furnaceSpeedMultipliers = { 1.5, 2.0, 2.5, 3.0 };
    public static double[] furnaceFuelEfficiencyMultipliers = { 2.5, 2.0, 1.5, 1.0 };

    private static String[] smelterTypes = { "Ignatius", "Shadow Iron", "Shadow Steel", "Vyroxeres", "Inolashite", "Kalendrite", "Vulcanite", "Sanguinite" };
    public static double[] smelterSpeedMultipliers = { 0.5, 0.8, 1.0, 1.4, 1.6, 2.0, 2.2, 2.5 };
    public static double[] smelterFuelEfficiencyMultipliers = { 2.5, 2.2, 2.0, 1.6, 1.4, 1.0, 0.8, 0.5 };
    public static int[] smelterTankCapacities = { 2000, 4000, 8000, 16000, 32000, 48000, 56000, 64000 };

    public static void setConfig(Configuration configuration)
    {
        config = configuration;
    }

    public static void loadConfig()
    {
        config.load();

        multipleConfigs("abstractor.speed_multipliers", abstractorTypes, abstractorSpeedMultipliers, "Speed Multiplier for %s Abstractor", 0.1D, 10.0D);
        multipleConfigs("abstractor.fuel_efficiency_multipliers", abstractorTypes, abstractorFuelEfficiencyMultipliers, "Fuel Efficiency Multiplier for %s Abstractor", 0.1D, 10.0D);

        multipleConfigs("crusher.speed_multipliers", crusherTypes, crusherSpeedMultipliers, "Speed Multiplier for %s Crusher", 0.1D, 10.0D);
        multipleConfigs("crusher.fuel_efficiency_multipliers", crusherTypes, crusherFuelEfficiencyMultipliers, "Fuel Efficiency Multiplier for %s Crusher", 0.1D, 10.0D);

        multipleConfigs("furnace.speed_multipliers", furnaceTypes, furnaceSpeedMultipliers, "Speed Multiplier for %s Furnance", 0.1D, 10.0D);
        multipleConfigs("furnace.fuel_efficiency_multipliers", furnaceTypes, furnaceFuelEfficiencyMultipliers, "Fuel Efficiency Multiplier for %s Furnance", 0.1D, 10.0D);

        multipleConfigs("smelter.speed_multipliers", smelterTypes, smelterSpeedMultipliers, "Speed Multiplier for %s Smelter", 0.1D, 10.0D);
        multipleConfigs("smelter.fuel_efficiency_multipliers", smelterTypes, smelterFuelEfficiencyMultipliers, "Fuel Efficiency Multiplier for %s Smelter", 0.1D, 10.0D);
        multipleConfigs("smelter.tank_capacities", smelterTypes, smelterTankCapacities, "Tank capacity in mB for %s Smelter", 1, 100000);

        if (config.hasChanged())
        {
            config.save();
        }
    }

    private static void multipleConfigs(String category, String[] types, double[] values, String comment, double min, double max)
    {
        for (int i = 0; i < types.length; i++)
        {
            String type = types[i];
            String key = type.toLowerCase(Locale.US).replace(" ", "_");
            values[i] = config.get(category, key, values[i], String.format(Locale.US, comment, type), min, max).getDouble(values[i]);
        }
    }

    private static void multipleConfigs(String category, String[] types, int[] values, String comment, int min, int max)
    {
        for (int i = 0; i < types.length; i++)
        {
            String type = types[i];
            String key = type.toLowerCase(Locale.US).replace(" ", "_");
            values[i] = config.get(category, key, values[i], String.format(Locale.US, comment, type), min, max).getInt(values[i]);
        }
    }
}
