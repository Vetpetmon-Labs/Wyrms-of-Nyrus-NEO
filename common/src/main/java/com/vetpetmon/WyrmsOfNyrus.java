package com.vetpetmon;

import com.mojang.logging.LogUtils;
import com.vetpetmon.blocks.WoNBlocks;
import com.vetpetmon.items.WoNItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public final class WyrmsOfNyrus {
    public static final String MOD_ID = "wyrms";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        WoNBlocks.blocks();
        WoNItems.items();
        // Write common init code here.
    }

    public static ResourceLocation createRL(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registryKey, String name) {
        return ResourceKey.create(registryKey, createRL(name));
    }
}
