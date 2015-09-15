package com.teammetallurgy.metallurgycm.client.gui;

import java.util.Locale;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.inventory.ContainerMetalChest;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalChest;

public class GuiMetalChest extends GuiContainer
{

    private TileEntityMetalChest tileEntity;
    private ResourceLocation texture;

    public GuiMetalChest(InventoryPlayer inventoryPlayer, TileEntityMetalChest tileEntityMetalChest)
    {
        super(new ContainerMetalChest(inventoryPlayer, tileEntityMetalChest));
        tileEntity = tileEntityMetalChest;

        int meta = tileEntity.getBlockMetadata();
        switch (meta)
        {
            case 0:
                texture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/gui/chest/brass.png");
                break;
            case 1:
                texture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/gui/chest/silver.png");
                break;
            case 2:
                texture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/gui/chest/gold.png");
                break;
            case 3:
                texture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/gui/chest/electrum.png");
                break;
            case 4:
                texture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/gui/chest/platinum.png");
                break;

            default:
                texture = field_147001_a;
        }

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTick, int mouseX, int mouseZ)
    {
        mc.renderEngine.bindTexture(texture);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

    }

}
