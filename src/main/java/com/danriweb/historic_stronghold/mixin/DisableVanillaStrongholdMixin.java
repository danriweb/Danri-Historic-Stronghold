package com.danriweb.historic_stronghold.mixin;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkGenerator.class)
public class DisableVanillaStrongholdMixin {

  /**
   * Blocks vanilla stronghold block generation completely.
   */
  @Inject(method = "tryGenerateStructure", at = @At("HEAD"), cancellable = true)
  private void historic_cancelVanillaGeneration(
      StructureSet.StructureSelectionEntry selectionEntry,
      StructureManager structureManager,
      RegistryAccess registryAccess,
      RandomState randomState,
      StructureTemplateManager templateManager,
      long seed,
      ChunkAccess chunk,
      ChunkPos chunkPos,
      SectionPos sectionPos,
      CallbackInfoReturnable<Boolean> cir) {
    selectionEntry.structure().unwrapKey().ifPresent(key -> {
      if (key.location().getNamespace().equals("minecraft") && key.location().getPath().equals("stronghold")) {
        cir.setReturnValue(false);
      }
    });
  }

  /**
   * Ignores the vanilla stronghold in locate searches so the Eye of Ender
   * cleanly skips it and points to the Historic Stronghold instead.
   */
  @Inject(method = "getNearestGeneratedStructure(Ljava/util/Set;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/core/BlockPos;ZLnet/minecraft/world/level/levelgen/structure/placement/ConcentricRingsStructurePlacement;)Lcom/mojang/datafixers/util/Pair;", at = @At("HEAD"), cancellable = true)
  private void historic_ignoreVanillaStronghold(
      java.util.Set<net.minecraft.core.Holder<net.minecraft.world.level.levelgen.structure.Structure>> structures,
      net.minecraft.server.level.ServerLevel level,
      StructureManager manager,
      net.minecraft.core.BlockPos pos,
      boolean bl,
      net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement placement,
      CallbackInfoReturnable<com.mojang.datafixers.util.Pair<net.minecraft.core.BlockPos, net.minecraft.core.Holder<net.minecraft.world.level.levelgen.structure.Structure>>> cir) {
    for (net.minecraft.core.Holder<net.minecraft.world.level.levelgen.structure.Structure> holder : structures) {
      holder.unwrapKey().ifPresent(key -> {
        if (key.location().getNamespace().equals("minecraft") && key.location().getPath().equals("stronghold")) {
          cir.setReturnValue(null); // Cleanly ignore
        }
      });
    }
  }
}
