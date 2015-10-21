package com.teammetallurgy.metallurgycm.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

import com.teammetallurgy.metallurgycm.tileentity.TileEntityCrusher;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerCrusher extends Container
{
    private TileEntityCrusher tileEntity;
    private int lastProcessingTicks;
    private int lastMaxProcessingTicks;
    private int lastBurningTicks;
    private int lastMaxBurningTicks;

    public ContainerCrusher(InventoryPlayer inventoryPlayer, TileEntityCrusher tileEntityCrusher)
    {
        tileEntity = tileEntityCrusher;

        int x = 0;
        int y = 0;

        // Fuel
        addSlotToContainer(new Slot(tileEntity, 0, 56, 53));
        // Input
        addSlotToContainer(new Slot(tileEntity, 1, 56, 17));
        // Output
        addSlotToContainer(new SlotCrusher(inventoryPlayer.player, tileEntity, 2, 116, 35));

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
        crafter.sendProgressBarUpdate(this, 2, tileEntity.burningTicks);
        crafter.sendProgressBarUpdate(this, 3, tileEntity.maxBurningTicks);
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

            if (lastBurningTicks != tileEntity.burningTicks) crafter.sendProgressBarUpdate(this, 2, tileEntity.burningTicks);

            if (lastMaxBurningTicks != tileEntity.maxBurningTicks) crafter.sendProgressBarUpdate(this, 3, tileEntity.maxBurningTicks);
        }

        lastProcessingTicks = tileEntity.processingTicks;
        lastMaxProcessingTicks = tileEntity.maxProcessingTicks;
        lastBurningTicks = tileEntity.burningTicks;
        lastMaxBurningTicks = tileEntity.maxBurningTicks;

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
                tileEntity.burningTicks = value;
                break;
            case 3:
                tileEntity.maxBurningTicks = value;
        }
    }

}
