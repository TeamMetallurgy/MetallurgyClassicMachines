package com.teammetallurgy.metallurgycm.block;

import java.util.Locale;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityBaseMachine;
import com.teammetallurgy.metallurgycm.tileentity.TileEntitySmelter;

public class BlockSmelter extends BlockBaseMachine
{
    private IIcon[][] frontIcons;
    private IIcon[][] sideIcons;
    private IIcon[][] activeIcons;
    private IIcon[] topActiveIcons;

    public BlockSmelter()
    {
        setBlockName(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ".slmelter");
        setBaseName("Smelter");
        setBlockTypes("Ignatius", "Shadow Iron", "Shadow Steel", "Vyroxeres", "Inolashite", "Kalendrite", "Vulcanite", "Sanguinite");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntitySmelter();
    }

    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(getTextureName());

        if (types == null) return;

        frontIcons = new IIcon[types.length][5];
        bottomIcons = new IIcon[types.length];
        topIcons = new IIcon[types.length];
        sideIcons = new IIcon[types.length][5];
        activeIcons = new IIcon[types.length][5];
        topActiveIcons = new IIcon[types.length];

        String texturePath = MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":" + baseName.toLowerCase(Locale.US) + "/";

        for (int i = 0; i < types.length; i++)
        {
            String baseTexture = texturePath + types[i].toLowerCase(Locale.US).replace(" ", "_");
            bottomIcons[i] = register.registerIcon(baseTexture + "_bottom");
            topIcons[i] = register.registerIcon(baseTexture + "_top_0");
            topActiveIcons[i] = register.registerIcon(baseTexture + "_top_1");

            for (int j = 0; j < 5; j++)
            {
                frontIcons[i][j] = register.registerIcon(baseTexture + "_front_" + j);
                sideIcons[i][j] = register.registerIcon(baseTexture + "_side_" + j);
                if (j != 0) activeIcons[i][j] = register.registerIcon(baseTexture + "_active_" + j);
            }

        }
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta < 0 || meta >= types.length) return blockIcon;

        int storageLevel = 0;

        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (!(tileEntity instanceof TileEntityBaseMachine)) return blockIcon;

        ForgeDirection facingDirection = ((TileEntityBaseMachine) tileEntity).getFacing();

        if (isFront(side, facingDirection)) return frontIcons[meta][storageLevel];

        switch (side)
        {
            case 0:
                return bottomIcons[meta];
            case 1:
                return topIcons[meta];
            default:
                return sideIcons[meta][storageLevel];
        }
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (meta < 0 || meta >= types.length) return blockIcon;

        int storageLevel = 0;

        switch (side)
        {
            case 0:
                return bottomIcons[meta];
            case 1:
                return topIcons[meta];
            case 3:
                return frontIcons[meta][storageLevel];
            default:
                return sideIcons[meta][storageLevel];
        }
    }

}
