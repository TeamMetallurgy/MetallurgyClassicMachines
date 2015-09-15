package com.teammetallurgy.metallurgycm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import com.teammetallurgy.metallurgycm.tileentity.TileEntitySmelter;

public class ContainerSmelter extends Container
{
    public ContainerSmelter(InventoryPlayer inventoryPlayer, TileEntitySmelter tileEntitySmelter)
    {
        // TODO
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

}
