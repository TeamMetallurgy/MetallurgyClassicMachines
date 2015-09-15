package com.teammetallurgy.metallurgycm.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgycm.client.gui.GuiAbstractor;
import com.teammetallurgy.metallurgycm.client.gui.GuiCrusher;
import com.teammetallurgy.metallurgycm.client.gui.GuiMetalChest;
import com.teammetallurgy.metallurgycm.client.gui.GuiMetalFurnace;
import com.teammetallurgy.metallurgycm.client.gui.GuiSmelter;
import com.teammetallurgy.metallurgycm.inventory.ContainerAbstractor;
import com.teammetallurgy.metallurgycm.inventory.ContainerCrusher;
import com.teammetallurgy.metallurgycm.inventory.ContainerMetalChest;
import com.teammetallurgy.metallurgycm.inventory.ContainerMetalFurnace;
import com.teammetallurgy.metallurgycm.inventory.ContainerSmelter;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityAbstractor;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityCrusher;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalChest;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalFurnace;
import com.teammetallurgy.metallurgycm.tileentity.TileEntitySmelter;

import cpw.mods.fml.common.network.IGuiHandler;

public class MetallurgyCMGuiHandler implements IGuiHandler
{
    public static final int ABSTRACTOR_ID = 0;
    public static final int CRUSHER_ID = 1;
    public static final int METAL_CHEST_ID = 2;
    public static final int METAL_FURNACE_ID = 3;
    public static final int SMELTER_ID = 4;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (ID == ABSTRACTOR_ID && tileEntity instanceof TileEntityAbstractor) return new ContainerAbstractor(player.inventory, (TileEntityAbstractor) tileEntity);
        if (ID == CRUSHER_ID && tileEntity instanceof TileEntityCrusher) return new ContainerCrusher(player.inventory, (TileEntityCrusher) tileEntity);
        if (ID == METAL_CHEST_ID && tileEntity instanceof TileEntityMetalChest) return new ContainerMetalChest(player.inventory, (TileEntityMetalChest) tileEntity);
        if (ID == METAL_FURNACE_ID && tileEntity instanceof TileEntityMetalFurnace) return new ContainerMetalFurnace(player.inventory, (TileEntityMetalFurnace) tileEntity);
        if (ID == SMELTER_ID && tileEntity instanceof TileEntitySmelter) return new ContainerSmelter(player.inventory, (TileEntitySmelter) tileEntity);

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (ID == ABSTRACTOR_ID && tileEntity instanceof TileEntityAbstractor) return new GuiAbstractor(player.inventory, (TileEntityAbstractor) tileEntity);
        if (ID == CRUSHER_ID && tileEntity instanceof TileEntityCrusher) return new GuiCrusher(player.inventory, (TileEntityCrusher) tileEntity);
        if (ID == METAL_CHEST_ID && tileEntity instanceof TileEntityMetalChest) return new GuiMetalChest(player.inventory, (TileEntityMetalChest) tileEntity);
        if (ID == METAL_FURNACE_ID && tileEntity instanceof TileEntityMetalFurnace) return new GuiMetalFurnace(player.inventory, (TileEntityMetalFurnace) tileEntity);
        if (ID == SMELTER_ID && tileEntity instanceof TileEntitySmelter) return new GuiSmelter(player.inventory, (TileEntitySmelter) tileEntity);

        return null;
    }

}
