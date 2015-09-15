package com.teammetallurgy.metallurgycm.client.gui;

import java.util.Locale;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.inventory.ContainerAbstractor;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityAbstractor;

public class GuiAbstractor extends GuiContainer
{
    ResourceLocation texture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/gui/abstractor.png");

    public GuiAbstractor(InventoryPlayer inventoryPlayer, TileEntityAbstractor tileEntityAbstractor)
    {
        super(new ContainerAbstractor(inventoryPlayer, tileEntityAbstractor));
        // TODO
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTick, int mouseX, int mouseZ)
    {
        mc.renderEngine.bindTexture(texture);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

    }

}
