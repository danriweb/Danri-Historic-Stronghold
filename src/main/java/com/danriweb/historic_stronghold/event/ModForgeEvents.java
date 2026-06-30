package com.danriweb.historic_stronghold.event;

import com.danriweb.historic_stronghold.HistoricStronghold;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;

@EventBusSubscriber(modid = HistoricStronghold.MOD_ID)
public class ModForgeEvents {

  @SubscribeEvent
  public static void onMobSpawnCheck(FinalizeSpawnEvent event) {
    var type = event.getSpawnType();

    String typeName = type.name();

    if (("NATURAL".equals(typeName) || "CHUNK_GENERATION".equals(typeName) || "STRUCTURE".equals(typeName)
        || "PATROL".equals(typeName))
        && event.getLevel() instanceof ServerLevel serverLevel) {

      ResourceKey<Structure> structureKey = ResourceKey.create(Registries.STRUCTURE,
          Identifier.fromNamespaceAndPath(HistoricStronghold.MOD_ID, "historic_stronghold"));

      Structure structure = serverLevel.registryAccess()
          .lookupOrThrow(Registries.STRUCTURE)
          .get(structureKey)
          .map(net.minecraft.core.Holder::value)
          .orElse(null);
      if (structure != null) {
        BlockPos pos = BlockPos.containing(event.getX(), event.getY(), event.getZ());
        if (serverLevel.structureManager().getStructureWithPieceAt(pos, structure).isValid()) {
          event.setSpawnCancelled(true);
        }
      }
    }
  }
}