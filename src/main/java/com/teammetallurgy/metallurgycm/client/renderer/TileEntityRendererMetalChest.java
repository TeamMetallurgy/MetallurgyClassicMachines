package com.teammetallurgy.metallurgycm.client.renderer;

import java.util.Locale;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalChest;

public class TileEntityRendererMetalChest extends TileEntitySpecialRenderer
{
    private ModelChest modelChest = new ModelChest();

    private ResourceLocation brassTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/chests/brass.png");
    private ResourceLocation silverTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/chests/silver.png");
    private ResourceLocation goldTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/chests/gold.png");
    private ResourceLocation electrumTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/chests/electrum.png");
    private ResourceLocation platinumTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/chests/platinum.png");

    public void renderTileEntityAt(TileEntityMetalChest tileEntityMetalChest, double x, double y, double z, float renderTicks)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y + 1, z + 1);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0F);
        bindChestTexture(tileEntityMetalChest.getBlockMetadata());
        modelChest.renderAll();
        GL11.glPopMatrix();
    }

    private void bindChestTexture(int meta)
    {
        switch (meta)
        {
            case 1:
                bindTexture(silverTexture);
                break;
            case 2:
                bindTexture(goldTexture);
                break;
            case 3:
                bindTexture(electrumTexture);
                break;
            case 4:
                bindTexture(platinumTexture);
                break;
            default:
                bindTexture(brassTexture);
        }
    }

    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float renderTicks)
    {
        renderTileEntityAt((TileEntityMetalChest) entity, x, y, z, renderTicks);
    }

}
