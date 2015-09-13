package com.teammetallurgy.metallurgycm.block;

import java.util.Locale;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityAbstractor;

public class BlockAbstractor extends BlockBaseMachine
{
    public BlockAbstractor()
    {
        setBlockName(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ".abstractor");
        setBaseName("Abstractor");
        setBlockTypes("Prometheum", "Deep Iron", "Block Steel", "Oureclase", "Mithril", "Haderoth", "Orichalcum", "Adamantine", "Atlarus", "Tartarite");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityAbstractor();
    }

}
