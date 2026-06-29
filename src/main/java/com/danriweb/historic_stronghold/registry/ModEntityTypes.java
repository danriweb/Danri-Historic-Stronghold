package com.danriweb.historic_stronghold.registry;

import com.danriweb.historic_stronghold.HistoricStronghold;
import com.danriweb.historic_stronghold.entity.StrongholdGuardianWarden;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntityTypes {
        public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister
                        .create(Registries.ENTITY_TYPE, HistoricStronghold.MOD_ID);

        public static final Supplier<EntityType<StrongholdGuardianWarden>> STRONGHOLD_GUARDIAN_WARDEN = ENTITY_TYPES
                        .register("stronghold_guardian_warden",
                                        () -> EntityType.Builder.of(StrongholdGuardianWarden::new, MobCategory.MONSTER)
                                                        .sized(0.9F, 2.9F)
                                                        .clientTrackingRange(16)
                                                        .fireImmune()
                                                        .build("stronghold_guardian_warden"));
}
