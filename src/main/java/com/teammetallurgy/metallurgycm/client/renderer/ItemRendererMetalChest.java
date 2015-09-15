package com.teammetallurgy.metallurgycm.client.renderer;

import java.util.Locale;

import net.minecraft.client.model.ModelChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.teammetallurgy.metallurgycm.MetallurgyCM;

import cpw.mods.fml.client.FMLClientHandler;

public class ItemRendererMetalChest implements IItemRenderer
{

    private ModelChest modelChest = new ModelChest();

    private ResourceLocation brassTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/chest/brass_model.png");
    private ResourceLocation silverTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/chest/silver_model.png");
    private ResourceLocation goldTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/chest/gold_model.png");
    private ResourceLocation electrumTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/chest/electrum_model.png");
    private ResourceLocation platinumTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/chest/platinum_model.png");

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        GL11.glPushMatrix();
        if (type == ItemRenderType.INVENTORY)
        {
            GL11.glTranslated(0, 0.1, 0);
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        }
        else
        {
            GL11.glRotatef(180, 1.0F, 0.0F, 0.0F);
            GL11.glTranslated(0, -1, -1);
        }
        bindChestTexture(item.getItemDamage());
        modelChest.renderAll();
        GL11.glPopMatrix();
    }

    private void bindChestTexture(int meta)
    {
        switch (meta)
        {
            case 1:
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(silverTexture);
                break;
            case 2:
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(goldTexture);
                break;
            case 3:
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(electrumTexture);
                break;
            case 4:
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(platinumTexture);
                break;
            default:
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(brassTexture);
        }
    }

}
