package com.danriweb.historic_stronghold;

import com.danriweb.historic_stronghold.registry.ModEntityTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(HistoricStronghold.MOD_ID)
public class HistoricStronghold {
  public static final String MOD_ID = "historic_stronghold";

  public HistoricStronghold(IEventBus modEventBus) {
    ModEntityTypes.ENTITY_TYPES.register(modEventBus);

    modEventBus.addListener(com.danriweb.historic_stronghold.event.ModEventBusEvents::entityAttributeEvent);

    if (net.neoforged.fml.loading.FMLEnvironment.dist.isClient()) {
      com.danriweb.historic_stronghold.client.ClientSetup.init(modEventBus);
    }
  }
}
