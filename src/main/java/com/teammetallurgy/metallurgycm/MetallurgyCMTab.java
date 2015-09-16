package com.teammetallurgy.metallurgycm;

import java.util.Locale;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MetallurgyCMTab extends CreativeTabs
{

    public MetallurgyCMTab()
    {
        super(MetallurgyCM.MOD_ID.toLowerCase(Locale.US));
    }

    @Override
    public Item getTabIconItem()
    {
        return Item.getItemFromBlock(MetallurgyCMBlocks.abstractor);
    }

}
