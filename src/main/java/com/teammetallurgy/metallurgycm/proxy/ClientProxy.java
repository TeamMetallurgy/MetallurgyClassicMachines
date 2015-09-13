package com.teammetallurgy.metallurgycm.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.teammetallurgy.metallurgycm.MetallurgyCMBlocks;
import com.teammetallurgy.metallurgycm.client.renderer.ItemRendererCrusher;
import com.teammetallurgy.metallurgycm.client.renderer.ItemRendererMetalChest;
import com.teammetallurgy.metallurgycm.client.renderer.TileEntityRendererCrusher;
import com.teammetallurgy.metallurgycm.client.renderer.TileEntityRendererMetalChest;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityCrusher;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityMetalChest;

import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public void init()
    {
        super.init();

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrusher.class, new TileEntityRendererCrusher());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMetalChest.class, new TileEntityRendererMetalChest());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MetallurgyCMBlocks.crusher), new ItemRendererCrusher());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MetallurgyCMBlocks.metalChest), new ItemRendererMetalChest());
    }
}
