package com.teammetallurgy.metallurgycm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

import com.teammetallurgy.metallurgycm.tileentity.TileEntitySmelter;

public class ContainerSmelter extends Container
{
    private TileEntitySmelter tileEntity;

    public ContainerSmelter(InventoryPlayer inventoryPlayer, TileEntitySmelter tileEntitySmelter)
    {
        tileEntity = tileEntitySmelter;

        // Input
        addSlotToContainer(new Slot(tileEntity, 0, 36, 34));
        // Output
        addSlotToContainer(new Slot(tileEntity, 1, 96, 34));

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

}
