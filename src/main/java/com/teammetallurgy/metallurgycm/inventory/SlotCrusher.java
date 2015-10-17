package com.teammetallurgy.metallurgycm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class SlotCrusher extends SlotFurnace
{

    private EntityPlayer player;
    private int currentStackSize;

    public SlotCrusher(EntityPlayer entityPlayer, IInventory inventory, int slotId, int x, int z)
    {
        super(entityPlayer, inventory, slotId, x, z);
        player = entityPlayer;
    }

    @Override
    protected void onCrafting(ItemStack itemStack, int increment)
    {
        currentStackSize += increment;
    }

    @Override
    protected void onCrafting(ItemStack itemStack)
    {
        itemStack.onCrafting(player.worldObj, player, currentStackSize);

        if (!player.worldObj.isRemote)
        {
            // TODO: give the player experience
        }

        currentStackSize = 0;

    }

}
