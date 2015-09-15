package com.teammetallurgy.metallurgycm.proxy;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.handler.MetallurgyCMGuiHandler;

import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy implements IProxy
{

    @Override
    public void init()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(MetallurgyCM.MOD_ID, new MetallurgyCMGuiHandler());
    }

}
