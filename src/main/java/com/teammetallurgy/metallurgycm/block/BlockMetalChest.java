package com.teammetallurgy.metallurgycm.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalChest;

public class BlockMetalChest extends BlockContainer
{

    public BlockMetalChest()
    {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityMetalChest();
    }

}
