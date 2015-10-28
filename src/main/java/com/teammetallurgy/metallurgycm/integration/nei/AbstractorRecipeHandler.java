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
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.teammetallurgy.metallurgycm.client.gui.GuiAbstractor;
import com.teammetallurgy.metallurgycm.crafting.RecipesAbstractor;

public class AbstractorRecipeHandler extends TemplateRecipeHandler
{

    public static ArrayList<CatalystPair> aCatalysts;

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals("metallurgycm.abstractor") && getClass() == AbstractorRecipeHandler.class)
        {
            // Normal recipes
            HashMap<ItemStack, Integer> abstractingRecipes = RecipesAbstractor.getAbstractingRecipes();
            for (Entry<ItemStack, Integer> recipe : abstractingRecipes.entrySet())
            {
                arecipes.add(new AbstractingPair(recipe.getKey(), recipe.getValue()));
            }

            // OreDic recipes
            HashMap<ArrayList<ItemStack>, Integer> oreDicAbstractingRecipes = RecipesAbstractor.getOreDicAbstractingRecipes();
            for (Entry<ArrayList<ItemStack>, Integer> recipe : oreDicAbstractingRecipes.entrySet())
            {
                arecipes.add(new AbstractingPair(recipe.getKey(), recipe.getValue()));
            }
        }
        else
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients)
    {
        if (inputId.equals("metallurgycm.catalyst") && getClass() == AbstractorRecipeHandler.class)
        {
            loadCraftingRecipes("metallurgycm.abstractor");
        }
        else
        {
            super.loadUsageRecipes(inputId, ingredients);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        // Normal recipes
        HashMap<ItemStack, Integer> abstractingRecipes = RecipesAbstractor.getAbstractingRecipes();
        for (Entry<ItemStack, Integer> recipe : abstractingRecipes.entrySet())
        {
            if (NEIServerUtils.areStacksSameType(recipe.getKey(), ingredient))
            {
                AbstractingPair abstractingPair = new AbstractingPair(recipe.getKey(), recipe.getValue());
                abstractingPair.setIngredientPermutation(Arrays.asList(abstractingPair.ingredient), ingredient);
                arecipes.add(abstractingPair);
            }
        }

        // OreDic recipes
        HashMap<ArrayList<ItemStack>, Integer> oreDicAbstractingRecipes = RecipesAbstractor.getOreDicAbstractingRecipes();
        for (Entry<ArrayList<ItemStack>, Integer> recipe : oreDicAbstractingRecipes.entrySet())
        {
            for (ItemStack recipeIngredient : recipe.getKey())
            {

                if (NEIServerUtils.areStacksSameType(recipeIngredient, ingredient))
                {
                    AbstractingPair abstractingPair = new AbstractingPair(recipe.getKey(), recipe.getValue());
                    abstractingPair.setIngredientPermutation(Arrays.asList(abstractingPair.ingredient), ingredient);
                    arecipes.add(abstractingPair);
                }
            }
        }
    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(50, 23, 18, 18), "metallurgycm.catalyst"));
        transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), "metallurgycm.abstractor"));
    }

    @Override
    public String getRecipeName()
    {
        return NEIClientUtils.translate("recipe.metallurgycm.abstractor");
    }

    @Override
    public TemplateRecipeHandler newInstance()
    {
        if (aCatalysts == null || aCatalysts.isEmpty())
        {
            findCatalysts();
        }
        return super.newInstance();
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiAbstractor.class;
    }

    @Override
    public String getGuiTexture()
    {
        return "metallurgycm:textures/gui/abstractor.png";
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
        return "metallurgycm.abstractor";
    }

    public static void findCatalysts()
    {
        aCatalysts = new ArrayList<CatalystPair>();
        HashMap<ItemStack, Integer> catalystBurning = RecipesAbstractor.getCatalystBurningRecipes();
        for (Entry<ItemStack, Integer> recipe : catalystBurning.entrySet())
        {
            aCatalysts.add(new CatalystPair(recipe.getKey(), recipe.getValue()));
        }

        // OreDic recipes
        HashMap<ArrayList<ItemStack>, Integer> oreDicCatalystBurningRecipes = RecipesAbstractor.getOreDicCatalystBurningRecipes();
        for (Entry<ArrayList<ItemStack>, Integer> recipe : oreDicCatalystBurningRecipes.entrySet())
        {
            aCatalysts.add(new CatalystPair(recipe.getKey(), recipe.getValue()));
        }
    }

    public class AbstractingPair extends CachedRecipe
    {
        PositionedStack ingredient;
        int result;

        public AbstractingPair(ItemStack ingredient, int result)
        {
            this.ingredient = new PositionedStack(ingredient, 51, 6);
            this.result = result;
        }

        public AbstractingPair(ArrayList<ItemStack> ingredients, int result)
        {

            this.ingredient = new PositionedStack(ingredients, 51, 6);
            this.result = result;
        }

        @Override
        public List<PositionedStack> getIngredients()
        {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(ingredient));
        }

        @Override
        public PositionedStack getResult()
        {
            return null;
        }

        @Override
        public PositionedStack getOtherStack()
        {
            return aCatalysts.get((cycleticks / 48) % aCatalysts.size()).ingredient;
        }

    }

    public static class CatalystPair
    {
        public PositionedStack ingredient;
        public int burnTime;

        public CatalystPair(ItemStack ingredient, int burnTime)
        {
            this.ingredient = new PositionedStack(ingredient, 51, 42);
            this.burnTime = burnTime;
        }

        public CatalystPair(ArrayList<ItemStack> ingredients, int burnTime)
        {
            this.ingredient = new PositionedStack(ingredients, 51, 42);
            this.burnTime = burnTime;

        }

    }

}
