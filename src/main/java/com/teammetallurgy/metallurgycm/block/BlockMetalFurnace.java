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
import com.teammetallurgy.metallurgycm.tileentity.TileEntityBaseMachine;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalFurnace;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityStandardMachine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xDistance, float yDistance, float zDistance)
    {
        player.openGui(MetallurgyCM.MOD_ID, MetallurgyCMGuiHandler.METAL_FURNACE_ID, world, x, y, z);
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

        if (!(tileEntity instanceof TileEntityMetalFurnace)) { return 0; }

        if (!((TileEntityMetalFurnace) tileEntity).isRunning()) { return 0; }
        // Same as the furnace
        return 13;
    }

    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(getTextureName());

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
