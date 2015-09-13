package com.teammetallurgy.metallurgycm.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgycm.tileentity.TileEntitySmelter;

public class BlockSmelter extends BlockBaseMachine
{

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntitySmelter();
    }

}
