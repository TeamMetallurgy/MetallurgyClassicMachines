package com.teammetallurgy.metallurgycm;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.teammetallurgy.metallurgycm.handler.ConfigHandler;

import cpw.mods.fml.common.registry.GameRegistry;

public class MetallurgyCMRecipes
{
    public static void init()
    {
        // Abstractor recipes
        for (int i = 0; i < ConfigHandler.abstractorTypes.length; i++)
        {
            String ingotName = "ingot" + ConfigHandler.abstractorTypes[i].replace(" ", "");
            ItemStack abstractorStack = new ItemStack(MetallurgyCMBlocks.abstractor, 1, i);

            if (i == 0)
            {
                GameRegistry.addRecipe(new ShapedOreRecipe(abstractorStack, "iii", "i i", "iii", 'i', ingotName));
            }
            else
            {
                ItemStack previousAbstractor = new ItemStack(MetallurgyCMBlocks.abstractor, 1, i - 1);
                GameRegistry.addRecipe(new ShapedOreRecipe(abstractorStack, "iii", "ipi", "iii", 'i', ingotName, 'p', previousAbstractor));
            }
        }

        // crusher recipes
        for (int i = 0; i < ConfigHandler.crusherTypes.length; i++)
        {
            String ingotName = "ingot" + ConfigHandler.crusherTypes[i].replace(" ", "");
            ItemStack crusherStack = new ItemStack(MetallurgyCMBlocks.crusher, 1, i);

            if (i == 0)
            {
                GameRegistry.addRecipe(new ShapedOreRecipe(crusherStack, "csc", "sfs", "csc", 'c', "cobblestone", 's', "stickWood", 'f', new ItemStack(Blocks.furnace)));
            }
            else
            {
                ItemStack previousCrusher = new ItemStack(MetallurgyCMBlocks.crusher, 1, i - 1);
                GameRegistry.addRecipe(new ShapedOreRecipe(crusherStack, "iii", "ipi", "iii", 'i', ingotName, 'p', previousCrusher));
            }
        }

        // Metal chests recipes
        for (int i = 0; i < ConfigHandler.chestsTypes.length; i++)
        {
            String ingotName = "ingot" + ConfigHandler.chestsTypes[i].replace(" ", "");
            ItemStack chestStack = new ItemStack(MetallurgyCMBlocks.metalChest, 1, i);

            if (i == 0)
            {
                GameRegistry.addRecipe(new ShapedOreRecipe(chestStack, "iii", "ici", "iii", 'i', ingotName, 'c', new ItemStack(Blocks.chest)));
            }
            else
            {
                ItemStack previousChest = new ItemStack(MetallurgyCMBlocks.metalChest, 1, i - 1);
                GameRegistry.addRecipe(new ShapedOreRecipe(chestStack, "iii", "ipi", "iii", 'i', ingotName, 'p', previousChest));
            }
        }

        // Metal Furnace
        for (int i = 0; i < ConfigHandler.furnaceTypes.length; i++)
        {
            String ingotName = "ingot" + ConfigHandler.furnaceTypes[i].replace(" ", "");
            ItemStack furnaceStack = new ItemStack(MetallurgyCMBlocks.metalFurnace, 1, i);

            if (i == 0)
            {
                GameRegistry.addRecipe(new ShapedOreRecipe(furnaceStack, "iii", "ifi", "iii", 'i', ingotName, 'f', new ItemStack(Blocks.furnace)));
            }
            else
            {
                ItemStack previousFurnace = new ItemStack(MetallurgyCMBlocks.metalFurnace, 1, i - 1);
                GameRegistry.addRecipe(new ShapedOreRecipe(furnaceStack, "iii", "ipi", "iii", 'i', ingotName, 'p', previousFurnace));
            }
        }

        // Smelter recipes
        for (int i = 0; i < ConfigHandler.smelterTypes.length; i++)
        {
            String ingotName = "ingot" + ConfigHandler.abstractorTypes[i].replace(" ", "");
            ItemStack smelterStack = new ItemStack(MetallurgyCMBlocks.smelter, 1, i);

            if (i == 0)
            {
                GameRegistry.addRecipe(new ShapedOreRecipe(smelterStack, "iii", "i i", "iii", 'i', ingotName));
            }
            else
            {
                ItemStack previousSmelter = new ItemStack(MetallurgyCMBlocks.abstractor, 1, i - 1);
                GameRegistry.addRecipe(new ShapedOreRecipe(smelterStack, "iii", "ipi", "iii", 'i', ingotName, 'p', previousSmelter));
            }
        }
    }
}
