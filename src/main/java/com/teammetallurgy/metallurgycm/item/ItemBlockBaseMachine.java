package com.teammetallurgy.metallurgycm.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgycm.block.BlockBaseMachine;

public class ItemBlockBaseMachine extends ItemBlock
{

    public ItemBlockBaseMachine(Block block)
    {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta)
    {
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return ((BlockBaseMachine) this.field_150939_a).getUnlocalizedName(stack);
    }

}
