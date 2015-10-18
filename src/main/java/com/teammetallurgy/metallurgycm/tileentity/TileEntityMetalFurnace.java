package com.teammetallurgy.metallurgycm.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityMetalFurnace extends TileEntityStandardMachine
{

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        boolean burning = burningTicks > 0;
        boolean requiresUpdate = false;
        maxProcessingTicks = 200;

        if (burning)
        {
            burningTicks--;
        }

        if (worldObj.isRemote) return;

        if (burningTicks != 0 || inventory[0] != null && inventory[1] != null)
        {
            if (burningTicks == 0 && canProcess())
            {
                // Try to start burning

                burningTicks = maxBurningTicks = TileEntityFurnace.getItemBurnTime(inventory[0]);

                if (burningTicks > 0)
                {
                    // Consume fuel
                    requiresUpdate = true;

                    if (inventory[0] != null)
                    {
                        inventory[0].stackSize--;

                        // Replace with container if available, ex. empty bucket
                        if (inventory[0].stackSize == 0)
                        {
                            inventory[0] = inventory[0].getItem().getContainerItem(inventory[0]);
                        }

                    }
                }

            }

            if (currentlyBurning() && canProcess())
            {
                processingTicks++;

                // Process Input
                if (processingTicks >= maxProcessingTicks)
                {
                    processingTicks = 0;
                    processInput();
                    requiresUpdate = true;
                }
            }
            else
            {
                processingTicks = 0;
            }

        }

        if (burning != currentlyBurning())
        {
            requiresUpdate = true;
            // TODO: update rendering state
        }

        if (requiresUpdate) markDirty();
    }

    private boolean canProcess()
    {

        if (inventory[1] == null) return false;

        ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(inventory[1]);
        if (result == null) return false;
        if (inventory[2] == null) return true;
        if (!inventory[2].isItemEqual(result)) return false;
        int totalStackSize = inventory[2].stackSize + result.stackSize;
        return totalStackSize <= getInventoryStackLimit() && totalStackSize <= result.getMaxStackSize();

    }

    private void processInput()
    {
        if (!canProcess()) return;

        ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(inventory[1]);

        if (inventory[2] == null)
        {
            inventory[2] = result.copy();
        }
        else if (inventory[2].isItemEqual(result))
        {
            inventory[2].stackSize += result.stackSize;
        }

        inventory[1].stackSize--;

        if (inventory[1].stackSize <= 0)
        {
            inventory[1] = null;
        }
    }

    private boolean currentlyBurning()
    {
        return burningTicks > 0;
    }
}
