package com.teammetallurgy.metallurgycm.networking;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.networking.message.MessageContainerProperties;
import com.teammetallurgy.metallurgycm.networking.message.MessageMachineRunning;
import com.teammetallurgy.metallurgycm.networking.message.MessageSmelterFluid;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkHandler
{
    public static final SimpleNetworkWrapper CHANNEL = new SimpleNetworkWrapper(MetallurgyCM.MOD_ID);

    public static final int MACHINE_RUNNING_ID = 0;
    public static final int CONTAINER_PROPERTIES = 1;
    public static final int SMELTER_FLUID_ID = 2;

    public static void init()
    {
        NetworkHandler.CHANNEL.registerMessage(MessageMachineRunning.class, MessageMachineRunning.class, MACHINE_RUNNING_ID, Side.CLIENT);
        NetworkHandler.CHANNEL.registerMessage(MessageContainerProperties.class, MessageContainerProperties.class, CONTAINER_PROPERTIES, Side.CLIENT);
        NetworkHandler.CHANNEL.registerMessage(MessageSmelterFluid.class, MessageSmelterFluid.class, SMELTER_FLUID_ID, Side.CLIENT);
    }
}
