package com.vetpetmon.forge;

import com.vetpetmon.WyrmsOfNyrus;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WyrmsOfNyrus.MOD_ID)
public final class WyrmsOfNyrusForge {
    @SuppressWarnings("removal")
    public WyrmsOfNyrusForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(WyrmsOfNyrus.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        WyrmsOfNyrus.init();
    }
}
