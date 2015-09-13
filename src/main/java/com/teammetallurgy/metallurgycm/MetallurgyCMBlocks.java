package com.teammetallurgy.metallurgycm;

import net.minecraft.block.Block;

import com.teammetallurgy.metallurgycm.block.BlockAbstractor;
import com.teammetallurgy.metallurgycm.block.BlockCrusher;
import com.teammetallurgy.metallurgycm.block.BlockMetalChest;
import com.teammetallurgy.metallurgycm.block.BlockMetalFurnace;
import com.teammetallurgy.metallurgycm.block.BlockSmelter;
import com.teammetallurgy.metallurgycm.item.ItemBlockBaseMachine;

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

    }
}
