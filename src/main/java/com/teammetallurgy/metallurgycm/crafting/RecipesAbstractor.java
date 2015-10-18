package com.teammetallurgy.metallurgycm.crafting;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;

public class RecipesAbstractor
{
    private static RecipesAbstractor INSTANCE = new RecipesAbstractor();
    private HashMap<ItemStack, Float> inputBaseEssences = new HashMap<ItemStack, Float>();
    private HashMap<ItemStack, Integer> catalystBurning = new HashMap<ItemStack, Integer>();

    public static void addBaseMaterial(ItemStack baseItemStack, float baseEssense)
    {
        RecipesAbstractor.INSTANCE.inputBaseEssences.put(baseItemStack, baseEssense);
    }

    public static void addCatalyst(ItemStack catalystItemStack, int burningTime)
    {
        RecipesAbstractor.INSTANCE.catalystBurning.put(catalystItemStack, burningTime);
    }

    public static float getBaseEssence(ItemStack baseStack)
    {
        float essence = 0.0F;

        for (Entry<ItemStack, Float> entry : INSTANCE.inputBaseEssences.entrySet())
        {
            ItemStack entryStack = entry.getKey();
            if (baseStack.isItemEqual(entryStack))
            {
                essence = entry.getValue().floatValue();
                break;
            }
        }

        return essence;
    }

    public static int getCatalystBurning(ItemStack catalystStack)
    {
        int burningTime = 0;

        for (Entry<ItemStack, Integer> entry : INSTANCE.catalystBurning.entrySet())
        {
            ItemStack entryStack = entry.getKey();
            if (catalystStack.isItemEqual(entryStack))
            {
                burningTime = entry.getValue().intValue();
                break;
            }
        }

        return burningTime;

    }

}
