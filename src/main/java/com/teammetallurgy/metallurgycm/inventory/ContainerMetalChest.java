package com.teammetallurgy.metallurgycm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalChest;

public class ContainerMetalChest extends Container
{

    public ContainerMetalChest(InventoryPlayer inventoryPlayer, TileEntityMetalChest tileEntityMetalChest)
    {
        // TODO
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

}
