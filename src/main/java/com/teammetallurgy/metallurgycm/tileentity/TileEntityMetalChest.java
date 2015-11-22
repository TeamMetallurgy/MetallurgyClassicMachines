package com.teammetallurgy.metallurgycm.tileentity;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;

import com.teammetallurgy.metallurgycm.block.BlockMetalChest;
import com.teammetallurgy.metallurgycm.inventory.ContainerMetalChest;

public class TileEntityMetalChest extends TileEntityBaseMachine implements IInventory
{
    private ItemStack[] inventory = new ItemStack[0];
    private int currentUsersCount;
    public float lidOpenRatio;
    public float lidPreviousOpenRatio;

    private int[] sizes = { 54, 72, 81, 90, 108 };
    private int size = sizes[0];

    private int syncTicks;

    public void setInvSize(int meta)
    {
        if (meta > 0 && meta < sizes.length) size = sizes[meta];

        inventory = new ItemStack[size];

        markDirty();
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

        worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, currentUsersCount);
        worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
        worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, getBlockType());

    }

    @Override
    public void closeInventory()
    {
        if (getBlockType() instanceof BlockMetalChest)
        {
            currentUsersCount--;

            worldObj.addBlockEvent(xCoord, yCoord, zCoord, getBlockType(), 1, currentUsersCount);
            worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getBlockType());
            worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, getBlockType());
        }

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
    {
        return true;
    }

    @Override
    public boolean receiveClientEvent(int eventId, int value)
    {
        if (eventId == 1)
        {
            currentUsersCount = value;
            return true;
        }

        return super.receiveClientEvent(eventId, value);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtCompound)
    {
        super.readFromNBT(nbtCompound);

        size = nbtCompound.getByte("InvSize");
        inventory = new ItemStack[size];

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

        nbtCompound.setByte("InvSize", (byte) size);

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

    @Override
    @SuppressWarnings("rawtypes")
    public void updateEntity()
    {
        super.updateEntity();

        syncTicks++;

        if (!worldObj.isRemote && currentUsersCount != 0 && (syncTicks + xCoord + yCoord + zCoord) % 200 == 0)
        {
            currentUsersCount = 0;
            double range = 5.0D;

            AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(xCoord - range, yCoord - range, zCoord - range, xCoord + range + 1, yCoord + range + 1, zCoord + range + 1);
            List playerList = worldObj.getEntitiesWithinAABB(EntityPlayer.class, boundingBox);

            for (Object entry : playerList)
            {
                EntityPlayer player = (EntityPlayer) entry;

                if (!(player.openContainer instanceof ContainerMetalChest))
                {
                    continue;
                }

                TileEntityMetalChest containerTileEntity = ((ContainerMetalChest) player.openContainer).getTileEntity();
                if (this == containerTileEntity)
                {
                    currentUsersCount++;
                }
            }

        }

        lidPreviousOpenRatio = lidOpenRatio;
        float step = 0.1F;

        if (currentUsersCount > 0 && lidOpenRatio == 0)
        {
            worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.12F + 0.83F);
        }

        if ((currentUsersCount <= 0 && lidOpenRatio > 0.0D) || (currentUsersCount > 0 && lidOpenRatio < 1.0D))
        {
            if (currentUsersCount > 0)
            {
                lidOpenRatio += step;
            }
            else
            {
                lidOpenRatio -= step;
            }

            if (lidOpenRatio > 1.0F)
            {
                lidOpenRatio = 1.0F;
            }

            if (lidOpenRatio < 0)
            {
                lidOpenRatio = 0;
            }

            float halfOpen = 0.5F;

            if (lidOpenRatio < halfOpen && lidPreviousOpenRatio >= halfOpen)
            {
                worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.12F + 0.83F);
            }

        }

    }
}
