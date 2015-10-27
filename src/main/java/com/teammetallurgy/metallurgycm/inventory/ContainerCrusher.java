package com.teammetallurgy.metallurgycm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import com.teammetallurgy.metallurgycm.crafting.RecipesCrusher;
import com.teammetallurgy.metallurgycm.networking.NetworkHandler;
import com.teammetallurgy.metallurgycm.networking.message.MessageContainerProperties;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityCrusher;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerCrusher extends Container
{
    private TileEntityCrusher tileEntity;
    private int lastProcessingTicks;
    private int lastMaxProcessingTicks;
    private int lastBurningTicks;
    private int lastMaxBurningTicks;

    public ContainerCrusher(InventoryPlayer inventoryPlayer, TileEntityCrusher tileEntityCrusher)
    {
        tileEntity = tileEntityCrusher;

        int x = 0;
        int y = 0;

        // Fuel
        addSlotToContainer(new Slot(tileEntity, 0, 56, 53));
        // Input
        addSlotToContainer(new Slot(tileEntity, 1, 56, 17));
        // Output
        addSlotToContainer(new SlotCrusher(inventoryPlayer.player, tileEntity, 2, 116, 35));

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

        if (sourceSlotId == 2)
        {
            // source is output slot
            if (!mergeItemStack(slotStack, 3, 39, true)) { return null; }

            slot.onSlotChange(slotStack, transferedItemStack);
        }
        else if (sourceSlotId != 0 && sourceSlotId != 1)
        {
            // source is player inventory
            if (RecipesCrusher.getResult(slotStack) != null)
            {
                if (!mergeItemStack(slotStack, 1, 2, false)) { return null; }
            }
            else if (TileEntityFurnace.getItemBurnTime(slotStack) > 0)
            {
                if (!mergeItemStack(slotStack, 0, 1, false)) { return null; }
            }
            else if (sourceSlotId >= 3 && sourceSlotId < 30)
            {
                // source is from player non-hotbar slots
                if (!mergeItemStack(slotStack, 30, 39, false)) { return null; }
            }
            else if (sourceSlotId >= 30 && sourceSlotId < 39)
            {
                // source is from player hotbar slots
                if (!mergeItemStack(slotStack, 3, 30, false)) { return null; }
            }

        }
        else if (!mergeItemStack(slotStack, 3, 39, false)) { return null; }

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
