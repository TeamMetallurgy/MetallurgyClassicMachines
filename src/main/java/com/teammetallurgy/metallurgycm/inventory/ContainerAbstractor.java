package com.teammetallurgy.metallurgycm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import com.teammetallurgy.metallurgycm.tileentity.TileEntityAbstractor;

public class ContainerAbstractor extends Container
{

    public ContainerAbstractor(InventoryPlayer inventoryPlayer, TileEntityAbstractor tileEntityAbstractor)
    {
        // TODO
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

}
