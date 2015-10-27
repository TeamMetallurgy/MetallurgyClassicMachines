package com.teammetallurgy.metallurgycm.block;

import java.util.Locale;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.handler.MetallurgyCMGuiHandler;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityCrusher;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityStandardMachine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
    public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y, z);
        if (block != this)
        {
            block.getLightValue(world, x, y, z);
        }

        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (!(tileEntity instanceof TileEntityCrusher)) { return 0; }

        if (!((TileEntityCrusher) tileEntity).isRunning()) { return 0; }
        // Same as the furnace
        return 13;
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

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        super.randomDisplayTick(world, x, y, z, random);

        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (!(tileEntity instanceof TileEntityStandardMachine)) return;

        if (!((TileEntityStandardMachine) tileEntity).isRunning()) return;

        ForgeDirection facing = ((TileEntityStandardMachine) tileEntity).getFacing();
        double centerX = x + 0.5D;
        double randomY = y + 0.0D + random.nextFloat() * 6.0D / 16.0D;
        double centerZ = z + 0.5D;
        double faceOffset = 0.6D;
        double randomOffset = random.nextFloat() * 0.6D - 0.3D;

        switch (facing)
        {
            case NORTH:
                world.spawnParticle("smoke", centerX + randomOffset, randomY, centerZ - faceOffset, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", centerX + randomOffset, randomY, centerZ - faceOffset, 0.0D, 0.0D, 0.0D);
                break;
            case SOUTH:
                world.spawnParticle("smoke", centerX + randomOffset, randomY, centerZ + faceOffset, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", centerX + randomOffset, randomY, centerZ + faceOffset, 0.0D, 0.0D, 0.0D);
                break;
            case WEST:
                world.spawnParticle("smoke", centerX - faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", centerX - faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
                break;
            case EAST:
                world.spawnParticle("smoke", centerX + faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", centerX + faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
                break;
            default:
                break;
        }
    }

}
