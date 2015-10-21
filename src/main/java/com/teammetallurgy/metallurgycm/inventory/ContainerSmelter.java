package com.teammetallurgy.metallurgycm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;

import com.teammetallurgy.metallurgycm.tileentity.TileEntitySmelter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerSmelter extends Container
{
    private TileEntitySmelter tileEntity;

    private int lastProcessingTicks;
    private int lastMaxProcessingTicks;
    private int lastFluidLevel;
    private int lastMaxCapacity;

    public ContainerSmelter(InventoryPlayer inventoryPlayer, TileEntitySmelter tileEntitySmelter)
    {
        tileEntity = tileEntitySmelter;

        // Input
        addSlotToContainer(new Slot(tileEntity, 0, 36, 34));
        // Output
        addSlotToContainer(new SlotFurnace(inventoryPlayer.player, tileEntity, 1, 96, 34));

        int x = 0;
        int y = 0;

        // Player inventory
        for (y = 0; y < 3; y++)
        {
            for (x = 0; x < 9; x++)
            {
                addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // Player hotbar
        for (x = 0; x < 9; x++)
        {
            addSlotToContainer(new Slot(inventoryPlayer, x, 8 + x * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

    @Override
    public void addCraftingToCrafters(ICrafting crafter)
    {
        super.addCraftingToCrafters(crafter);

        crafter.sendProgressBarUpdate(this, 0, tileEntity.processingTicks);
        crafter.sendProgressBarUpdate(this, 1, tileEntity.maxProcessingTicks);
        crafter.sendProgressBarUpdate(this, 2, tileEntity.fluidLevel);
        crafter.sendProgressBarUpdate(this, 3, tileEntity.maxCapacity);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < crafters.size(); i++)
        {
            ICrafting crafter = (ICrafting) crafters.get(i);

            if (lastProcessingTicks != tileEntity.processingTicks) crafter.sendProgressBarUpdate(this, 0, tileEntity.processingTicks);

            if (lastMaxProcessingTicks != tileEntity.maxProcessingTicks) crafter.sendProgressBarUpdate(this, 1, tileEntity.maxProcessingTicks);

            if (lastFluidLevel != tileEntity.fluidLevel) crafter.sendProgressBarUpdate(this, 2, tileEntity.fluidLevel);

            if (lastMaxCapacity != tileEntity.maxCapacity) crafter.sendProgressBarUpdate(this, 3, tileEntity.maxCapacity);
        }

        lastProcessingTicks = tileEntity.processingTicks;
        lastMaxProcessingTicks = tileEntity.maxProcessingTicks;
        lastFluidLevel = tileEntity.fluidLevel;
        lastMaxCapacity = tileEntity.maxCapacity;

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value)
    {
        switch (id)
        {
            case 0:
                tileEntity.processingTicks = value;
                break;
            case 1:
                tileEntity.maxProcessingTicks = value;
                break;
            case 2:
                tileEntity.fluidLevel = value;
                break;
            case 3:
                tileEntity.maxCapacity = value;
        }
    }
}
