package com.teammetallurgy.metallurgycm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalFurnace;

public class ContainerMetalFurnace extends Container
{

    public ContainerMetalFurnace(InventoryPlayer inventoryPlayer, TileEntityMetalFurnace tileEntityMetalFurnace)
    {
        // TODO
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

}
