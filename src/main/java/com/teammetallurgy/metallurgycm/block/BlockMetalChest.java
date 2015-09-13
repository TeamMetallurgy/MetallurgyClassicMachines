package com.teammetallurgy.metallurgycm.block;

import java.util.Locale;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalChest;

public class BlockMetalChest extends BlockBaseMachine
{

    public BlockMetalChest()
    {
        setBlockName(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ".metal.chest");
        setBaseName("Chest");
        setBlockTypes("Stone", "Copper", "Bronze", "Iron", "Steel");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityMetalChest();
    }

}
