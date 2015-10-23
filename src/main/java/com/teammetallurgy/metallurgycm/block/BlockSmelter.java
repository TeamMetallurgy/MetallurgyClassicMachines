package com.teammetallurgy.metallurgycm.block;

import java.util.Locale;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.handler.MetallurgyCMGuiHandler;
import com.teammetallurgy.metallurgycm.tileentity.TileEntitySmelter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xDistance, float yDistance, float zDistance)
    {
        // Try to fill using held fluid container ex. Lava Bucket
        ItemStack equipedItemStack = player.getHeldItem();

        if (FluidContainerRegistry.isFilledContainer(equipedItemStack))
        {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileEntitySmelter)
            {
                TileEntitySmelter teSmelter = (TileEntitySmelter) tileEntity;
                FluidStack fuelStack = FluidContainerRegistry.getFluidForFilledItem(equipedItemStack);

                if (teSmelter.canFill(teSmelter.getFacing(), fuelStack.getFluid()))
                {
                    int containerVolume = FluidContainerRegistry.getContainerCapacity(equipedItemStack);
                    int filledSimulated = teSmelter.fill(teSmelter.getFacing(), fuelStack, false);

                    if (filledSimulated == containerVolume)
                    {
                        ItemStack emptyContainer = FluidContainerRegistry.drainFluidContainer(equipedItemStack);
                        teSmelter.fill(teSmelter.getFacing(), fuelStack, true);
                        player.setCurrentItemOrArmor(0, emptyContainer);
                        return false;
                    }
                }
            }
        }

        // Open GUI
        player.openGui(MetallurgyCM.MOD_ID, MetallurgyCMGuiHandler.SMELTER_ID, world, x, y, z);
        return true;
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

        if (!(tileEntity instanceof TileEntitySmelter)) return blockIcon;

        FluidTankInfo[] tanksInfo = ((TileEntitySmelter) tileEntity).getTankInfo(((TileEntitySmelter) tileEntity).getFacing());

        if (tanksInfo[0].capacity > 0)
        {
            storageLevel = ((TileEntitySmelter) tileEntity).getScaledFluidLevel(4);

            if ((storageLevel == 0 && ((TileEntitySmelter) tileEntity).fluidLevel > 0) || (storageLevel == 0 && ((TileEntitySmelter) tileEntity).isRunning()))
            {
                storageLevel = 1;
            }
        }

        ForgeDirection facingDirection = ((TileEntitySmelter) tileEntity).getFacing();

        if (isFront(side, facingDirection))
        {
            if (((TileEntitySmelter) tileEntity).isRunning())
            {
                return activeIcons[meta][storageLevel];
            }
            else
            {
                return frontIcons[meta][storageLevel];
            }
        }

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

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        super.randomDisplayTick(world, x, y, z, random);

        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (!(tileEntity instanceof TileEntitySmelter)) return;

        if (!((TileEntitySmelter) tileEntity).isRunning()) return;

        ForgeDirection facing = ((TileEntitySmelter) tileEntity).getFacing();
        double centerX = x + 0.5D;
        double randomY = y + 0.0D + random.nextFloat() * 6.0D / 16.0D;
        double centerZ = z + 0.5D;
        double faceOffset = 0.6D;
        double randomOffset = random.nextFloat() * 0.6D - 0.3D;

        switch (facing)
        {
            case NORTH:
                world.spawnParticle("smoke", centerX + randomOffset, randomY, centerZ - faceOffset, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("lava", centerX + randomOffset, randomY, centerZ - faceOffset, 0.0D, 0.0D, 0.0D);
                break;
            case SOUTH:
                world.spawnParticle("smoke", centerX + randomOffset, randomY, centerZ + faceOffset, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("lava", centerX + randomOffset, randomY, centerZ + faceOffset, 0.0D, 0.0D, 0.0D);
                break;
            case WEST:
                world.spawnParticle("smoke", centerX - faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("lava", centerX - faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
                break;
            case EAST:
                world.spawnParticle("smoke", centerX + faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("lava", centerX + faceOffset, randomY, centerZ + randomOffset, 0.0D, 0.0D, 0.0D);
                break;
            default:
                break;
        }
    }

}
