package com.teammetallurgy.metallurgycm.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.teammetallurgy.metallurgycm.block.BlockBaseMachine;
import com.teammetallurgy.metallurgycm.inventory.ContainerMetalFurnace;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalFurnace;

public class GuiMetalFurnace extends GuiContainer
{

    private ResourceLocation texture = new ResourceLocation("textures/gui/container/furnace.png");
    private TileEntityMetalFurnace tileEntity;

    public GuiMetalFurnace(InventoryPlayer inventoryPlayer, TileEntityMetalFurnace tileEntityMetalFurnace)
    {
        super(new ContainerMetalFurnace(inventoryPlayer, tileEntityMetalFurnace));
        tileEntity = tileEntityMetalFurnace;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTick, int mouseX, int mouseZ)
    {
        mc.renderEngine.bindTexture(texture);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (tileEntity.currentlyBurning())
        {
            int scale = tileEntity.getScaledProcessingTime(24);
            drawTexturedModalRect(guiLeft + 79, guiTop + 34, 176, 14, scale + 1, 16);

            scale = tileEntity.getScaledBurningTicks(13);
            drawTexturedModalRect(guiLeft + 56, guiTop + 36 + 12 - scale, 176, 12 - scale, 14, scale + 1);
        }

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ)
    {
        String unlocalizedContainerName = ((BlockBaseMachine) tileEntity.getBlockType()).getUnlocalizedContainerName(tileEntity.getBlockMetadata());
        String localizedName = StatCollector.translateToLocal(unlocalizedContainerName);
        this.fontRendererObj.drawString(localizedName, this.xSize / 2 - this.fontRendererObj.getStringWidth(localizedName) / 2, 6, 0x404040);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);
    }

}
