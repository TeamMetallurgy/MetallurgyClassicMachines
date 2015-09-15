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
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalChest;

public class BlockMetalChest extends BlockBaseMachine
{

    public BlockMetalChest()
    {
        setBlockName(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ".metal.chest");
        setBaseName("Chest");
        setBlockTypes("Brass", "Silver", "Gold", "Electrum", "Platinum");
        setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityMetalChest();
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(getTextureName());

        if (types == null) return;

        frontIcons = new IIcon[types.length];
        topIcons = new IIcon[types.length];
        sideIcons = new IIcon[types.length];

        String texturePath = MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":" + baseName.toLowerCase(Locale.US) + "/";

        for (int i = 0; i < types.length; i++)
        {
            String baseTexture = texturePath + types[i].toLowerCase(Locale.US).replace(" ", "_");
            frontIcons[i] = register.registerIcon(baseTexture + "_front");
            topIcons[i] = register.registerIcon(baseTexture + "_top");
            sideIcons[i] = register.registerIcon(baseTexture + "_side");
        }
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta < 0 || meta >= types.length) return blockIcon;

        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (!(tileEntity instanceof TileEntityBaseMachine)) return blockIcon;

        ForgeDirection facingDirection = ((TileEntityBaseMachine) tileEntity).getFacing();

        if (isFront(side, facingDirection)) return frontIcons[meta];

        switch (side)
        {
            case 0:
            case 1:
                return topIcons[meta];
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
