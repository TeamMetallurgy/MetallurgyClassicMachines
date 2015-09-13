package com.teammetallurgy.metallurgycm.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgycm.tileentity.TileEntityCrusher;

public class BlockCrusher extends BlockBaseMachine
{

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityCrusher();
    }

}
