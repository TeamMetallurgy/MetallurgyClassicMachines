package com.teammetallurgy.metallurgycm.client.renderer;

import java.util.Locale;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.client.model.ModelCrusher;

import cpw.mods.fml.client.FMLClientHandler;

public class ItemRendererCrusher implements IItemRenderer
{

    private ModelCrusher modelCrusher = new ModelCrusher();

    private ResourceLocation stoneCrusherTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/crusher/stone_model.png");
    private ResourceLocation copperCrusherTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/crusher/copper_model.png");
    private ResourceLocation bronzeCrusherTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/crusher/bronze_model.png");
    private ResourceLocation ironCrusherTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/crusher/iron_model.png");
    private ResourceLocation steelCrusherTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/crusher/steel_model.png");

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
        bindCrusherTexture(item.getItemDamage());
        modelCrusher.renderAll();
        GL11.glPopMatrix();
    }

    private void bindCrusherTexture(int meta)
    {
        switch (meta)
        {
            case 1:
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(copperCrusherTexture);
                break;
            case 2:
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(bronzeCrusherTexture);
                break;
            case 3:
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(ironCrusherTexture);
                break;
            case 4:
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(steelCrusherTexture);
                break;
            default:
                FMLClientHandler.instance().getClient().renderEngine.bindTexture(stoneCrusherTexture);

        }
    }

}
