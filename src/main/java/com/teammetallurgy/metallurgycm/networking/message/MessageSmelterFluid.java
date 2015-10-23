package com.teammetallurgy.metallurgycm.networking.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;

import com.teammetallurgy.metallurgycm.tileentity.TileEntitySmelter;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSmelterFluid implements IMessage, IMessageHandler<MessageSmelterFluid, IMessage>
{

    private int x;
    private int y;
    private int z;
    private int amount;

    public MessageSmelterFluid()
    {

    }

    public MessageSmelterFluid(TileEntitySmelter tileEntity, int fluidAmount)
    {
        x = tileEntity.xCoord;
        y = tileEntity.yCoord;
        z = tileEntity.zCoord;

        amount = fluidAmount;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();

        amount = buf.readInt();

    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);

        buf.writeInt(amount);

    }

    @Override
    public IMessage onMessage(MessageSmelterFluid message, MessageContext ctx)
    {
        WorldClient world = FMLClientHandler.instance().getWorldClient();

        TileEntity tileEntity = world.getTileEntity(message.x, message.y, message.z);
        if (tileEntity instanceof TileEntitySmelter)
        {
            ((TileEntitySmelter) tileEntity).fluidLevel = message.amount;
            world.markBlockForUpdate(message.x, message.y, message.z);
        }

        return null;
    }

}
