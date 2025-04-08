package com.vetpetmon.core.item;

import com.vetpetmon.LoaderHandler;
import com.vetpetmon.WyrmsOfNyrus;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.function.Supplier;

public class WoNItems {

    public static final ArrayList<Supplier<? extends Item>> ITEMS = new ArrayList<>();
    public static final ArrayList<Supplier<? extends Item>> SIMPLE_ITEMS = new ArrayList<>();


    public static <I extends Item> Supplier<I> registerSimpleItem(String id, Supplier<I> item) {
        Supplier<I> supplier = registerItem(id, item);
        SIMPLE_ITEMS.add(supplier);
        return supplier;
    }

    public static <I extends Item> Supplier<I> registerItem(String id, Supplier<I> item) {
        Supplier<I> supplier = register(id, item);
        ITEMS.add(supplier);
        return supplier;
    }

    public static <I extends Item> Supplier<I> register(String id, Supplier<I> item) {
        return LoaderHandler.PLATFORM_HANDLER.register(BuiltInRegistries.ITEM, id, item);
    }
    public static void items() {
        WyrmsOfNyrus.LOGGER.info("Registering items and block items");
    }
}
