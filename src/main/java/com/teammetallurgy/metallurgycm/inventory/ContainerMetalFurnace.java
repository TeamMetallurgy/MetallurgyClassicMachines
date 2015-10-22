package com.teammetallurgy.metallurgycm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;

import com.teammetallurgy.metallurgycm.networking.NetworkHandler;
import com.teammetallurgy.metallurgycm.networking.message.MessageContainerProperties;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerMetalFurnace extends Container
{
    private TileEntityMetalFurnace tileEntity;
    private int lastProcessingTicks;
    private int lastMaxProcessingTicks;
    private int lastBurningTicks;
    private int lastMaxBurningTicks;

    public ContainerMetalFurnace(InventoryPlayer inventoryPlayer, TileEntityMetalFurnace tileEntityMetalFurnace)
    {
        tileEntity = tileEntityMetalFurnace;

        // Fuel
        addSlotToContainer(new Slot(tileEntityMetalFurnace, 0, 56, 53));
        // Input
        addSlotToContainer(new Slot(tileEntityMetalFurnace, 1, 56, 17));
        // Output
        addSlotToContainer(new SlotFurnace(inventoryPlayer.player, tileEntityMetalFurnace, 2, 116, 35));

        int x = 0;
        int y = 0;

        // Player inventory
        for (y = 0; y < 3; y++)
        {
            for (x = 0; x < 9; x++)
            {
                addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // Player hotbar
        for (x = 0; x < 9; x++)
        {
            addSlotToContainer(new Slot(inventoryPlayer, x, 8 + x * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafter)
    {
        super.addCraftingToCrafters(crafter);

        if (!(crafter instanceof EntityPlayerMP)) { return; }

        EntityPlayerMP player = (EntityPlayerMP) crafter;

        NetworkHandler.CHANNEL.sendTo(new MessageContainerProperties(this.windowId, 0, tileEntity.processingTicks), player);
        NetworkHandler.CHANNEL.sendTo(new MessageContainerProperties(this.windowId, 1, tileEntity.maxProcessingTicks), player);
        NetworkHandler.CHANNEL.sendTo(new MessageContainerProperties(this.windowId, 2, tileEntity.burningTicks), player);
        NetworkHandler.CHANNEL.sendTo(new MessageContainerProperties(this.windowId, 3, tileEntity.maxBurningTicks), player);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < crafters.size(); i++)
        {
            ICrafting crafter = (ICrafting) crafters.get(i);

            if (!(crafter instanceof EntityPlayerMP))
            {
                continue;
            }

            EntityPlayerMP player = (EntityPlayerMP) crafter;

            if (lastProcessingTicks != tileEntity.processingTicks)
            {
                NetworkHandler.CHANNEL.sendTo(new MessageContainerProperties(this.windowId, 0, tileEntity.processingTicks), player);
            }

            if (lastMaxProcessingTicks != tileEntity.maxProcessingTicks)
            {
                NetworkHandler.CHANNEL.sendTo(new MessageContainerProperties(this.windowId, 1, tileEntity.maxProcessingTicks), player);
            }

            if (lastBurningTicks != tileEntity.burningTicks)
            {
                NetworkHandler.CHANNEL.sendTo(new MessageContainerProperties(this.windowId, 2, tileEntity.burningTicks), player);
            }

            if (lastMaxBurningTicks != tileEntity.maxBurningTicks)
            {
                NetworkHandler.CHANNEL.sendTo(new MessageContainerProperties(this.windowId, 3, tileEntity.maxBurningTicks), player);
            }
        }

        lastProcessingTicks = tileEntity.processingTicks;
        lastMaxProcessingTicks = tileEntity.maxProcessingTicks;
        lastBurningTicks = tileEntity.burningTicks;
        lastMaxBurningTicks = tileEntity.maxBurningTicks;

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
    {
        switch (id)
        {
            case 0:
                tileEntity.processingTicks = value;
                break;
            case 1:
                tileEntity.maxProcessingTicks = value;
                break;
            case 2:
                tileEntity.burningTicks = value;
                break;
            case 3:
                tileEntity.maxBurningTicks = value;
        }
    }
}
