package com.vetpetmon.fabric;

import com.google.auto.service.AutoService;
import com.vetpetmon.PlatformHandler;
import com.vetpetmon.WyrmsOfNyrus;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Supplier;

@AutoService(PlatformHandler.class)
public class FabricPlatformHandler implements PlatformHandler {
    @Override
    public Platform getPlatform() {
        return Platform.FABRIC;
    }

    @Override
    public Path configPath() {
        return null;
    }

    @Override
    public Supplier<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items) {
        CreativeModeTab tab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, WyrmsOfNyrus.createRL(name), FabricItemGroup.builder()
                .title(Component.translatable("itemGroup." + WyrmsOfNyrus.MOD_ID + "." + name))
                .icon(icon)
                .displayItems((entry, context) -> {
                    for (ArrayList<Supplier<? extends Item>> item : items)
                        item.forEach((item1) -> context.accept(item1.get()));
                })
                .build());
        return () -> tab;
    }

    @Override
    public <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> value) {
        T value1 = Registry.register(registry, WyrmsOfNyrus.createRL(name), value.get());
        return () -> value1;
    }

    @Override
    public <T> Supplier<Holder.Reference<T>> registerForHolder(Registry<T> registry, String name, Supplier<T> value) {
        Holder.Reference<T> reference = Registry.registerForHolder(registry, WyrmsOfNyrus.createRL(name), value.get());
        return () -> reference;
    }
}
