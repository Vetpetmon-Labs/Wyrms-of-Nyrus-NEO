package com.vetpetmon.fabric;

import com.google.auto.service.AutoService;
import com.vetpetmon.LoaderHandler;
import com.vetpetmon.WyrmsOfNyrus;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Supplier;

@AutoService(LoaderHandler.class)
public class FabricHandler implements LoaderHandler {
    @Override
    public Platform getPlatform() {return Platform.FABRIC;}

    @Override
    public Path configPath() {return null;}

    @Override
    public Supplier<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items) {
        CreativeModeTab tab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, WyrmsOfNyrus.createRL(name), FabricItemGroup.builder()
                .title(Component.translatable("itemGroup." + WyrmsOfNyrus.MOD_ID + "." + name)).icon(icon)
                .displayItems((entry, context) -> {for (ArrayList<Supplier<? extends Item>> item : items) item.forEach((item1) -> context.accept(item1.get()));})
                .build());
        return () -> tab;
    }

    @Override
    public <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> value) {
        T value1 = Registry.register(registry, WyrmsOfNyrus.createRL(name), value.get());
        return () -> value1;
    }

    public <E extends Entity> Supplier<EntityType<E>> registerEntity(String id, EntityType.EntityFactory<E> factory, MobCategory category, float width, float height, int trackingRange){
        EntityType<E> entity = FabricEntityTypeBuilder.create(category, factory).dimensions(EntityDimensions.scalable(width, height)).trackRangeChunks(trackingRange).build();
        Registry.register(BuiltInRegistries.ENTITY_TYPE, WyrmsOfNyrus.createRL(id), entity);
        return () -> entity;
    }


    @Override
    public <T> Supplier<Holder.Reference<T>> registerForHolder(Registry<T> registry, String name, Supplier<T> value) {
        Holder.Reference<T> reference = Registry.registerForHolder(registry, WyrmsOfNyrus.createRL(name), value.get());
        return () -> reference;
    }
}
