package com.teammetallurgy.metallurgycm.client.gui;

import java.util.ArrayList;
import java.util.Locale;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.teammetallurgy.metallurgycm.MetallurgyCM;
import com.teammetallurgy.metallurgycm.block.BlockBaseMachine;
import com.teammetallurgy.metallurgycm.inventory.ContainerSmelter;
import com.teammetallurgy.metallurgycm.tileentity.TileEntitySmelter;

public class GuiSmelter extends GuiContainer
{

    private ResourceLocation texture = new ResourceLocation(MetallurgyCM.MOD_ID.toLowerCase(Locale.US) + ":textures/gui/smelter.png");
    private TileEntitySmelter tileEntity;

    public GuiSmelter(InventoryPlayer inventoryPlayer, TileEntitySmelter tileEntitySmelter)
    {
        super(new ContainerSmelter(inventoryPlayer, tileEntitySmelter));
        tileEntity = tileEntitySmelter;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTick, int mouseX, int mouseZ)
    {
        mc.renderEngine.bindTexture(texture);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (tileEntity.isRunning())
        {
            int scale = tileEntity.getScaledProcessingTime(24);
            drawTexturedModalRect(guiLeft + 59, guiTop + 33, 176, 14, scale + 1, 16);
        }

        if (tileEntity.fluidLevel > 0)
        {
            int scale = tileEntity.getScaledFluidLevel(63);

            drawTexturedModalRect(guiLeft + 144, guiTop + 10 + 63 - scale, 176, 93 - scale, 16, scale + 1);
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

    @Override
    public void drawScreen(int mouseX, int mouseZ, float particalRenderTicks)
    {
        super.drawScreen(mouseX, mouseZ, particalRenderTicks);

        // Drawing Tank's tooltip
        int x = mouseX - guiLeft;
        int z = mouseZ - guiTop;

        boolean isMouseInTank = x >= 144 && x <= 159 && z >= 11 && z <= 74;
        if (isMouseInTank)
        {
            ArrayList<String> info = new ArrayList<String>();
            info.add(StatCollector.translateToLocal("tooltip.metallurgymc.lava.level") + ": " + tileEntity.fluidLevel);
            info.add(StatCollector.translateToLocal("tooltip.metallurgymc.smelter.capacity") + ": " + tileEntity.maxCapacity);
            drawHoveringText(info, mouseX, mouseZ, fontRendererObj);
        }
    }

}
