package com.teammetallurgy.metallurgycm.integration.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.FurnaceRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.teammetallurgy.metallurgycm.client.gui.GuiCrusher;
import com.teammetallurgy.metallurgycm.crafting.RecipesCrusher;

public class CrusherRecipeHandler extends TemplateRecipeHandler
{
    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals("metallurgycm.crusher") && getClass() == CrusherRecipeHandler.class)
        {
            // Normal recipes
            HashMap<ItemStack, ItemStack> crushingRecipes = RecipesCrusher.getCrushingRecipes();
            for (Entry<ItemStack, ItemStack> recipe : crushingRecipes.entrySet())
            {
                arecipes.add(new CrushingPair(recipe.getKey(), recipe.getValue()));
            }

            // OreDic recipes
            HashMap<ArrayList<ItemStack>, ItemStack> oreDicCrushingRecipes = RecipesCrusher.getOreDicCrushingRecipes();
            for (Entry<ArrayList<ItemStack>, ItemStack> recipe : oreDicCrushingRecipes.entrySet())
            {
                arecipes.add(new CrushingPair(recipe.getKey(), recipe.getValue()));
            }
        }
        else
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        // Normal recipes
        HashMap<ItemStack, ItemStack> crushingRecipes = RecipesCrusher.getCrushingRecipes();
        for (Entry<ItemStack, ItemStack> recipe : crushingRecipes.entrySet())
        {
            if (NEIServerUtils.areStacksSameType(recipe.getValue(), result))
            {
                arecipes.add(new CrushingPair(recipe.getKey(), recipe.getValue()));
            }
        }

        // OreDic recipes
        HashMap<ArrayList<ItemStack>, ItemStack> oreDicCrushingRecipes = RecipesCrusher.getOreDicCrushingRecipes();
        for (Entry<ArrayList<ItemStack>, ItemStack> recipe : oreDicCrushingRecipes.entrySet())
        {
            if (NEIServerUtils.areStacksSameType(recipe.getValue(), result))
            {
                arecipes.add(new CrushingPair(recipe.getKey(), recipe.getValue()));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        HashMap<ItemStack, ItemStack> crushingRecipes = RecipesCrusher.getCrushingRecipes();
        for (Entry<ItemStack, ItemStack> recipe : crushingRecipes.entrySet())
        {
            if (NEIServerUtils.areStacksSameType(recipe.getKey(), ingredient))
            {
                CrushingPair crushingPair = new CrushingPair(recipe.getKey(), recipe.getValue());
                crushingPair.setIngredientPermutation(Arrays.asList(crushingPair.ingredient), ingredient);
                arecipes.add(crushingPair);
            }
        }

        // OreDic recipes
        HashMap<ArrayList<ItemStack>, ItemStack> oreDicCrushingRecipes = RecipesCrusher.getOreDicCrushingRecipes();
        for (Entry<ArrayList<ItemStack>, ItemStack> recipe : oreDicCrushingRecipes.entrySet())
        {
            for (ItemStack recipeIngredient : recipe.getKey())
            {

                if (NEIServerUtils.areStacksSameType(recipeIngredient, ingredient))
                {
                    CrushingPair crushingPair = new CrushingPair(recipe.getKey(), recipe.getValue());
                    crushingPair.setIngredientPermutation(Arrays.asList(crushingPair.ingredient), ingredient);
                    arecipes.add(crushingPair);
                }
            }
        }
    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(50, 23, 18, 18), "fuel"));
        transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), "metallurgycm.crusher"));
    }

    @Override
    public String getRecipeName()
    {
        return NEIClientUtils.translate("recipe.metallurgycm.crusher");
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiCrusher.class;
    }

    @Override
    public String getGuiTexture()
    {
        return "metallurgycm:textures/gui/crusher.png";
    }

    @Override
    public void drawExtras(int recipe)
    {
        drawProgressBar(51, 25, 176, 0, 14, 14, 48, 7);
        drawProgressBar(74, 23, 176, 14, 24, 16, 48, 0);
    }

    @Override
    public String getOverlayIdentifier()
    {
        return "metallurgycm.crusher";
    }

    public class CrushingPair extends CachedRecipe
    {
        PositionedStack ingredient;
        PositionedStack result;

        public CrushingPair(ItemStack ingredient, ItemStack result)
        {
            this.ingredient = new PositionedStack(ingredient, 51, 6);
            this.result = new PositionedStack(result, 111, 24);
        }

        public CrushingPair(ArrayList<ItemStack> ingredients, ItemStack result)
        {
            this.ingredient = new PositionedStack(ingredients, 51, 6);
            this.result = new PositionedStack(result, 111, 24);
        }

        @Override
        public List<PositionedStack> getIngredients()
        {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(ingredient));
        }

        @Override
        public PositionedStack getResult()
        {
            return result;
        }

        @Override
        public PositionedStack getOtherStack()
        {
            return FurnaceRecipeHandler.afuels.get((cycleticks / 48) % FurnaceRecipeHandler.afuels.size()).stack;
        }

    }

}
