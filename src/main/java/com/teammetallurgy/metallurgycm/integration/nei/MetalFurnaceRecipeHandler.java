package com.teammetallurgy.metallurgycm.integration.nei;

import java.awt.Rectangle;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.recipe.FurnaceRecipeHandler;

import com.teammetallurgy.metallurgycm.client.gui.GuiMetalFurnace;

public class MetalFurnaceRecipeHandler extends FurnaceRecipeHandler
{
    @Override
    @SuppressWarnings("unchecked")
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals("metallurgy.metal.furnace") && getClass() == MetalFurnaceRecipeHandler.class)
        {
            Map<ItemStack, ItemStack> recipes = FurnaceRecipes.smelting().getSmeltingList();
            for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
                arecipes.add(new SmeltingPair(recipe.getKey(), recipe.getValue()));
        }
        else
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients)
    {
        if (inputId.equals("fuel") && getClass() == MetalFurnaceRecipeHandler.class)
        {
            loadCraftingRecipes("metallurgy.metal.furnace");
        }
        else
        {
            super.loadUsageRecipes(inputId, ingredients);
        }

    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(50, 23, 18, 18), "fuel"));
        transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), "metallurgy.metal.furnace"));
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiMetalFurnace.class;
    }

    @Override
    public String getRecipeName()
    {
        return NEIClientUtils.translate("recipe.metallurgycm.metal.furnace");
    }

    @Override
    public String getOverlayIdentifier()
    {
        return "metallurgy.metal.furnace";
    }
}
