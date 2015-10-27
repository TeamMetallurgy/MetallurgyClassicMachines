package com.teammetallurgy.metallurgycm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalChest;

public class ContainerMetalChest extends Container
{

    private TileEntityMetalChest tileEntity;
    private int[] guiSizeX = { 184, 184, 184, 202, 238 };
    private int[] guiSizeY = { 202, 238, 256, 256, 256 };
    private int[] guiRows = { 6, 8, 9, 9, 9 };
    private int[] guiColumns = { 9, 9, 9, 10, 12 };
    private static final int INV_OFFSET_X = 12;
    private static final int INV_OFFSET_Y = 8;
    private int[] playerInvOffsetX = { 12, 12, 12, 21, 39 };
    private int[] playerInvOffsetY = { 120, 156, 174, 174, 174 };

    public ContainerMetalChest(InventoryPlayer inventoryPlayer, TileEntityMetalChest tileEntityMetalChest)
    {
        tileEntity = tileEntityMetalChest;
        int meta = tileEntity.getBlockMetadata();

        if (meta < 0 || meta >= guiRows.length) return;

        tileEntity.openInventory();

        int x = 0;
        int y = 0;

        // Chest inventory
        for (y = 0; y < guiRows[meta]; y++)
        {
            for (x = 0; x < guiColumns[meta]; x++)
            {
                addSlotToContainer(new Slot(tileEntity, x + y * guiColumns[meta], INV_OFFSET_X + x * 18, INV_OFFSET_Y + y * 18));
            }
        }

        // Player inventory
        for (y = 0; y < 3; y++)
        {
            for (x = 0; x < 9; x++)
            {
                addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, playerInvOffsetX[meta] + x * 18, playerInvOffsetY[meta] + y * 18));
            }
        }

        // Player hotbar
        for (x = 0; x < 9; x++)
        {
            addSlotToContainer(new Slot(inventoryPlayer, x, playerInvOffsetX[meta] + x * 18, playerInvOffsetY[meta] + (18 * 3) + 4));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotId)
    {
        ItemStack transferedStack = null;

        Slot slot = getSlot(sourceSlotId);

        if (slot == null || !slot.getHasStack()) { return null; }

        ItemStack slotStack = slot.getStack();
        transferedStack = slotStack.copy();

        int inventorySize = tileEntity.getSizeInventory();
        int playerInvenotyLimit = inventorySize + 36;

        if (sourceSlotId >= 0 && sourceSlotId < inventorySize)
        {
            // source slot Chest inventory
            if (!mergeItemStack(slotStack, inventorySize, playerInvenotyLimit, true)) { return null; }
        }
        else
        {
            // source slot player inventor
            if (!mergeItemStack(slotStack, 0, inventorySize, false)) { return null; }
        }

        if (slotStack.stackSize <= 0)
        {
            slot.putStack(null);
        }
        else
        {
            slot.onSlotChanged();
        }

        if (transferedStack.stackSize == slotStack.stackSize) { return null; }

        slot.onPickupFromSlot(player, slotStack);

        return transferedStack;
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        tileEntity.closeInventory();
    }

    public TileEntityMetalChest getTileEntity()
    {
        return tileEntity;
    }
}
