package com.danriweb.historic_stronghold.client;

import com.danriweb.historic_stronghold.registry.ModEntityTypes;
import net.minecraft.client.renderer.entity.WardenRenderer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class ClientSetup {
    public static void init(IEventBus modEventBus) {
        modEventBus.addListener(ClientSetup::registerEntityRenderers);
    }

    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.STRONGHOLD_GUARDIAN_WARDEN.get(), WardenRenderer::new);
    }
}
