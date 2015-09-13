package com.teammetallurgy.metallurgycm;

import net.minecraft.block.Block;

import com.teammetallurgy.metallurgycm.block.BlockAbstractor;
import com.teammetallurgy.metallurgycm.block.BlockCrusher;
import com.teammetallurgy.metallurgycm.block.BlockMetalChest;
import com.teammetallurgy.metallurgycm.block.BlockMetalFurnace;
import com.teammetallurgy.metallurgycm.block.BlockSmelter;
import com.teammetallurgy.metallurgycm.item.ItemBlockBaseMachine;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityAbstractor;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityCrusher;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalChest;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalFurnace;
import com.teammetallurgy.metallurgycm.tileentity.TileEntitySmelter;

import cpw.mods.fml.common.registry.GameRegistry;

public class MetallurgyCMBlocks
{
    public static Block abstractor;
    public static Block crusher;
    public static Block metalChest;
    public static Block metalFurnace;
    public static Block smelter;

    public static void init()
    {
        abstractor = new BlockAbstractor();
        crusher = new BlockCrusher();
        metalChest = new BlockMetalChest();
        metalFurnace = new BlockMetalFurnace();
        smelter = new BlockSmelter();

        GameRegistry.registerBlock(abstractor, ItemBlockBaseMachine.class, "abstractor");
        GameRegistry.registerBlock(crusher, ItemBlockBaseMachine.class, "crusher");
        GameRegistry.registerBlock(metalChest, ItemBlockBaseMachine.class, "metal_chest");
        GameRegistry.registerBlock(metalFurnace, ItemBlockBaseMachine.class, "metal_furnace");
        GameRegistry.registerBlock(smelter, ItemBlockBaseMachine.class, "smelter");

        GameRegistry.registerTileEntity(TileEntityAbstractor.class, MetallurgyCM.MOD_NAME + ":abstractor");
        GameRegistry.registerTileEntity(TileEntityCrusher.class, MetallurgyCM.MOD_NAME + ":crusher");
        GameRegistry.registerTileEntity(TileEntityMetalChest.class, MetallurgyCM.MOD_NAME + ":metal_chest");
        GameRegistry.registerTileEntity(TileEntityMetalFurnace.class, MetallurgyCM.MOD_NAME + ":metal_furnace");
        GameRegistry.registerTileEntity(TileEntitySmelter.class, MetallurgyCM.MOD_NAME + ":smelter");
    }
}
