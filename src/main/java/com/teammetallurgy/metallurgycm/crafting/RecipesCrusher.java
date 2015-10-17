package com.teammetallurgy.metallurgycm.crafting;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;

public class RecipesCrusher
{
    private static RecipesCrusher INSTANCE = new RecipesCrusher();
    private HashMap<ItemStack, ItemStack> crushedMaterials = new HashMap<ItemStack, ItemStack>();
    private HashMap<ItemStack, Float> processingEssence = new HashMap<ItemStack, Float>();

    public static void addRecipe(ItemStack input, ItemStack result, float experiance)
    {
        if (input == null || input.getItem() == null || result == null || result.getItem() == null) return;

        RecipesCrusher.INSTANCE.crushedMaterials.put(input, result);
        RecipesCrusher.INSTANCE.processingEssence.put(result, experiance);
    }

    public static ItemStack getResult(ItemStack input)
    {
        ItemStack result = null;

        if (input == null || input.getItem() == null) return result;

        for (Entry<ItemStack, ItemStack> entry : RecipesCrusher.INSTANCE.crushedMaterials.entrySet())
        {
            ItemStack entryStack = entry.getKey();
            if (input.isItemEqual(entryStack))
            {
                result = entry.getValue().copy();
                break;
            }
        }

        return result;
    }

    public static float getExperiance(ItemStack result)
    {
        float experiance = 0.0F;

        for (Entry<ItemStack, Float> entry : RecipesCrusher.INSTANCE.processingEssence.entrySet())
        {
            ItemStack entryStack = entry.getKey();
            if (result.isItemEqual(entryStack))
            {
                experiance = entry.getValue().floatValue();
                break;
            }
        }
        return experiance;
    }

}
