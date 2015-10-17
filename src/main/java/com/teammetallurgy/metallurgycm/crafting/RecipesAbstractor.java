package com.teammetallurgy.metallurgycm.crafting;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;

public class RecipesAbstractor
{
    private static RecipesAbstractor INSTANCE = new RecipesAbstractor();
    private HashMap<ItemStack, Float> inputBaseEssences = new HashMap<ItemStack, Float>();
    private HashMap<ItemStack, Float> catalystMultipliers = new HashMap<ItemStack, Float>();

    public static void addBaseMaterial(ItemStack baseItemStack, float baseEssense)
    {
        RecipesAbstractor.INSTANCE.inputBaseEssences.put(baseItemStack, baseEssense);
    }

    public static void addCatalyst(ItemStack catalystItemStack, float multiplyer)
    {
        RecipesAbstractor.INSTANCE.catalystMultipliers.put(catalystItemStack, multiplyer);
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

    public static float getCatalystMultiplier(ItemStack catalystStack)
    {
        float multiplier = 0.0F;

        for (Entry<ItemStack, Float> entry : INSTANCE.catalystMultipliers.entrySet())
        {
            ItemStack entryStack = entry.getKey();
            if (catalystStack.isItemEqual(entryStack))
            {
                multiplier = entry.getValue().floatValue();
                break;
            }
        }

        return multiplier;

    }

}
