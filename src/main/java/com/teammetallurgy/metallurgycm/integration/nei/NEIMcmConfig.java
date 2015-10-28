package com.teammetallurgy.metallurgycm.integration.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class NEIMcmConfig implements IConfigureNEI
{

    @Override
    public void loadConfig()
    {
        // TODO Add Handlers
        API.registerRecipeHandler(new AbstractorRecipeHandler());
        API.registerUsageHandler(new AbstractorRecipeHandler());

        API.registerRecipeHandler(new CatalystRecipeHandler());
        API.registerUsageHandler(new CatalystRecipeHandler());

        API.registerRecipeHandler(new CrusherRecipeHandler());
        API.registerUsageHandler(new CrusherRecipeHandler());

        API.registerRecipeHandler(new MetalFurnaceRecipeHandler());
        API.registerUsageHandler(new MetalFurnaceRecipeHandler());

        API.registerRecipeHandler(new SmelterRecipeHandler());
        API.registerUsageHandler(new SmelterRecipeHandler());

    }

    @Override
    public String getName()
    {
        return "Metallurgy Classic Machines NEI plugin";
    }

    @Override
    public String getVersion()
    {
        return "0.1.0";
    }

}
