package com.teammetallurgy.metallurgycm.client.gui;

import java.util.Locale;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.block.BlockBaseMachine;
import com.teammetallurgy.metallurgycm.inventory.ContainerAbstractor;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityAbstractor;

public class GuiAbstractor extends GuiContainer
{
    private ResourceLocation texture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/gui/abstractor.png");
    private TileEntityAbstractor tileEntity;

    public GuiAbstractor(InventoryPlayer inventoryPlayer, TileEntityAbstractor tileEntityAbstractor)
    {
        super(new ContainerAbstractor(inventoryPlayer, tileEntityAbstractor));
        tileEntity = tileEntityAbstractor;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTick, int mouseX, int mouseZ)
    {
        mc.renderEngine.bindTexture(texture);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ)
    {
        String unlocalizedContainerName = ((BlockBaseMachine) tileEntity.getBlockType()).getUnlocalizedContainerName(tileEntity.getBlockMetadata());
        this.fontRendererObj.drawString(StatCollector.translateToLocal(unlocalizedContainerName), this.xSize / 2 - this.fontRendererObj.getStringWidth(unlocalizedContainerName) / 2, 6, 0x404040);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);
    }

}
