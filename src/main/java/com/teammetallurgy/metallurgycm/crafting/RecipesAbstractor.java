package com.teammetallurgy.metallurgycm.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesAbstractor
{
    private static RecipesAbstractor INSTANCE = new RecipesAbstractor();
    private HashMap<ItemStack, Integer> inputBaseEssences = new HashMap<ItemStack, Integer>();
    private HashMap<ItemStack, Integer> catalystBurning = new HashMap<ItemStack, Integer>();
    private HashMap<ArrayList<ItemStack>, Integer> oreDicBaseEsssences = new HashMap<ArrayList<ItemStack>, Integer>();
    private HashMap<ArrayList<ItemStack>, Integer> oreDicBurning = new HashMap<ArrayList<ItemStack>, Integer>();

    public static void addBaseMaterial(ItemStack baseItemStack, int baseEssense)
    {
        RecipesAbstractor.INSTANCE.inputBaseEssences.put(baseItemStack, baseEssense);
    }

    public static void addCatalyst(ItemStack catalystItemStack, int burningTime)
    {
        RecipesAbstractor.INSTANCE.catalystBurning.put(catalystItemStack, burningTime);
    }

    public static void addOreDicBaseMaterial(String oreDicName, int baseEssense)
    {
        ArrayList<ItemStack> oreDicStacks = OreDictionary.getOres(oreDicName);

        if (oreDicStacks.size() <= 0) return;

        RecipesAbstractor.INSTANCE.oreDicBaseEsssences.put(oreDicStacks, baseEssense);
    }

    public static void addOreDicCatalyst(String oreDicName, int burningTime)
    {
        ArrayList<ItemStack> oreDicStacks = OreDictionary.getOres(oreDicName);

        if (oreDicStacks.size() <= 0) return;

        RecipesAbstractor.INSTANCE.oreDicBurning.put(oreDicStacks, burningTime);
    }

    public static int getBaseEssence(ItemStack baseStack)
    {
        int essence = 0;

        for (Entry<ItemStack, Integer> entry : INSTANCE.inputBaseEssences.entrySet())
        {
            ItemStack entryStack = entry.getKey();
            if (baseStack.isItemEqual(entryStack))
            {
                essence = entry.getValue().intValue();
                break;
            }
        }

        // Found essence
        if (essence != 0) return essence;

        // Searches oreDic entries
        for (Entry<ArrayList<ItemStack>, Integer> entry : INSTANCE.oreDicBaseEsssences.entrySet())
        {
            ArrayList<ItemStack> oreDicStacks = entry.getKey();
            for (ItemStack entryStack : oreDicStacks)
            {
                if (OreDictionary.itemMatches(baseStack, entryStack, false))
                {
                    essence = entry.getValue().intValue();
                }
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

        // Found Burning
        if (burningTime > 0) return burningTime;

        // Searches oreDic entries
        for (Entry<ArrayList<ItemStack>, Integer> entry : INSTANCE.oreDicBurning.entrySet())
        {
            ArrayList<ItemStack> oreDicStacks = entry.getKey();
            for (ItemStack entryStack : oreDicStacks)
            {
                if (OreDictionary.itemMatches(catalystStack, entryStack, false))
                {
                    burningTime = entry.getValue().intValue();
                }
            }
        }
        return burningTime;

    }

    public static HashMap<ItemStack, Integer> getAbstractingRecipes()
    {
        return INSTANCE.inputBaseEssences;
    }

    public static HashMap<ArrayList<ItemStack>, Integer> getOreDicAbstractingRecipes()
    {
        return INSTANCE.oreDicBaseEsssences;
    }

    public static HashMap<ItemStack, Integer> getCatalystBurningRecipes()
    {
        return INSTANCE.catalystBurning;
    }

    public static HashMap<ArrayList<ItemStack>, Integer> getOreDicCatalystBurningRecipes()
    {
        return INSTANCE.oreDicBurning;
    }
}
