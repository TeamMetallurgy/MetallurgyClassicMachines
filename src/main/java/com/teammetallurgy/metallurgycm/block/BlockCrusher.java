package com.teammetallurgy.metallurgycm.block;

import java.util.Locale;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.handler.MetallurgyCMGuiHandler;
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xDistance, float yDistance, float zDistance)
    {
        player.openGui(MetallurgyCM.MOD_ID, MetallurgyCMGuiHandler.CRUSHER_ID, world, x, y, z);
        return true;
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

    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(getTextureName());

        if (types == null) return;

        frontIcons = new IIcon[types.length];

        String texturePath = MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":" + baseName.toLowerCase(Locale.US) + "/";

        for (int i = 0; i < types.length; i++)
        {
            String baseTexture = texturePath + types[i].toLowerCase(Locale.US).replace(" ", "_");
            frontIcons[i] = register.registerIcon(baseTexture + "_block");

        }
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {

        if (meta < 0 || meta >= types.length) return blockIcon;

        return frontIcons[meta];
    }

}
