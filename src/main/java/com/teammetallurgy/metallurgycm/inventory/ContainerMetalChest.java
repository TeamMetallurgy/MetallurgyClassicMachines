package com.teammetallurgy.metallurgycm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalChest;

public class ContainerMetalChest extends Container
{

    private TileEntityMetalChest tileEntity;
    private int[] guiSizeX = { 184, 184, 184, 202, 238 };
    private int[] guiSizeY = { 202, 238, 256, 256, 256 };
    private int[] guiRows = { 6, 8, 9, 9, 9 };
    private int[] guiColumns = { 9, 9, 9, 10, 11 };
    private static final int INV_OFFSET_X = 11;
    private static final int INV_OFFSET_Y = 7;
    private int[] playerInvOffsetX = { 11, 11, 11, 11, 20, 38 };
    private int[] playerInvOffsetY = { 120, 155, 173, 173, 173 };

    public ContainerMetalChest(InventoryPlayer inventoryPlayer, TileEntityMetalChest tileEntityMetalChest)
    {
        tileEntity = tileEntityMetalChest;
        int meta = tileEntity.getBlockMetadata();

        if (meta < 0 || meta >= guiRows.length) return;

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
            addSlotToContainer(new Slot(inventoryPlayer, x, playerInvOffsetX[meta] + x * 18, playerInvOffsetY[meta] + 18 * 3));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

}
