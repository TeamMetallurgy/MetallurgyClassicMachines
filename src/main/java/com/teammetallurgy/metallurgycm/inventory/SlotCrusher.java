package com.teammetallurgy.metallurgycm.inventory;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgycm.crafting.RecipesCrusher;

public class SlotCrusher extends Slot
{

    private EntityPlayer player;
    private int currentStackSize;

    public SlotCrusher(EntityPlayer entityPlayer, IInventory inventory, int slotId, int x, int z)
    {
        super(inventory, slotId, x, z);
        player = entityPlayer;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack)
    {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int amount)
    {
        if (this.getHasStack())
        {
            currentStackSize += Math.min(amount, this.getStack().stackSize);
        }

        return super.decrStackSize(amount);
    }

    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack itemStack)
    {
        onCrafting(itemStack);
        super.onPickupFromSlot(player, itemStack);
    }

    @Override
    protected void onCrafting(ItemStack itemStack, int increment)
    {
        currentStackSize += increment;
        onCrafting(itemStack);
    }

    @Override
    protected void onCrafting(ItemStack itemStack)
    {
        itemStack.onCrafting(player.worldObj, player, currentStackSize);

        if (!player.worldObj.isRemote)
        {
            int totalEssence = currentStackSize;
            int currentEssence = 0;
            float resultEssence = RecipesCrusher.getExperiance(itemStack);

            if (resultEssence == 0.0F)
            {
                totalEssence = 0;
            }
            else if (resultEssence < 1.0F)
            {
                currentEssence = (int) Math.floor(totalEssence * resultEssence);

                if (currentEssence < Math.ceil(totalEssence * resultEssence) && Math.random() < totalEssence * resultEssence - currentEssence)
                {
                    currentEssence++;
                }

                totalEssence = currentEssence;
            }

            while (totalEssence > 0)
            {
                currentEssence = EntityXPOrb.getXPSplit(totalEssence);
                totalEssence -= currentEssence;
                player.worldObj.spawnEntityInWorld(new EntityXPOrb(player.worldObj, player.posX, player.posY + 0.5D, player.posZ + 0.5D, currentEssence));
            }
        }

        currentStackSize = 0;

    }

}
