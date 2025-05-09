package com.vetpetmon.forge;

import com.google.auto.service.AutoService;
import com.vetpetmon.LoaderHandler;
import com.vetpetmon.WyrmsOfNyrus;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Supplier;

@AutoService(LoaderHandler.class)
public class ForgeHandler implements LoaderHandler {
    @Override
    public Platform getPlatform() {
        return Platform.FORGE;
    }

    @Override
    public Path configPath() {
        return null;
    }

    @Override
    public Supplier<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items) {
        return register(BuiltInRegistries.CREATIVE_MODE_TAB, name, () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + WyrmsOfNyrus.MOD_ID + "." + name)).icon(icon)
                .displayItems((context, entries) -> {for (ArrayList<Supplier<? extends Item>> item : items) item.forEach((item1) -> entries.accept(item1.get()));})
                .withSearchBar().build());
    }

    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WyrmsOfNyrus.MOD_ID);

    private static final Map<ResourceKey<?>, DeferredRegister> CACHED = new Reference2ObjectOpenHashMap<>();
    @Override
    public <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> value) {return CACHED.computeIfAbsent(registry.key(), key -> DeferredRegister.create(registry.key().location(), WyrmsOfNyrus.MOD_ID)).register(name, value);    }

    @Override
    public <E extends Entity> Supplier<EntityType<E>> registerEntity(String id, EntityType.EntityFactory<E> factory, MobCategory category, float width, float height, int trackingRange){return ENTITIES.register(id, () -> EntityType.Builder.of(factory, category).sized(width, height).build(WyrmsOfNyrus.createRL(id).toString()));}

    @Override
    public <T> Supplier<Holder.Reference<T>> registerForHolder(Registry<T> registry, String name, Supplier<T> value) {
        RegistryObject<T> registryObject = CACHED.computeIfAbsent(registry.key(), key -> DeferredRegister.create(registry.key().location(), WyrmsOfNyrus.MOD_ID)).register(name, value);
        return () -> (Holder.Reference<T>) registryObject.getHolder().get();
    }

    public static void register(final IEventBus bus) {CACHED.values().forEach(deferredRegister -> deferredRegister.register(bus));}
}
