package com.teammetallurgy.metallurgycm;

import net.minecraftforge.common.config.Configuration;

import com.teammetallurgy.metallurgycm.handler.ConfigHandler;
import com.teammetallurgy.metallurgycm.handler.LogHandler;
import com.teammetallurgy.metallurgycm.proxy.IProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MetallurgyCM.MOD_ID, name = MetallurgyCM.MOD_NAME, version = MetallurgyCM.VERSION, dependencies = MetallurgyCM.DEPS)
public class MetallurgyCM
{

    public static final String MOD_ID = "metallurgycm";
    public static final String MOD_NAME = "Metallurgy Classic Machines";
    public static final String VERSION = "0.1.0";
    public static final String DEPS = "required-after:Metallurgy";
    public static final String PROXY_PATH = "com.teammetallurgy.metallurgycm.proxy.";

    @Instance(MetallurgyCM.MOD_ID)
    public static MetallurgyCM instance;

    @SidedProxy(modId = MetallurgyCM.MOD_ID, clientSide = PROXY_PATH + "ClientProxy", serverSide = PROXY_PATH + "ServerProxy")
    public static IProxy proxy;

    public static MetallurgyCMTab creativeTab;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigHandler.setConfig(new Configuration(event.getSuggestedConfigurationFile()));
        ConfigHandler.loadConfig();
        LogHandler.setLogger(event.getModLog());

        creativeTab = new MetallurgyCMTab();

        MetallurgyCMBlocks.init();
        MetallurgyCMRecipes.init();

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @EventHandler
    public void imc(IMCEvent event)
    {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

}
