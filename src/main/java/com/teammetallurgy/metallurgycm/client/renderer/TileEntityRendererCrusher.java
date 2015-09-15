package com.teammetallurgy.metallurgycm.client.renderer;

import java.util.Locale;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.client.model.ModelCrusher;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityCrusher;

public class TileEntityRendererCrusher extends TileEntitySpecialRenderer
{

    private ModelCrusher modelCrusher = new ModelCrusher();

    private ResourceLocation stoneCrusherTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/crusher/stone_model.png");
    private ResourceLocation copperCrusherTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/crusher/copper_model.png");
    private ResourceLocation bronzeCrusherTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/crusher/bronze_model.png");
    private ResourceLocation ironCrusherTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/crusher/iron_model.png");
    private ResourceLocation steelCrusherTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/crusher/steel_model.png");

    public void renderTileEntityAt(TileEntityCrusher tileEntityCrusher, double x, double y, double z, float renderTicks)
    {

        ForgeDirection facing = tileEntityCrusher.getFacing();

        double offsetX = 0;
        double offsetZ = 0;
        int angle = 0;

        if (facing == ForgeDirection.SOUTH)
        {
            offsetZ = 1;
        }
        else if (facing == ForgeDirection.WEST)
        {
            angle = 90;
        }
        else if (facing == ForgeDirection.NORTH)
        {
            angle = 180;
            offsetX = 1;
        }

        else if (facing == ForgeDirection.EAST)
        {
            angle = 270;
            offsetX = 1;
            offsetZ = 1;
        }

        GL11.glPushMatrix();
        GL11.glTranslated(x + offsetX, y + 1, z + offsetZ);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0F);
        GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);

        bindCrusherTexture(tileEntityCrusher.getBlockMetadata());
        modelCrusher.renderAll();
        GL11.glPopMatrix();
    }

    private void bindCrusherTexture(int meta)
    {
        switch (meta)
        {
            case 1:
                bindTexture(copperCrusherTexture);
                break;
            case 2:
                bindTexture(bronzeCrusherTexture);
                break;
            case 3:
                bindTexture(ironCrusherTexture);
                break;
            case 4:
                bindTexture(steelCrusherTexture);
                break;
            default:
                bindTexture(stoneCrusherTexture);

        }
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float renderTicks)
    {
        renderTileEntityAt((TileEntityCrusher) tileEntity, x, y, z, renderTicks);

    }

}
