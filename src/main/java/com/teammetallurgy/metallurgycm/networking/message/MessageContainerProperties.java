package com.teammetallurgy.metallurgycm.networking.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityClientPlayerMP;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageContainerProperties implements IMessage, IMessageHandler<MessageContainerProperties, IMessage>
{
    private int windowId;
    private int propertyId;
    private int value;

    public MessageContainerProperties()
    {

    }

    public MessageContainerProperties(int windowId, int propertyId, int value)
    {
        this.windowId = windowId;
        this.propertyId = propertyId;
        this.value = value;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        windowId = buf.readUnsignedByte();
        propertyId = buf.readShort();
        value = buf.readInt();

    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(windowId);
        buf.writeShort(propertyId);
        buf.writeInt(value);

    }

    @Override
    public IMessage onMessage(MessageContainerProperties message, MessageContext ctx)
    {
        EntityClientPlayerMP player = FMLClientHandler.instance().getClientPlayerEntity();

        if (player.openContainer != null && player.openContainer.windowId == message.windowId)
        {
            player.openContainer.updateProgressBar(message.propertyId, message.value);
        }

        return null;
    }

}
