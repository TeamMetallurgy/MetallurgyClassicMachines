package com.teammetallurgy.metallurgycm.client.renderer;

import java.util.Locale;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalChest;

public class TileEntityRendererMetalChest extends TileEntitySpecialRenderer
{
    private ModelChest modelChest = new ModelChest();

    private ResourceLocation brassTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/chest/brass_model.png");
    private ResourceLocation silverTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/chest/silver_model.png");
    private ResourceLocation goldTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/chest/gold_model.png");
    private ResourceLocation electrumTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/chest/electrum_model.png");
    private ResourceLocation platinumTexture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/blocks/chest/platinum_model.png");

    public void renderTileEntityAt(TileEntityMetalChest tileEntityMetalChest, double x, double y, double z, float renderTicks)
    {

        ForgeDirection facing = tileEntityMetalChest.getFacing();

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

        float lidPrev = tileEntityMetalChest.lidPreviousOpenRatio;
        float lidCurrent = tileEntityMetalChest.lidOpenRatio;
        float lidRotationAngleX = lidPrev + (lidCurrent - lidPrev) * renderTicks;

        lidRotationAngleX = 1.0F - lidRotationAngleX;
        lidRotationAngleX = 1.0F - lidRotationAngleX * lidRotationAngleX * lidRotationAngleX;
        lidRotationAngleX *= Math.PI;
        modelChest.chestLid.rotateAngleX = -(lidRotationAngleX / 2.0F);

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
