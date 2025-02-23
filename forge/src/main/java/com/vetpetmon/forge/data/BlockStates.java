package com.vetpetmon.forge.data;

import com.vetpetmon.WyrmsOfNyrus;
import com.vetpetmon.blocks.WoNBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

//Casually stealing everything from Biscuit Acceleration
//It's not plagiarism now, is it?
//I made Biscuit Acceleration. It's my code. Therefore, I do what I want with it.

public class BlockStates extends BlockStateProvider {
    private static final int DEFAULT_ANGLE_OFFSET = 180;

    public BlockStates(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, WyrmsOfNyrus.MOD_ID, existingFileHelper);
    }



    private String blockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(WyrmsOfNyrus.MOD_ID, "block/" + path);
    }

    public ModelFile existingModel(Block block) {
        return new ModelFile.ExistingModelFile(resourceBlock(blockName(block)), models().existingFileHelper);
    }

    public ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(resourceBlock(path), models().existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        for (Supplier<? extends Block> b : WoNBlocks.cubeAllBlocks)
            cubeAllSides(b.get());

    }
    public void completeRandBlockSet(Block block, StairBlock stairs, SlabBlock slab){
        simpleBlock(block,cubeRandomRotation(block,""));
        stairs(stairs,block);
        slab(slab,block);
    }

    public void completeBlockSet(Block block, StairBlock stairs, SlabBlock slab){
        cubeAllSides(block);
        stairs(stairs,block);
        slab(slab,block);
    }

    public void cubeAllSides(Block block) {
        this.simpleBlock(block,
                models().cubeAll(blockName(block),resourceBlock(blockName(block))));
    }

    public void crossBlock(Block block) {
        this.simpleBlock(block, models().cross(blockName(block), resourceBlock(blockName(block))).renderType("cutout"));
    }


    public void slab(SlabBlock block, Block textureBlock) {
        slabBlock(block, resourceBlock(blockName(textureBlock)), resourceBlock(blockName(textureBlock)));
        simpleBlockItem(block);
    }

    public void stairs(StairBlock block, Block textureBlock) {
        stairsBlock(block, resourceBlock(blockName(textureBlock)));
        simpleBlockItem(block);
    }

    public void simpleBlockItem(Block block) {
        String path = path(block);
        itemModels().getBuilder(path).parent(models().getBuilder(path));
    }


    protected String path(Block block) {
        return registryKey(block).getPath();
    }
    protected ResourceLocation registryKey(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block));
    }

    public ConfiguredModel[] cubeRandomRotation(Block block, String suffix) {
        String formattedName = blockName(block) + (suffix.isEmpty() ? "" : "_" + suffix);
        return ConfiguredModel.allYRotations(models().cubeAll(formattedName, resourceBlock(formattedName)), 0, false);
    }

    public void topSidesBlock(Block block) {
        this.simpleBlock(block, models().withExistingParent(blockName(block),"cube")
                .texture("particle", resourceBlock(blockName(block) + "_top"))
                .texture("down", resourceBlock(blockName(block) + "_top"))
                .texture("up", resourceBlock(blockName(block) + "_top"))
                .texture("north", resourceBlock(blockName(block) + "_side"))
                .texture("south", resourceBlock(blockName(block) + "_side"))
                .texture("east", resourceBlock(blockName(block) + "_side"))
                .texture("west", resourceBlock(blockName(block) + "_side"))
        );
    }

    public void customDirectionalBlock(Block block, Function<BlockState, ModelFile> modelFunc, Property<?>... ignored) {
        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    Direction dir = state.getValue(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(modelFunc.apply(state))
                            .rotationX(dir == Direction.DOWN ? 180 : dir.getAxis().isHorizontal() ? 90 : 0)
                            .rotationY(dir.getAxis().isVertical() ? 0 : ((int) dir.toYRot() + DEFAULT_ANGLE_OFFSET) % 360)
                            .build();
                }, ignored);
    }

    public void customHorizontalBlock(Block block, Function<BlockState, ModelFile> modelFunc, Property<?>... ignored) {
        getVariantBuilder(block)
                .forAllStatesExcept(state -> ConfiguredModel.builder()
                        .modelFile(modelFunc.apply(state))
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + DEFAULT_ANGLE_OFFSET) % 360)
                        .build(), ignored);
    }

    public void stageBlock(Block block, IntegerProperty ageProperty, Property<?>... ignored) {
        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    int ageSuffix = state.getValue(ageProperty);
                    String stageName = blockName(block) + "_stage" + ageSuffix;
                    return ConfiguredModel.builder()
                            .modelFile(models().cross(stageName, resourceBlock(stageName)).renderType("cutout")).build();
                }, ignored);
    }

    public void customStageBlock(Block block, @Nullable ResourceLocation parent, String textureKey, IntegerProperty ageProperty, List<Integer> suffixes, Property<?>... ignored) {
        getVariantBuilder(block)
                .forAllStatesExcept(state -> {
                    int ageSuffix = state.getValue(ageProperty);
                    String stageName = blockName(block) + "_stage";
                    stageName += suffixes.isEmpty() ? ageSuffix : suffixes.get(Math.min(suffixes.size(), ageSuffix));
                    if (parent == null) {
                        return ConfiguredModel.builder()
                                .modelFile(models().cross(stageName, resourceBlock(stageName)).renderType("cutout")).build();
                    }
                    return ConfiguredModel.builder()
                            .modelFile(models().singleTexture(stageName, parent, textureKey, resourceBlock(stageName)).renderType("cutout")).build();
                }, ignored);
    }

    public void crossedBlock(Block block) {
        this.simpleBlock(block, models().cross(blockName(block), resourceBlock(blockName(block))).renderType("cutout"));
    }

    private ResourceLocation woodBlockTexture(String type, String name) {
        return WyrmsOfNyrus.createRL(ModelProvider.BLOCK_FOLDER + "/" + type + name);
    }

    private void log(RotatedPillarBlock block) {
        axisBlock(block,woodBlockTexture(blockName(block),"_side"), woodBlockTexture(blockName(block),"_top"));
        itemModels().cubeColumn(blockName(block), woodBlockTexture(blockName(block),"_side"), woodBlockTexture(blockName(block),"_top"));
    }


    public void doublePlantBlock(Block block) {
        getVariantBuilder(block)
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                .modelForState().modelFile(models().cross(blockName(block) + "_bottom", resourceBlock(blockName(block) + "_bottom")).renderType("cutout")).addModel()
                .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
                .modelForState().modelFile(models().cross(blockName(block) + "_top", resourceBlock(blockName(block) + "_top")).renderType("cutout")).addModel();
    }

}
