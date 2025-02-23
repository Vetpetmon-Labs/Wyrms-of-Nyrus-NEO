package com.vetpetmon.blocks;

import com.vetpetmon.PlatformHandler;
import com.vetpetmon.WyrmsOfNyrus;
import com.vetpetmon.items.WoNItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.function.Supplier;

public class WoNBlocks {

    public static final ArrayList<Supplier<? extends Block>> cubeAllBlocks = new ArrayList<>();
    public static final ArrayList<Supplier<? extends Block>> cubeAllBlocksVariants = new ArrayList<>();
    public static final ArrayList<Integer> cubeAllBlocksVariantNumbers = new ArrayList<>();

    public static final ArrayList<Supplier<? extends Block>> BLOCKS = new ArrayList<>();
    public static final ArrayList<Supplier<? extends Item>> BLOCK_ITEMS = new ArrayList<>();

    public static final Supplier<Block> HIVE_DIRT = registerBasicBlockWithItem("hivedirt", BlockBehaviour.Properties.copy(Blocks.DIRT));
    public static final Supplier<Block> CREEPED_BULB = registerBasicBlockWithItem("creepedbulb", BlockBehaviour.Properties.copy(Blocks.NETHER_WART_BLOCK));
    public static final Supplier<Block> COOLANT_TOWER = registerBasicBlockWithItem("coolanttower", BlockBehaviour.Properties.copy(Blocks.SHROOMLIGHT));
    public static final Supplier<Block> CORIUM = registerBasicBlockWithItem("corium", BlockBehaviour.Properties.copy(Blocks.BEDROCK));

    public static final Supplier<Block> MELTDOWN_COAL = registerBasicBlockWithItem("meltdowncoal", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
    public static final Supplier<Block> MELTDOWN_IRON = registerBasicBlockWithItem("meltdowniron", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
    public static final Supplier<Block> MELTDOWN_GOLD = registerBasicBlockWithItem("meltdowngold", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
    public static final Supplier<Block> MELTDOWN_LAPIS = registerBasicBlockWithItem("meltdownlapis", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
    public static final Supplier<Block> MELTDOWN_REDSTONE = registerBasicBlockWithItem("meltdownredstone", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
    public static final Supplier<Block> MELTDOWN_DIAMOND = registerBasicBlockWithItem("meltdowndiamond", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));
    public static final Supplier<Block> MELTDOWN_EMERALD = registerBasicBlockWithItem("meltdownemerald", BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK));



    public static final Supplier<Block> BORG_PLATING = registerBasicBlockWithItemVar("borgplating", BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK),2);



    public static Supplier<Block> registerBasicBlockWithItem(String key, BlockBehaviour.Properties properties) {
        return registerCubeAllBlockItem(key, () -> new Block(properties));
    }

    public static Supplier<Block> registerBasicBlockWithItemVar(String key, BlockBehaviour.Properties properties, int variants) {
        return registerCubeAllBlockItemVar(key, () -> new Block(properties),variants);
    }

    public static <B extends Block> Supplier<B> registerCubeAllBlockItemVar(String key, Supplier<B> blockSupplier,int variants) {
        Supplier<B> block = registerBlockItem(key, blockSupplier);
        cubeAllBlocksVariants.add(block);
        cubeAllBlocksVariantNumbers.add(variants);
        return block;
    }

    public static <B extends Block> Supplier<B> registerCubeAllBlockItem(String key, Supplier<B> blockSupplier) {
        Supplier<B> block = registerBlockItem(key, blockSupplier);
        cubeAllBlocks.add(block);
        return block;
    }

    public static <B extends Block> Supplier<B> registerBlockItem(String key, Supplier<B> blockSupplier) {
        Supplier<B> block = registerBlock(key, blockSupplier);
        Supplier<Item> item = WoNItems.register(key, () -> new BlockItem(block.get(), new Item.Properties()));
        BLOCK_ITEMS.add(item);
        return block;
    }

    public static <B extends Block> Supplier<B> registerBlock(String id, Supplier<B> block) {
        Supplier<B> blockSupplier = register(id, block);
        BLOCKS.add(blockSupplier);
        return blockSupplier;
    }

    public static <B extends Block> Supplier<B> register(String id, Supplier<B> block) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.BLOCK, id, block);
    }
    public static void blocks() {
        WyrmsOfNyrus.LOGGER.info("Registering blocks");
    }
}
