package com.teammetallurgy.metallurgycm.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.teammetallurgy.metallurgycm.block.BlockMetalChest;

public class TileEntityMetalChest extends TileEntityBaseMachine implements IInventory
{
    private ItemStack[] inventory;
    private int currentUsersCount;

    private int[] sizes = { 54, 72, 81, 90, 108 };
    private int size = sizes[0];

    public void setInvSize(int meta)
    {
        if (meta > 0 && meta < sizes.length) size = sizes[meta];

        inventory = new ItemStack[size];
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.length;
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
        return ((BlockMetalChest) getBlockType()).getUnlocalizedContainerName(getBlockMetadata());
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

        if (currentUsersCount < 0) currentUsersCount = 0;

        currentUsersCount++;

        // TODO Networking Sync

    }

    @Override
    public void closeInventory()
    {
        currentUsersCount--;

        // TODO Networking Sync

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtCompound)
    {
        super.readFromNBT(nbtCompound);

        NBTTagList nbtList = nbtCompound.getTagList("Items", 10);

        for (int i = 0; i < nbtList.tagCount(); i++)
        {
            NBTTagCompound slotCompound = nbtList.getCompoundTagAt(i);
            int slot = slotCompound.getShort("Slot");

            if (slot < 0 || slot >= inventory.length) continue;

            inventory[slot] = ItemStack.loadItemStackFromNBT(slotCompound);
        }

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
            slotCompound.setShort("Slot", (short) i);
            inventory[i].writeToNBT(slotCompound);
            nbtList.appendTag(slotCompound);
        }

        nbtCompound.setTag("Items", nbtList);
    }

}
