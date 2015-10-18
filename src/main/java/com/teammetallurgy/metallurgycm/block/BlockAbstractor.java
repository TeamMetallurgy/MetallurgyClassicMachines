package com.teammetallurgy.metallurgycm.block;

import java.util.Locale;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.handler.MetallurgyCMGuiHandler;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityAbstractor;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityBaseMachine;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityStandardMachine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta < 0 || meta >= types.length) return blockIcon;

        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (!(tileEntity instanceof TileEntityBaseMachine)) return blockIcon;

        ForgeDirection facingDirection = ((TileEntityBaseMachine) tileEntity).getFacing();

        if (isFront(side, facingDirection))
        {
            if (((TileEntityStandardMachine) tileEntity).isRunning()) return activeIcons[meta];
            else return frontIcons[meta];
        }

        switch (side)
        {
            case 0:
                return bottomIcons[meta];
            case 1:
                return topIcons[meta];
            default:
                return sideIcons[meta];
        }

    }
}
