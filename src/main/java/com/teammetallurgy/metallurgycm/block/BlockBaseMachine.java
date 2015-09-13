package com.teammetallurgy.metallurgycm.block;

import java.util.List;
import java.util.Locale;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.teammetallurgy.metallurgycm.MetallurgyCM;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockBaseMachine extends BlockContainer
{

    protected String[] types;
    protected String baseName = "baseBlock";

    protected IIcon frontIcons[];
    protected IIcon bottomIcons[];
    protected IIcon topIcons[];
    protected IIcon sideIcons[];
    protected IIcon activeIcons[];

    public BlockBaseMachine()
    {
        super(Material.rock);
        setHardness(2);
        setResistance(2.0F);
        setBlockTextureName(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":default_block");

    }

    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }

    protected void setBlockTypes(String... blockTypes)
    {
        types = blockTypes;
    }

    protected void setBaseName(String blockBaseName)
    {
        baseName = blockBaseName;
    }

    public String getUnlocalizedName(ItemStack stack)
    {
        if (stack == null || stack.getItem() == null || types == null) return getUnlocalizedName();

        int meta = stack.getItemDamage();
        if (meta < 0 || meta >= types.length) return getUnlocalizedName();

        String unlocalizedName = "tile." + MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ".";
        unlocalizedName += baseName.toLowerCase(Locale.US).trim().replace(" ", ".") + ".";
        unlocalizedName += types[meta].toLowerCase(Locale.US).trim().replace(" ", ".");

        return unlocalizedName;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        // Registering blockIcon
        super.registerBlockIcons(register);

        if (types == null) return;

        frontIcons = new IIcon[types.length];
        bottomIcons = new IIcon[types.length];
        topIcons = new IIcon[types.length];
        sideIcons = new IIcon[types.length];
        activeIcons = new IIcon[types.length];

        String texturePath = MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":" + baseName.toLowerCase(Locale.US) + "/";

        for (int i = 0; i < types.length; i++)
        {
            String baseTexture = texturePath + types[i].toLowerCase(Locale.US).replace(" ", "_");
            frontIcons[i] = register.registerIcon(baseTexture + "_front");
            bottomIcons[i] = register.registerIcon(baseTexture + "_bottom");
            topIcons[i] = register.registerIcon(baseTexture + "_top");
            sideIcons[i] = register.registerIcon(baseTexture + "_side");
            activeIcons[i] = register.registerIcon(baseTexture + "_active");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta < 0 || meta >= types.length) return blockIcon;

        switch (side)
        {
            case 0:
                return bottomIcons[meta];
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
    public IIcon getIcon(int side, int meta)
    {
        if (meta < 0 || meta >= types.length) return blockIcon;

        switch (side)
        {
            case 0:
                return bottomIcons[meta];
            case 1:
                return topIcons[meta];
            case 3:
                return frontIcons[meta];
            default:
                return sideIcons[meta];
        }
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < types.length; i++)
        {
            list.add(new ItemStack(this, 1, i));
        }
    }

}
