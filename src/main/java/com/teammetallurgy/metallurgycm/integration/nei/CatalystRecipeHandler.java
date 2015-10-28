package com.teammetallurgy.metallurgycm.integration.nei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.PositionedStack;

import com.teammetallurgy.metallurgycm.crafting.RecipesAbstractor;

public class CatalystRecipeHandler extends AbstractorRecipeHandler
{
    private ArrayList<AbstractingPair> allAbstractingRecipes = new ArrayList<AbstractingPair>();

    public CatalystRecipeHandler()
    {
        super();
        loadAbstractorRecipes();
    }

    private void loadAbstractorRecipes()
    {
        // Normal recipes
        HashMap<ItemStack, Integer> abstractingRecipes = RecipesAbstractor.getAbstractingRecipes();
        for (Entry<ItemStack, Integer> recipe : abstractingRecipes.entrySet())
        {
            allAbstractingRecipes.add(new AbstractingPair(recipe.getKey(), recipe.getValue()));
        }

        // OreDic recipes
        HashMap<ArrayList<ItemStack>, Integer> oreDicAbstractingRecipes = RecipesAbstractor.getOreDicAbstractingRecipes();
        for (Entry<ArrayList<ItemStack>, Integer> recipe : oreDicAbstractingRecipes.entrySet())
        {
            allAbstractingRecipes.add(new AbstractingPair(recipe.getKey(), recipe.getValue()));
        }
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals("metallurgycm.catalyst") && getClass() == CatalystRecipeHandler.class)
        {
            for (CatalystPair catalyst : aCatalysts)
            {
                arecipes.add(new RecipeCatalystPair(catalyst));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        for (CatalystPair catalyst : aCatalysts)
        {
            for (PositionedStack pairIngredient : catalyst.ingredients)
            {
                if (pairIngredient.contains(ingredient))
                {
                    arecipes.add(new RecipeCatalystPair(catalyst));
                }
            }
        }
    }

    @Override
    public String getRecipeName()
    {
        return NEIClientUtils.translate("recipe.metallurgycm.catalyst");
    }

    @Override
    public String getOverlayIdentifier()
    {
        return "metallurgycm.catalyst";
    }

    public class RecipeCatalystPair extends CachedRecipe
    {

        private CatalystPair catatlystPair;

        public RecipeCatalystPair(CatalystPair catalystPair)
        {
            this.catatlystPair = catalystPair;
        }

        @Override
        public PositionedStack getIngredient()
        {
            AbstractingPair recipe = allAbstractingRecipes.get(cycleticks / 48 % allAbstractingRecipes.size());
            return recipe.ingredient.get(cycleticks / 48 % recipe.ingredient.size());
        }

        @Override
        public PositionedStack getResult()
        {
            return null;
        }

        @Override
        public List<PositionedStack> getOtherStacks()
        {
            return this.catatlystPair.ingredients;
        }

    }

}
