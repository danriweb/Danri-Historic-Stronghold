package com.danriweb.historic_stronghold;

import net.minecraft.world.entity.monster.warden.Warden;

public class WardenMethodDumper {
    public static void main(String[] args) {
        for (java.lang.reflect.Method m : Warden.class.getDeclaredMethods()) {
            System.out.println(m.getName() + " - " + m.getReturnType().getName());
        }
    }
}
