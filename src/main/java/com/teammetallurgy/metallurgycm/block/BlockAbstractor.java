package com.teammetallurgy.metallurgycm.block;

import java.util.Locale;
import java.util.Random;

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
                world.spawnParticle("happyVillager", centerX + randomOffset, randomY, centerZ - faceOffset, 0.0D, 0.0D, 0.0D);
                break;
            case SOUTH:
                world.spawnParticle("smoke", centerX + randomOffset, randomY, centerZ + faceOffset, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("happyVillager", centerX + randomOffset, randomY, centerZ + faceOffset, 0.0D, 0.0D, 0.0D);
                break;
            case WEST:
                world.spawnParticle("smoke", centerX - faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("happyVillager", centerX - faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
                break;
            case EAST:
                world.spawnParticle("smoke", centerX + faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("happyVillager", centerX + faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
                break;
            default:
                break;
        }
    }
}
