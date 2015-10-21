package com.teammetallurgy.metallurgycm.client.gui;

import java.util.Locale;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.block.BlockBaseMachine;
import com.teammetallurgy.metallurgycm.inventory.ContainerCrusher;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityCrusher;

public class GuiCrusher extends GuiContainer
{
    private ResourceLocation texture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/gui/crusher.png");
    private TileEntityCrusher tileEntity;

    public GuiCrusher(InventoryPlayer inventoryPlayer, TileEntityCrusher tileEntityCrusher)
    {
        super(new ContainerCrusher(inventoryPlayer, tileEntityCrusher));
        tileEntity = tileEntityCrusher;
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
