package com.teammetallurgy.metallurgycm.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalFurnace;

public class BlockMetalFurnace extends BlockBaseMachine
{

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityMetalFurnace();
    }

}
