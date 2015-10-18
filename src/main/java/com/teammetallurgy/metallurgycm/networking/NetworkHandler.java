package com.teammetallurgy.metallurgycm.networking;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.networking.message.MessageMachineRunning;

import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class NetworkHandler
{
    public static final SimpleNetworkWrapper CHANNEL = new SimpleNetworkWrapper(MetallurgyCM.MOD_ID);

    public static final int MACHINE_RUNNING_ID = 0;

    public static void init()
    {
        NetworkHandler.CHANNEL.registerMessage(MessageMachineRunning.class, MessageMachineRunning.class, MACHINE_RUNNING_ID, Side.CLIENT);
    }
}
