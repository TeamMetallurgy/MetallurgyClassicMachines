package com.teammetallurgy.metallurgycm.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.teammetallurgy.metallurgycm.block.BlockMetalFurnace;
import com.teammetallurgy.metallurgycm.handler.ConfigHandler;
import com.teammetallurgy.metallurgycm.networking.NetworkHandler;
import com.teammetallurgy.metallurgycm.networking.message.MessageMachineRunning;
import com.teammetallurgy.metallurgycm.networking.message.MessageSmelterFluid;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntitySmelter extends TileEntityBaseMachine implements ISidedInventory, IFluidHandler
{
    private ItemStack[] inventory = new ItemStack[2];

    public int processingTicks;
    public int maxProcessingTicks;
    private int burningTicks;
    private int maxBurningTicks;
    public int fluidLevel;
    public int maxCapacity;
    private int fluidTicks;

    private boolean running;

    private FluidTank tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 10);

    @Override
    public void setType(int meta)
    {
        super.setType(meta);

        tank = new FluidTank(ConfigHandler.smelterTankCapacities[meta]);
    }

    @Override
    public int getSizeInventory()
    {
        return 2;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if (slot < 0 || slot >= inventory.length) return null;

        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int ammount)
    {
        if (slot < 0 || slot >= inventory.length) return null;

        if (inventory[slot] == null) return null;

        ItemStack stack;

        if (inventory[slot].stackSize <= ammount)
        {
            stack = inventory[slot];
            inventory[slot] = null;
            markDirty();
            return stack;
        }

        stack = inventory[slot].splitStack(ammount);
        if (inventory[slot].stackSize <= 0)
        {
            inventory[slot] = null;
        }
        markDirty();
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (slot < 0 || slot >= inventory.length) return null;

        ItemStack stack = inventory[slot];
        inventory[slot] = null;
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory[slot] = stack;

        if (stack != null && stack.stackSize > getInventoryStackLimit())
        {
            stack.stackSize = getInventoryStackLimit();
        }

        markDirty();

    }

    @Override
    public String getInventoryName()
    {
        return ((BlockMetalFurnace) getBlockType()).getUnlocalizedContainerName(getBlockMetadata());
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) > 64.0D;
    }

    @Override
    public void openInventory()
    {
        // Nothing

    }

    @Override
    public void closeInventory()
    {
        // Nothing

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        switch (slot)
        {
            case 0: // input
                return true;
            default: // output and errored
                return false;
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        return null;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side)
    {
        return isItemValidForSlot(slot, stack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side)
    {
        if (slot == 1 && side == 1) return true;

        return false;

    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        if (resource.getFluid() != FluidRegistry.LAVA) return 0;
        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        if (fluid != FluidRegistry.LAVA) return false;
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[] { tank.getInfo() };
    }

    public boolean isRunning()
    {
        return running;
    }

    public void setRunning(boolean isRunning)
    {
        running = isRunning;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtCompound)
    {
        super.readFromNBT(nbtCompound);

        NBTTagList nbtList = nbtCompound.getTagList("Items", 10);

        for (int i = 0; i < nbtList.tagCount(); i++)
        {
            NBTTagCompound slotCompound = nbtList.getCompoundTagAt(i);
            byte slot = slotCompound.getByte("Slot");
            inventory[slot] = ItemStack.loadItemStackFromNBT(slotCompound);
        }

        processingTicks = nbtCompound.getInteger("Process");
        burningTicks = nbtCompound.getInteger("Burning");
        running = nbtCompound.getBoolean("Running");

        maxCapacity = ConfigHandler.smelterTankCapacities[getType()];

        tank.setCapacity(maxCapacity);

        tank.readFromNBT(nbtCompound);

        fluidLevel = tank.getFluidAmount();

    }

    @Override
    public void writeToNBT(NBTTagCompound nbtCompound)
    {
        super.writeToNBT(nbtCompound);

        NBTTagList nbtList = new NBTTagList();

        for (int i = 0; i < inventory.length; i++)
        {
            if (inventory[i] == null) continue;

            NBTTagCompound slotCompound = new NBTTagCompound();
            slotCompound.setByte("Slot", (byte) i);
            inventory[i].writeToNBT(slotCompound);
            nbtList.appendTag(slotCompound);
        }

        nbtCompound.setTag("Items", nbtList);

        nbtCompound.setInteger("Process", processingTicks);
        nbtCompound.setInteger("Burning", burningTicks);
        nbtCompound.setBoolean("Running", running);

        tank.writeToNBT(nbtCompound);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        boolean burning = burningTicks > 0;
        boolean requiresUpdate = false;
        maxProcessingTicks = ConfigHandler.smelterProcessTicks[getType()];
        maxCapacity = tank.getCapacity();

        if (burning)
        {
            burningTicks--;
        }

        if (worldObj.isRemote) return;

        int drainAmount = ConfigHandler.smelterDrainPerProcess[getType()];
        if (burningTicks != 0 || inventory[0] != null && tank.getFluidAmount() >= drainAmount)
        {
            if (burningTicks == 0 && canProcess())
            {
                // Try to start burning

                burningTicks = maxBurningTicks = maxProcessingTicks;

                if (burningTicks > 0)
                {
                    // Consume fuel
                    requiresUpdate = true;

                    tank.drain(drainAmount, true);
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

        if (fluidTicks <= 0)
        {
            fluidTicks = 20;

            if (fluidLevel != tank.getFluidAmount())
            {
                fluidLevel = tank.getFluidAmount();
                TargetPoint point = new TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 64.0D);
                NetworkHandler.CHANNEL.sendToAllAround(new MessageSmelterFluid(this, fluidLevel), point);
            }

        }

        fluidTicks--;

        if (requiresUpdate) markDirty();
    }

    private boolean canProcess()
    {

        if (inventory[0] == null) return false;

        ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(inventory[0]);
        if (result == null) return false;
        if (inventory[1] == null) return true;
        if (!inventory[1].isItemEqual(result)) return false;
        int totalStackSize = inventory[1].stackSize + result.stackSize;
        return totalStackSize <= getInventoryStackLimit() && totalStackSize <= result.getMaxStackSize();

    }

    private void processInput()
    {
        if (!canProcess()) return;

        ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(inventory[0]);

        if (inventory[1] == null)
        {
            inventory[1] = result.copy();
        }
        else if (inventory[1].isItemEqual(result))
        {
            inventory[1].stackSize += result.stackSize;
        }

        inventory[0].stackSize--;

        if (inventory[0].stackSize <= 0)
        {
            inventory[0] = null;
        }
    }

    public boolean currentlyBurning()
    {
        return burningTicks > 0;
    }

    public int getScaledProcessingTime(int scale)
    {
        int processingDivisor = maxProcessingTicks;
        int displayTicks = processingTicks;
        if (processingDivisor <= 0)
        {
            processingDivisor = 1;
        }

        if (displayTicks > processingDivisor)
        {
            displayTicks = processingDivisor;
        }

        return displayTicks * scale / processingDivisor;
    }

    public int getScaledFluidLevel(int scale)
    {
        int fluidDivisor = maxCapacity;
        int displayLevel = fluidLevel;

        if (fluidDivisor <= 0)
        {
            fluidDivisor = 1;
        }

        if (displayLevel > fluidDivisor)
        {
            displayLevel = fluidDivisor;
        }

        return displayLevel * scale / fluidDivisor;
    }

}
