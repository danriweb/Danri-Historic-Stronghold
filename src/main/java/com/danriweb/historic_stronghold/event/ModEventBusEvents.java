package com.danriweb.historic_stronghold.event;

import com.danriweb.historic_stronghold.registry.ModEntityTypes;
import net.minecraft.world.entity.monster.warden.Warden;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

public class ModEventBusEvents {
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.STRONGHOLD_GUARDIAN_WARDEN.get(), Warden.createAttributes().build());
    }
}
