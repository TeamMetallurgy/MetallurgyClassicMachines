package com.teammetallurgy.metallurgycm.block;

import java.util.Locale;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalFurnace;

public class BlockMetalFurnace extends BlockBaseMachine
{

    public BlockMetalFurnace()
    {
        setBlockName(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ".metal.furnace");
        setBaseName("Furnace");
        setBlockTypes("Copper", "Bronze", "Iron", "Steel");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityMetalFurnace();
    }

    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        this.blockIcon = register.registerIcon(getTextureName());

        if (types == null) return;

        frontIcons = new IIcon[types.length];
        topIcons = new IIcon[types.length];
        sideIcons = new IIcon[types.length];
        activeIcons = new IIcon[types.length];

        String texturePath = MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":" + baseName.toLowerCase(Locale.US) + "/";

        for (int i = 0; i < types.length; i++)
        {
            String baseTexture = texturePath + types[i].toLowerCase(Locale.US).replace(" ", "_");
            frontIcons[i] = register.registerIcon(baseTexture + "_front");
            topIcons[i] = register.registerIcon(baseTexture + "_top");
            sideIcons[i] = register.registerIcon(baseTexture + "_side");
            activeIcons[i] = register.registerIcon(baseTexture + "_active");
        }
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta < 0 || meta >= types.length) return blockIcon;

        switch (side)
        {
            case 0:
            case 1:
                return topIcons[meta];
            case 3:
                return frontIcons[meta];
            default:
                return sideIcons[meta];
        }
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (meta < 0 || meta >= types.length) return blockIcon;

        switch (side)
        {
            case 0:
            case 1:
                return topIcons[meta];
            case 3:
                return frontIcons[meta];
            default:
                return sideIcons[meta];
        }
    }

}
