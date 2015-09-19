package com.teammetallurgy.metallurgycm.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityFurnace;

import com.teammetallurgy.metallurgycm.block.BlockMetalFurnace;

public class TileEntityStandardMachine extends TileEntityBaseMachine implements ISidedInventory
{
    protected ItemStack[] inventory = new ItemStack[3];

    protected int processingTicks;
    protected int maxProcessingTicks;
    protected int burningTicks;
    protected int maxBurningTicks;

    @Override
    public int getSizeInventory()
    {
        return 3;
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
            case 0: // fuel
                return TileEntityFurnace.isItemFuel(stack);
            case 1: // input
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
        if (slot == 2 && side == 1) return true;
        if (slot == 0 && stack.getItem() == Items.bucket) return true;

        return false;

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
    }

}
