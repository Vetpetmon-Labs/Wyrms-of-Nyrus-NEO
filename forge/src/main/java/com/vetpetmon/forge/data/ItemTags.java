package com.vetpetmon.forge.data;

import com.vetpetmon.WyrmsOfNyrus;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ItemTags extends ItemTagsProvider {
    public ItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, blockTagProvider, WyrmsOfNyrus.MOD_ID, existingFileHelper);
    }

    // For built-in
    private void registerMinecraftTags() {

    }

    // For Mod
    private void registerModTags() {

    }

    // For Forge
    @SuppressWarnings("unchecked")
    private void registerForgeTags() {

    }

    // For Mod Compatibilities
    public void registerCompatibilityTags() {

    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.registerMinecraftTags();
        this.registerModTags();
        this.registerForgeTags();
        this.registerCompatibilityTags();
    }
}
