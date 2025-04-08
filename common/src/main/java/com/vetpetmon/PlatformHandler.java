package com.vetpetmon;


import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ServiceLoader;
import java.util.function.Supplier;


/*
    Needed to bridge the gap somehow.
 */
public interface PlatformHandler {
    PlatformHandler PLATFORM_HANDLER = load(PlatformHandler.class);
    Platform getPlatform();
    Path configPath();

    Supplier<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items);

    <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> value);
    <T> Supplier<Holder.Reference<T>> registerForHolder(Registry<T> registry, String name, Supplier<T> value);

    private static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        WyrmsOfNyrus.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
    <E extends Entity> Supplier<EntityType<E>> registerEntity(String id, EntityType.EntityFactory<E> factory, MobCategory category, float width, float height, int trackingRange);

    enum Platform {
        FORGE,
        FABRIC
    }

}
