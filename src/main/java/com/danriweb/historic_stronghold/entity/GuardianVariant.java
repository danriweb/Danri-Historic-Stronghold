package com.danriweb.historic_stronghold.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;

public enum GuardianVariant {
    RED(0, "entity.historic_stronghold.guardian_red", BossEvent.BossBarColor.RED),
    GREEN(1, "entity.historic_stronghold.guardian_green", BossEvent.BossBarColor.GREEN),
    BLUE(2, "entity.historic_stronghold.guardian_blue", BossEvent.BossBarColor.BLUE),
    YELLOW(3, "entity.historic_stronghold.guardian_yellow", BossEvent.BossBarColor.YELLOW);

    private final int id;
    private final String translationKey;
    private final BossEvent.BossBarColor barColor;

    GuardianVariant(int id, String translationKey, BossEvent.BossBarColor barColor) {
        this.id = id;
        this.translationKey = translationKey;
        this.barColor = barColor;
    }

    public int getId() {
        return id;
    }

    public Component getDisplayName() {
        return Component.translatable(translationKey);
    }

    public BossEvent.BossBarColor getBarColor() {
        return barColor;
    }

    public static GuardianVariant byId(int id) {
        for (GuardianVariant variant : values()) {
            if (variant.getId() == id) {
                return variant;
            }
        }
        return RED;
    }
}
