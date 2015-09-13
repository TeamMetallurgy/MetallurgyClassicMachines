package com.teammetallurgy.metallurgycm.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.teammetallurgy.metallurgycm.MetallurgyCMBlocks;
import com.teammetallurgy.metallurgycm.client.renderer.ItemRendererCrusher;
import com.teammetallurgy.metallurgycm.client.renderer.TileEntityRendererCrusher;
import com.teammetallurgy.metallurgycm.tileentity.TileEntityCrusher;

import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public void init()
    {
        super.init();

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrusher.class, new TileEntityRendererCrusher());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MetallurgyCMBlocks.crusher), new ItemRendererCrusher());
    }
}
