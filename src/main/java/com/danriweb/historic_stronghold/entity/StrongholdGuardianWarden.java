package com.danriweb.historic_stronghold.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import java.util.UUID;

public class StrongholdGuardianWarden extends Warden {
  private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData
      .defineId(StrongholdGuardianWarden.class, EntityDataSerializers.INT);

  private final ServerBossEvent bossEvent = (ServerBossEvent) (new ServerBossEvent(
      UUID.randomUUID(),
      Component.empty(),
      BossEvent.BossBarColor.RED,
      BossEvent.BossBarOverlay.PROGRESS) {
  }).setDarkenScreen(true);

  public StrongholdGuardianWarden(EntityType<? extends Warden> type, Level level) {
    super(type, level);
    this.updateBossBar();
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder builder) {
    super.defineSynchedData(builder);
    builder.define(DATA_VARIANT_ID, 0);
  }

  public GuardianVariant getVariant() {
    return GuardianVariant.byId(this.entityData.get(DATA_VARIANT_ID));
  }

  public void setVariant(GuardianVariant variant) {
    this.entityData.set(DATA_VARIANT_ID, variant.getId());
    this.updateBossBar();
  }

  @Override
  public void addAdditionalSaveData(ValueOutput output) {
    super.addAdditionalSaveData(output);
    output.putInt("Variant", this.getVariant().getId());
  }

  @Override
  public void readAdditionalSaveData(ValueInput input) {
    super.readAdditionalSaveData(input);
    this.setVariant(GuardianVariant.byId(input.getIntOr("Variant", 0)));
  }

  private void updateBossBar() {
    if (this.hasCustomName()) {
      this.bossEvent.setName(this.getCustomName());
    } else {
      this.bossEvent.setName(this.getVariant().getDisplayName());
    }
    this.bossEvent.setColor(this.getVariant().getBarColor());
  }

  @Override
  public void setCustomName(@javax.annotation.Nullable Component name) {
    super.setCustomName(name);
    this.updateBossBar();
  }

  @Override
  public Component getName() {
    if (this.hasCustomName()) {
      return this.getCustomName();
    }
    return this.getVariant().getDisplayName();
  }

  @Override
  public void tick() {
    super.tick();
    if (!this.level().isClientSide()) {
      this.getBrain().setMemoryWithExpiry(net.minecraft.world.entity.ai.memory.MemoryModuleType.DIG_COOLDOWN,
          net.minecraft.util.Unit.INSTANCE, 1200L);
      if (!this.isAlive()) {
        this.bossEvent.removeAllPlayers();
        return;
      }

      this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

      java.util.List<ServerPlayer> nearbyPlayers = this.level().getEntitiesOfClass(ServerPlayer.class,
          this.getBoundingBox().inflate(20.0D));

      for (ServerPlayer player : new java.util.ArrayList<>(this.bossEvent.getPlayers())) {
        if (!nearbyPlayers.contains(player) || !player.isAlive()) {
          this.bossEvent.removePlayer(player);
        }
      }

      for (ServerPlayer player : nearbyPlayers) {
        if (player.isAlive() && !this.bossEvent.getPlayers().contains(player)) {
          this.bossEvent.addPlayer(player);
        }
      }
    }
  }

  @Override
  public void remove(RemovalReason reason) {
    super.remove(reason);
    this.bossEvent.removeAllPlayers();
  }

  @Override
  public boolean removeWhenFarAway(double distanceToClosestPlayer) {
    return false;
  }

  @Override
  public boolean canTargetEntity(@javax.annotation.Nullable net.minecraft.world.entity.Entity entity) {
    if (!super.canTargetEntity(entity)) {
      return false;
    }

    if (entity instanceof net.minecraft.world.entity.player.Player) {
      return true;
    }

    if (entity instanceof net.minecraft.world.entity.LivingEntity living) {
      if (this.getLastHurtByMob() == living) {
        return true;
      }
    }

    return false;
  }
}