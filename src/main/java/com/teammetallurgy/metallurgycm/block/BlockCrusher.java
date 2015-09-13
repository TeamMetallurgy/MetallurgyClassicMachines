package com.teammetallurgy.metallurgycm.block;

import java.util.Locale;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityCrusher;

public class BlockCrusher extends BlockBaseMachine
{
    public BlockCrusher()
    {
        setBlockName(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ".crusher");
        setBaseName("Crusher");
        setBlockTypes("Stone", "Copper", "Bronze", "Iron", "Steel");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityCrusher();
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

}
