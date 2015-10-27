package com.teammetallurgy.metallurgycm.tileentity;

import java.util.Random;

import net.minecraft.entity.item.EntityXPOrb;

import com.teammetallurgy.metallurgycm.crafting.RecipesAbstractor;
import com.teammetallurgy.metallurgycm.handler.ConfigHandler;
import com.teammetallurgy.metallurgycm.networking.NetworkHandler;
import com.teammetallurgy.metallurgycm.networking.message.MessageMachineRunning;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityAbstractor extends TileEntityStandardMachine
{
    private static Random RAND = new Random();

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        boolean burning = burningTicks > 0;
        boolean requiresUpdate = false;
        maxProcessingTicks = ConfigHandler.abstractorProcessTicks[getType()];

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

                int baseBurningTicks = RecipesAbstractor.getCatalystBurning(inventory[0]);

                if (baseBurningTicks > 0)
                {
                    burningTicks = maxBurningTicks = (int) Math.round((baseBurningTicks * ConfigHandler.abstractorFuelEfficiencyMultipliers[getType()]));

                    if (burningTicks <= 0)
                    {
                        burningTicks = maxBurningTicks = 1;
                    }

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
            setRunning(currentlyBurning());
            TargetPoint point = new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 64.0D);
            NetworkHandler.CHANNEL.sendToAllAround(new MessageMachineRunning(this), point);
            updateLighting();
        }

        if (requiresUpdate) markDirty();
    }

    private boolean canProcess()
    {

        if (inventory[1] == null) return false;

        float essance = RecipesAbstractor.getBaseEssence(inventory[1]);
        if (essance <= 0) return false;
        return true;
    }

    private void processInput()
    {
        if (!canProcess()) return;

        int essance = RecipesAbstractor.getBaseEssence(inventory[1]);

        inventory[1].stackSize--;

        if (inventory[1].stackSize <= 0)
        {
            inventory[1] = null;
        }

        // Spawning Experiance orbs

        while (essance > 0)
        {
            int currentEssence = EntityXPOrb.getXPSplit(essance);
            essance -= currentEssence;

            double centerX = this.xCoord + 0.5D;
            double randomY = this.yCoord + 0.0D + RAND.nextDouble() * 6.0D / 16.0D;
            double centerZ = this.zCoord + 0.5D;
            double faceOffset = 1D;
            double randomOffset = RAND.nextDouble() * 0.6D - 0.3D;

            switch (getFacing())
            {
                case NORTH:
                    worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, centerX + randomOffset, randomY, centerZ - faceOffset, currentEssence));
                    break;
                case SOUTH:
                    worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, centerX + randomOffset, randomY, centerZ + faceOffset, currentEssence));
                    break;
                case WEST:
                    worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, centerX - faceOffset, randomY, centerZ + randomOffset, currentEssence));
                    break;
                case EAST:
                    worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, centerX + faceOffset, randomY, centerZ + randomOffset, currentEssence));
                    break;
                default:

            }

        }

    }
}
