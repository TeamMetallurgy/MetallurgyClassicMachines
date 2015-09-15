package com.teammetallurgy.metallurgycm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import com.teammetallurgy.metallurgycm.tileentity.TileEntityCrusher;

public class ContainerCrusher extends Container
{

    public ContainerCrusher(InventoryPlayer inventoryPlayer, TileEntityCrusher tileEntityCrusher)
    {
        // TODO
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

}
