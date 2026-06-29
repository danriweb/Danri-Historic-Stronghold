package com.danriweb.historic_stronghold.event;

import com.danriweb.historic_stronghold.HistoricStronghold;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

@EventBusSubscriber(modid = HistoricStronghold.MOD_ID)
public class ModForgeEvents {

    @SubscribeEvent
    public static void onMobSpawnCheck(FinalizeSpawnEvent event) {
        MobSpawnType type = event.getSpawnType();
        if ((type == MobSpawnType.NATURAL || type == MobSpawnType.CHUNK_GENERATION || type == MobSpawnType.STRUCTURE
                || type == MobSpawnType.PATROL)
                && event.getLevel() instanceof ServerLevel serverLevel) {
            ResourceKey<Structure> structureKey = ResourceKey.create(Registries.STRUCTURE,
                    ResourceLocation.fromNamespaceAndPath(HistoricStronghold.MOD_ID, "historic_stronghold"));
            Structure structure = serverLevel.registryAccess().registryOrThrow(Registries.STRUCTURE).get(structureKey);
            if (structure != null) {
                BlockPos pos = BlockPos.containing(event.getX(), event.getY(), event.getZ());
                if (serverLevel.structureManager().getStructureWithPieceAt(pos, structure).isValid()) {
                    event.setSpawnCancelled(true);
                }
            }
        }
    }
}
