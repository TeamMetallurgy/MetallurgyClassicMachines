package com.teammetallurgy.metallurgycm.block;

import java.util.Locale;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalFurnace;

public class BlockMetalFurnace extends BlockBaseMachine
{

    public BlockMetalFurnace()
    {
        setBlockName(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ".metal.furnace");
        setBaseName("Furnace");
        setBlockTypes("Copper", "Bronze", "Iron", "Steel");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityMetalFurnace();
    }

}
