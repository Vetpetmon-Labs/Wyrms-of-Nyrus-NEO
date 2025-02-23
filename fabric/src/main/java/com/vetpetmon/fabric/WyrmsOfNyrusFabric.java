package com.vetpetmon.fabric;

import com.vetpetmon.WyrmsOfNyrus;
import net.fabricmc.api.ModInitializer;


public final class WyrmsOfNyrusFabric implements ModInitializer {
    //yeah, fabric does things a bit differently.
    // Kinda cringe but whatever gives you your 1-second boot times I guess
    private static String firstInitializedFrom = null;

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        initialization("Wyrms of Nyrus Fabric");
    }

    public static void initialization(String initializedFrom){

        // Run our common setup.
        if (firstInitializedFrom != null) {
            WyrmsOfNyrus.LOGGER.info("Attempted to initialize Wyrms of Nyrus, but it was already initialized. There should be absolutely no problem, ignore this message. I'm new to Fabric. -Modrome");
            return;
        }
        firstInitializedFrom = initializedFrom;

        WyrmsOfNyrus.init();

    }

}
