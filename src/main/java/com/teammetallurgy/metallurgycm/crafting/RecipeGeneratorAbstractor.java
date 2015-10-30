package com.teammetallurgy.metallurgycm.crafting;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

import com.teammetallurgy.metallurgycm.handler.LogHandler;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class RecipeGeneratorAbstractor
{
    private static HashMap<ItemStack, Float> tempRecipeMap = new HashMap<ItemStack, Float>();

    public static void init()
    {
        LogHandler.info("Generating Abstractor recipes, Please wait...");
        LogHandler.trace("==== Reading information from ItemRegistry ====");
        long start = System.nanoTime() / 1000000;
        processItemRegistry();
        long registryFinish = System.nanoTime() / 1000000;
        LogHandler.trace("Finished reading registery in " + (registryFinish - start) + "ms");

        LogHandler.trace("==== Processing Temp Recipes ====");
        long tempRecipesStart = System.nanoTime() / 1000000;
        processTempRecipes();
        long finsih = System.nanoTime() / 1000000;
        LogHandler.trace("Finished processing tempRecipes in " + (finsih - tempRecipesStart) + "ms");

        LogHandler.info("Finished generating Abstractor recipes in " + (finsih - start) + "ms");
    }

    private static void processItemRegistry()
    {

        for (Object registryItem : GameData.getItemRegistry())
        {
            if (registryItem instanceof ItemTool)
            {
                UniqueIdentifier itemToolUid = GameRegistry.findUniqueIdentifierFor((ItemTool) registryItem);

                LogHandler.abstractorVerboseLog("Found ItemTool: " + itemToolUid.toString() + " Attemting to get ToolMaterial");

                ToolMaterial toolMaterial = null;

                try
                {
                    toolMaterial = (ToolMaterial) ReflectionHelper.findField(ItemTool.class, "toolMaterial", "field_77862_b").get(registryItem);
                }
                catch (Exception e)
                {
                    LogHandler.warning("Ran into an issue while getting ToolMaterials for " + itemToolUid.toString());
                    LogHandler.warning(e.getLocalizedMessage());
                    LogHandler.warning("StackTrace:");
                    LogHandler.warning("------------------------------");
                    for (StackTraceElement element : e.getStackTrace())
                    {
                        LogHandler.warning(element.toString());
                    }
                }

                if (testToolMaterial(toolMaterial, itemToolUid.toString()))
                {
                    LogHandler.abstractorVerboseLog("Material: " + toolMaterial.toString() + " Enchantability: " + toolMaterial.getEnchantability());
                    addTempRecipe(toolMaterial.getRepairItemStack().copy(), toolMaterial.getEnchantability());
                }
                else
                {
                    LogHandler.abstractorInvaildLog("Skipping adding recipe");
                }
                continue;
            }

            if (registryItem instanceof ItemSword)
            {
                UniqueIdentifier itemSwordUid = GameRegistry.findUniqueIdentifierFor((ItemSword) registryItem);

                LogHandler.abstractorVerboseLog("Found ItemSword: " + itemSwordUid.toString() + " Attemting to get ToolMaterial");

                ToolMaterial toolMaterial = null;

                try
                {
                    toolMaterial = (ToolMaterial) ReflectionHelper.findField(ItemSword.class, "field_150933_b").get(registryItem);
                }
                catch (Exception e)
                {
                    LogHandler.warning("Ran into an issue while getting ToolMaterials for " + itemSwordUid.toString());
                    LogHandler.warning(e.getLocalizedMessage());
                    LogHandler.warning("StackTrace:");
                    LogHandler.warning("------------------------------");
                    for (StackTraceElement element : e.getStackTrace())
                    {
                        LogHandler.warning(element.toString());
                    }
                }

                if (testToolMaterial(toolMaterial, itemSwordUid.toString()))
                {
                    LogHandler.abstractorVerboseLog("Material: " + toolMaterial.toString() + " Enchantability: " + toolMaterial.getEnchantability());

                    addTempRecipe(toolMaterial.getRepairItemStack().copy(), toolMaterial.getEnchantability());
                }
                else
                {
                    LogHandler.abstractorInvaildLog("Skipping adding recipe");
                }

                continue;
            }

            if (registryItem instanceof ItemArmor)
            {
                UniqueIdentifier itemArmorUid = GameRegistry.findUniqueIdentifierFor((ItemArmor) registryItem);

                LogHandler.abstractorVerboseLog("Found ItemArmor: " + itemArmorUid.toString() + " Attemting to get ArmorMaterial");

                ArmorMaterial armorMaterial = null;

                try
                {
                    armorMaterial = (ArmorMaterial) ReflectionHelper.findField(ItemArmor.class, "material", "field_77878_bZ").get(registryItem);
                }
                catch (Exception e)
                {
                    LogHandler.warning("Ran into an issue while getting ArmorMaterials for " + itemArmorUid.toString());
                    LogHandler.warning(e.getLocalizedMessage());
                    LogHandler.warning("StackTrace:");
                    LogHandler.warning("------------------------------");
                    for (StackTraceElement element : e.getStackTrace())
                    {
                        LogHandler.warning(element.toString());
                    }
                }

                if (testArmorMaterial(armorMaterial, itemArmorUid.toString()))
                {
                    LogHandler.abstractorVerboseLog("Material: " + armorMaterial.toString() + " Enchantability: " + armorMaterial.getEnchantability());

                    addTempRecipe(new ItemStack(armorMaterial.func_151685_b()), armorMaterial.getEnchantability());

                }
                else
                {
                    LogHandler.abstractorInvaildLog("Skipping adding recipe");
                }
                continue;
            }
        }

        LogHandler.trace("Found " + tempRecipeMap.size() + " items in ItemRegistry");

    }

    private static boolean testToolMaterial(ToolMaterial toolMaterial, String toolUid)
    {
        try
        {
            if (toolMaterial == null)
            {
                LogHandler.abstractorInvaildLog("ToolMaterial is null");
                return false;
            }

            if (toolMaterial.getEnchantability() <= 0)
            {
                LogHandler.abstractorInvaildLog("ToolMaterial " + toolMaterial.toString() + " have invaild Enchantability");
                return false;
            }

            if (toolMaterial.getRepairItemStack() == null || toolMaterial.getRepairItemStack().getItem() == null)
            {
                LogHandler.abstractorInvaildLog("ToolMaterial " + toolMaterial.toString() + " have an invalid Repair Item");
                return false;
            }

        }
        catch (Exception e)
        {
            LogHandler.warning("Ran into an issue while testing ToolMaterial for " + toolUid);
            LogHandler.warning(e.getLocalizedMessage());
            LogHandler.warning("StackTrace:");
            LogHandler.warning("------------------------------");
            for (StackTraceElement element : e.getStackTrace())
            {
                LogHandler.warning(element.toString());
            }
            return false;
        }

        LogHandler.abstractorVerboseLog("ToolMaterial " + toolMaterial.toString() + " for " + toolUid + " have passed the test");

        return true;
    }

    private static boolean testArmorMaterial(ArmorMaterial armorMaterial, String armorlUid)
    {
        try
        {
            if (armorMaterial == null)
            {
                LogHandler.abstractorInvaildLog("ArmorMaterial is null");
                return false;
            }

            if (armorMaterial.getEnchantability() <= 0)
            {
                LogHandler.abstractorInvaildLog("ArmorMaterial " + armorMaterial.toString() + " have invaild Enchantability");
                return false;
            }

            if (armorMaterial.func_151685_b() == null)
            {
                LogHandler.abstractorInvaildLog("ArmorMaterial " + armorMaterial.toString() + " have an invalid Repair Item");
                return false;
            }

        }
        catch (Exception e)
        {
            LogHandler.warning("Ran into an issue while testing ArmorMaterial for " + armorlUid);
            LogHandler.warning(e.getLocalizedMessage());
            LogHandler.warning("StackTrace:");
            LogHandler.warning("------------------------------");
            for (StackTraceElement element : e.getStackTrace())
            {
                LogHandler.warning(element.toString());
            }
            return false;
        }

        LogHandler.abstractorVerboseLog("ArmorMaterial " + armorMaterial.toString() + " for " + armorlUid + " have passed the test");

        return true;

    }

    private static void addTempRecipe(ItemStack itemStack, int enchantablilty)
    {
        tempRecipeMap.put(itemStack, (float) Math.ceil(enchantablilty / 10.0F));
    }

    private static void processTempRecipes()
    {
        HashMap<ItemStack, Float> processedRecipes = new HashMap<ItemStack, Float>();

        // Cleaning duplicates and getting the highest value.
        for (Entry<ItemStack, Float> tempEntry : tempRecipeMap.entrySet())
        {
            boolean newEntry = true;

            for (Entry<ItemStack, Float> processedEntry : processedRecipes.entrySet())
            {
                if (ItemStack.areItemStacksEqual(processedEntry.getKey(), tempEntry.getKey()))
                {
                    newEntry = false;

                    if (processedEntry.getValue() < tempEntry.getValue())
                    {
                        processedEntry.setValue(tempEntry.getValue());
                    }
                }
            }

            if (newEntry)
            {
                processedRecipes.put(tempEntry.getKey(), tempEntry.getValue());
            }

        }
        LogHandler.trace("Recipes after clean up: " + processedRecipes.size());

        // Adding recipes
        int recipesAdded = 0;
        for (Entry<ItemStack, Float> processedEntry : processedRecipes.entrySet())
        {
            int baseEssense = processedEntry.getValue().intValue();
            if (baseEssense > 0)
            {
                RecipesAbstractor.addBaseMaterial(processedEntry.getKey(), baseEssense);
                recipesAdded++;
            }
        }

        LogHandler.trace("Added recipes: " + recipesAdded);
    }

}
