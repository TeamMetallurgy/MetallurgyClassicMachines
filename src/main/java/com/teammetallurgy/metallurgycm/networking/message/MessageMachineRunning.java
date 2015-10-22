package com.teammetallurgy.metallurgycm.networking.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;

import com.teammetallurgy.metallurgycm.tileentity.TileEntitySmelter;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityStandardMachine;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageMachineRunning implements IMessage, IMessageHandler<MessageMachineRunning, IMessage>
{
    private int x;
    private int y;
    private int z;

    private boolean running;

    public MessageMachineRunning()
    {
        // TODO Auto-generated constructor stub
    }

    public MessageMachineRunning(TileEntityStandardMachine tileEntity)
    {
        x = tileEntity.xCoord;
        y = tileEntity.yCoord;
        z = tileEntity.zCoord;

        running = tileEntity.isRunning();
    }

    public MessageMachineRunning(TileEntitySmelter tileEntity)
    {
        x = tileEntity.xCoord;
        y = tileEntity.yCoord;
        z = tileEntity.zCoord;

        running = tileEntity.isRunning();
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();

        running = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);

        buf.writeBoolean(running);
    }

    @Override
    public IMessage onMessage(MessageMachineRunning message, MessageContext ctx)
    {
        WorldClient world = FMLClientHandler.instance().getWorldClient();
        TileEntity tileEntity = world.getTileEntity(message.x, message.y, message.z);

        if (tileEntity instanceof TileEntityStandardMachine)
        {
            ((TileEntityStandardMachine) tileEntity).setRunning(message.running);
            world.markBlockForUpdate(message.x, message.y, message.z);
        }

        if (tileEntity instanceof TileEntitySmelter)
        {
            ((TileEntitySmelter) tileEntity).setRunning(message.running);
            world.markBlockForUpdate(message.x, message.y, message.z);
        }

        return null;
    }

}
