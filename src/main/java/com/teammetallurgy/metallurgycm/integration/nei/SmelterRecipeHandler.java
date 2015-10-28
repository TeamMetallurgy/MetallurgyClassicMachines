package com.teammetallurgy.metallurgycm.integration.nei;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.teammetallurgy.metallurgycm.client.gui.GuiSmelter;

public class SmelterRecipeHandler extends TemplateRecipeHandler
{

    @Override
    @SuppressWarnings("unchecked")
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals("metallurgycm.smelter") && getClass() == SmelterRecipeHandler.class)
        {
            Map<ItemStack, ItemStack> recipes = FurnaceRecipes.smelting().getSmeltingList();
            for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
            {

                arecipes.add(new LavaSmeltingPair(recipe.getKey(), recipe.getValue()));
            }
        }
        else
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadCraftingRecipes(ItemStack result)
    {
        Map<ItemStack, ItemStack> recipes = FurnaceRecipes.smelting().getSmeltingList();
        for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
        {
            if (NEIServerUtils.areStacksSameType(result, recipe.getValue()))
            {
                arecipes.add(new LavaSmeltingPair(recipe.getKey(), recipe.getValue()));
            }
        }
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void loadUsageRecipes(ItemStack ingredient)
    {
        Map<ItemStack, ItemStack> recipes = FurnaceRecipes.smelting().getSmeltingList();
        for (Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
        {
            if (NEIServerUtils.areStacksSameTypeCrafting(ingredient, recipe.getKey()))
            {
                LavaSmeltingPair lavaSmeltingPair = new LavaSmeltingPair(recipe.getKey(), recipe.getValue());
                List ingredientList = Arrays.asList(new PositionedStack[] { lavaSmeltingPair.ingredient });
                lavaSmeltingPair.setIngredientPermutation(ingredientList, ingredient);
                arecipes.add(lavaSmeltingPair);
            }
        }
    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(56, 23, 24, 18), "metallurgycm.smelter"));
    }

    @Override
    public String getRecipeName()
    {
        return NEIClientUtils.translate("recipe.metallurgycm.smelter");
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiSmelter.class;
    }

    @Override
    public String getGuiTexture()
    {
        return "metallurgycm:textures/gui/smelter_nei.png";
    }

    @Override
    public void drawExtras(int recipe)
    {
        drawProgressBar(74, 23, 176, 14, 25, 16, 48, 0);
    }

    @Override
    public String getOverlayIdentifier()
    {
        return "metallurgycm.smelter";
    }

    public class LavaSmeltingPair extends CachedRecipe
    {
        private PositionedStack ingredient;
        private PositionedStack result;

        public LavaSmeltingPair(ItemStack ingredient, ItemStack result)
        {
            this.ingredient = new PositionedStack(ingredient, 51, 24);
            this.result = new PositionedStack(result, 111, 24);
        }

        @Override
        public PositionedStack getIngredient()
        {
            return ingredient;
        }

        @Override
        public PositionedStack getResult()
        {
            return result;
        }

    }

}
