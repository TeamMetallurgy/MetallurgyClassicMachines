package com.teammetallurgy.metallurgycm.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesCrusher
{
    private static RecipesCrusher INSTANCE = new RecipesCrusher();
    private HashMap<ItemStack, ItemStack> crushedMaterials = new HashMap<ItemStack, ItemStack>();
    private HashMap<ArrayList<ItemStack>, ItemStack> oreDicCrushedMaterials = new HashMap<ArrayList<ItemStack>, ItemStack>();
    private HashMap<ItemStack, Float> processingEssence = new HashMap<ItemStack, Float>();

    public static void addRecipe(ItemStack input, ItemStack result, float experiance)
    {
        if (input == null || input.getItem() == null || result == null || result.getItem() == null) return;

        RecipesCrusher.INSTANCE.crushedMaterials.put(input, result);
        RecipesCrusher.INSTANCE.processingEssence.put(result, experiance);
    }

    public static void addOreDicRecipe(String input, ItemStack result, float experiance)
    {
        ArrayList<ItemStack> inputItemStacks = OreDictionary.getOres(input);

        if (inputItemStacks.size() <= 0) return;

        RecipesCrusher.INSTANCE.oreDicCrushedMaterials.put(inputItemStacks, result);
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

        // Found result from normal recipe.
        if (result != null) return result;

        // Find an oredic recipe
        for (Entry<ArrayList<ItemStack>, ItemStack> entry : RecipesCrusher.INSTANCE.oreDicCrushedMaterials.entrySet())
        {
            ArrayList<ItemStack> oreDicItemStacks = entry.getKey();
            for (ItemStack entryStack : oreDicItemStacks)
            {
                if (OreDictionary.itemMatches(entryStack, input, false))
                {
                    result = entry.getValue().copy();
                    break;
                }
            }

            if (result != null) break;

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
