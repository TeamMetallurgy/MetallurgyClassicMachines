package com.teammetallurgy.metallurgycm;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.teammetallurgy.metallurgycm.crafting.RecipesAbstractor;
import com.teammetallurgy.metallurgycm.crafting.RecipesCrusher;
import com.teammetallurgy.metallurgycm.handler.LogHandler;

import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;

public class MetallurgyCMImc
{

    public static void processImc(IMCEvent event)
    {
        List<IMCMessage> messages = event.getMessages();

        if (messages.size() <= 0)
        {
            LogHandler.info("No IMC messages received");
            return;
        }

        LogHandler.info("Processing " + messages.size() + " received IMC messages");

        for (IMCMessage message : messages)
        {
            if (message.key.equals("crusher"))
            {
                processCrusher(message);
            }
            else if (message.key.equals("abstractor"))
            {
                processAbstractor(message);
            }
            else if (message.key.equals("catalyst"))
            {
                processCatalyst(message);
            }
            else
            {
                LogHandler.warning("Unknown IMC message type " + message.key + " sent by " + message.getSender());
            }
        }

    }

    private static void processCrusher(IMCMessage message)
    {
        if (!message.isNBTMessage())
        {
            LogHandler.warning("Invaild crusher recipe IMC message type sent by " + message.getSender());
            return;
        }

        NBTTagCompound nbtTag = message.getNBTValue();

        if (nbtTag == null)
        {
            LogHandler.warning("Invaild crusher recipe NBT sent by " + message.getSender());
            return;
        }

        NBTTagCompound inputNbt = nbtTag.getCompoundTag("input");
        if (inputNbt == null)
        {
            LogHandler.warning("Missing crusher recipe input, message sent by " + message.getSender());
            LogHandler.warning(nbtTag.toString());
            return;
        }

        NBTTagCompound outputNbt = nbtTag.getCompoundTag("output");
        if (outputNbt == null)
        {
            LogHandler.warning("Missing crusher recipe output, message sent by " + message.getSender());
            LogHandler.warning(nbtTag.toString());
            return;
        }

        ItemStack input = ItemStack.loadItemStackFromNBT(inputNbt);
        if (input == null || input.getItem() == null)
        {
            LogHandler.warning("Invaild ItemStack for crusher recipe input, message sent by " + message.getSender());
            LogHandler.warning(nbtTag.toString());
            return;
        }

        ItemStack output = ItemStack.loadItemStackFromNBT(outputNbt);
        if (output == null || output.getItem() == null)
        {
            LogHandler.warning("Invaild ItemStack for crusher recipe output, message sent by " + message.getSender());
            LogHandler.warning(nbtTag.toString());
            return;
        }

        float experience = nbtTag.getFloat("experience");

        // Clamps experience between 0 and 10
        if (experience < 0)
        {
            experience = 0;
        }

        if (experience > 10)
        {
            experience = 10;
        }

        RecipesCrusher.addRecipe(input, output, experience);
    }

    private static void processAbstractor(IMCMessage message)
    {
        if (!message.isNBTMessage())
        {
            LogHandler.warning("Invaild abstractor recipe IMC message type sent by " + message.getSender());
            return;
        }

        NBTTagCompound nbtTag = message.getNBTValue();

        if (nbtTag == null)
        {
            LogHandler.warning("Invaild abstractor recipe NBT sent by " + message.getSender());
            return;
        }

        NBTTagCompound inputNbt = nbtTag.getCompoundTag("input");
        if (inputNbt == null)
        {
            LogHandler.warning("Missing abstractor recipe input, message sent by " + message.getSender());
            LogHandler.warning(nbtTag.toString());
            return;
        }

        ItemStack input = ItemStack.loadItemStackFromNBT(inputNbt);
        if (input == null || input.getItem() == null)
        {
            LogHandler.warning("Invaild ItemStack for abstractor recipe input, message sent by " + message.getSender());
            LogHandler.warning(nbtTag.toString());
            return;
        }

        int experience = nbtTag.getInteger("experience");

        // Clamps experience between 0 and 10
        if (experience < 0)
        {
            experience = 0;
        }

        if (experience > 10)
        {
            experience = 10;
        }

        RecipesAbstractor.addBaseMaterial(input, experience);
    }

    private static void processCatalyst(IMCMessage message)
    {
        if (!message.isNBTMessage())
        {
            LogHandler.warning("Invaild catalyst recipe IMC message type sent by " + message.getSender());
            return;
        }

        NBTTagCompound nbtTag = message.getNBTValue();

        if (nbtTag == null)
        {
            LogHandler.warning("Invaild catalyst recipe NBT sent by " + message.getSender());
            return;
        }

        NBTTagCompound cataylstNbt = nbtTag.getCompoundTag("input");
        if (cataylstNbt == null)
        {
            LogHandler.warning("Missing catalyst recipe input, message sent by " + message.getSender());
            LogHandler.warning(nbtTag.toString());
            return;
        }

        ItemStack catalyst = ItemStack.loadItemStackFromNBT(cataylstNbt);
        if (catalyst == null || catalyst.getItem() == null)
        {
            LogHandler.warning("Invaild ItemStack for catalyst recipe input, message sent by " + message.getSender());
            LogHandler.warning(nbtTag.toString());
            return;
        }

        int burnTicks = nbtTag.getInteger("burnTicks");

        // Clamps burnTicks between 1 and Integer.MAX_VALUE
        if (burnTicks < 1)
        {
            burnTicks = 1;
        }

        if (burnTicks > Integer.MAX_VALUE)
        {
            burnTicks = Integer.MAX_VALUE;
        }

        RecipesAbstractor.addCatalyst(catalyst, burnTicks);
    }
}
