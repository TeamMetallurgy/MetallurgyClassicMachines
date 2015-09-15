package com.teammetallurgy.metallurgycm.block;

import java.util.Locale;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.handler.MetallurgyCMGuiHandler;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityAbstractor;

public class BlockAbstractor extends BlockBaseMachine
{
    public BlockAbstractor()
    {
        setBlockName(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ".abstractor");
        setBaseName("Abstractor");
        setBlockTypes("Prometheum", "Deep Iron", "Black Steel", "Oureclase", "Mithril", "Haderoth", "Orichalcum", "Adamantine", "Atlarus", "Tartarite");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityAbstractor();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xDistance, float yDistance, float zDistance)
    {
        player.openGui(MetallurgyCM.MOD_ID, MetallurgyCMGuiHandler.ABSTRACTOR_ID, world, x, y, z);
        return true;
    }
}
