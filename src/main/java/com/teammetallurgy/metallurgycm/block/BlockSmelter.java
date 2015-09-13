package com.teammetallurgy.metallurgycm.block;

import java.util.Locale;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.tileentity.TileEntitySmelter;

public class BlockSmelter extends BlockBaseMachine
{
    public BlockSmelter()
    {
        setBlockName(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ".slmelter");
        setBaseName("Smelter");
        setBlockTypes("Ignatius", "Shadow Iron", "Shadow Steel", "Vyroxeres", "Inolashite", "Kalendrite", "Vulcanite", "Sanguinite");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntitySmelter();
    }

}
