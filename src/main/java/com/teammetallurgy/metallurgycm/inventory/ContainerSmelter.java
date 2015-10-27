package com.teammetallurgy.metallurgycm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.teammetallurgy.metallurgycm.networking.NetworkHandler;
import com.teammetallurgy.metallurgycm.networking.message.MessageContainerProperties;
import com.teammetallurgy.metallurgycm.tileentity.TileEntitySmelter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerSmelter extends Container
{
    private TileEntitySmelter tileEntity;

    private int lastProcessingTicks;
    private int lastMaxProcessingTicks;
    private int lastFluidLevel;
    private int lastMaxCapacity;

    public ContainerSmelter(InventoryPlayer inventoryPlayer, TileEntitySmelter tileEntitySmelter)
    {
        tileEntity = tileEntitySmelter;

        // Input
        addSlotToContainer(new Slot(tileEntity, 0, 36, 34));
        // Output
        addSlotToContainer(new SlotFurnace(inventoryPlayer.player, tileEntity, 1, 96, 34));

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

    // Shfit-click handling
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotId)
    {
        ItemStack transferedItemStack = null;
        Slot slot = this.getSlot(sourceSlotId);

        if (slot == null || !slot.getHasStack()) { return transferedItemStack; }

        ItemStack slotStack = slot.getStack();
        transferedItemStack = slotStack.copy();

        if (sourceSlotId == 1)
        {
            // source is output slot
            if (!mergeItemStack(slotStack, 2, 38, true)) { return null; }

            slot.onSlotChange(slotStack, transferedItemStack);
        }
        else if (sourceSlotId != 0)
        {
            // source is player inventory
            if (FurnaceRecipes.smelting().getSmeltingResult(slotStack) != null)
            {
                if (!mergeItemStack(slotStack, 0, 1, false)) { return null; }
            }
            else if (sourceSlotId >= 2 && sourceSlotId < 29)
            {
                // source is from player non-hotbar slots
                if (!mergeItemStack(slotStack, 29, 38, false)) { return null; }
            }
            else if (sourceSlotId >= 29 && sourceSlotId < 38)
            {
                // source is from player hotbar slots
                if (!mergeItemStack(slotStack, 2, 29, false)) { return null; }
            }

        }
        else if (!mergeItemStack(slotStack, 2, 38, false)) { return null; }

        if (slotStack.stackSize <= 0)
        {
            slot.putStack(null);
        }
        else
        {
            slot.onSlotChanged();
        }

        if (transferedItemStack.stackSize == slotStack.stackSize) { return null; }

        slot.onPickupFromSlot(player, slotStack);

        return transferedItemStack;
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafter)
    {
        super.addCraftingToCrafters(crafter);

        if (!(crafter instanceof EntityPlayerMP)) { return; }

        EntityPlayerMP player = (EntityPlayerMP) crafter;

        NetworkHandler.CHANNEL.sendTo(new MessageContainerProperties(this.windowId, 0, tileEntity.processingTicks), player);
        NetworkHandler.CHANNEL.sendTo(new MessageContainerProperties(this.windowId, 1, tileEntity.maxProcessingTicks), player);
        NetworkHandler.CHANNEL.sendTo(new MessageContainerProperties(this.windowId, 2, tileEntity.fluidLevel), player);
        NetworkHandler.CHANNEL.sendTo(new MessageContainerProperties(this.windowId, 3, tileEntity.maxCapacity), player);
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

            if (lastFluidLevel != tileEntity.fluidLevel)
            {
                NetworkHandler.CHANNEL.sendTo(new MessageContainerProperties(this.windowId, 2, tileEntity.fluidLevel), player);
            }

            if (lastMaxCapacity != tileEntity.maxCapacity)
            {
                NetworkHandler.CHANNEL.sendTo(new MessageContainerProperties(this.windowId, 3, tileEntity.maxCapacity), player);
            }
        }

        lastProcessingTicks = tileEntity.processingTicks;
        lastMaxProcessingTicks = tileEntity.maxProcessingTicks;
        lastFluidLevel = tileEntity.fluidLevel;
        lastMaxCapacity = tileEntity.maxCapacity;

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
                tileEntity.fluidLevel = value;
                break;
            case 3:
                tileEntity.maxCapacity = value;
        }
    }
}
