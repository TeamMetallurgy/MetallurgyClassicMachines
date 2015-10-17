package com.teammetallurgy.metallurgycm;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.teammetallurgy.metallurgycm.crafting.RecipesAbstractor;
import com.teammetallurgy.metallurgycm.crafting.RecipesCrusher;

public class MetallurgyCMRecipes
{
    public static void init()
    {
        RecipesAbstractor.addBaseMaterial(new ItemStack(Items.apple), 2.0F);
        RecipesAbstractor.addCatalyst(new ItemStack(Items.cooked_beef), 1.0F);

        RecipesCrusher.addRecipe(new ItemStack(Items.apple), new ItemStack(Items.cooked_beef), 2.0F);
    }
}
