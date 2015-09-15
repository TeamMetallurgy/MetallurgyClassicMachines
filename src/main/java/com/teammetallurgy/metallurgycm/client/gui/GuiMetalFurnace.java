package com.teammetallurgy.metallurgycm.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.teammetallurgy.metallurgycm.inventory.ContainerMetalFurnace;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalFurnace;

public class GuiMetalFurnace extends GuiContainer
{

    ResourceLocation texture = new ResourceLocation("textures/gui/container/furnace.png");

    public GuiMetalFurnace(InventoryPlayer inventoryPlayer, TileEntityMetalFurnace tileEntityMetalFurnace)
    {
        super(new ContainerMetalFurnace(inventoryPlayer, tileEntityMetalFurnace));
        // TODO
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTick, int mouseX, int mouseZ)
    {
        mc.renderEngine.bindTexture(texture);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

    }

}
