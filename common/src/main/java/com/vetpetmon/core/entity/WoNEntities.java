package com.vetpetmon.core.entity;

import com.vetpetmon.LoaderHandler;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class WoNEntities {

    private static <E extends Entity> Supplier<EntityType<E>> createEntity(String id, EntityType.EntityFactory<E> entityFactory, MobCategory category, float width, float height, int trackingRange) {
        return LoaderHandler.PLATFORM_HANDLER.registerEntity(id, entityFactory, category, width, height, trackingRange);
    }

    // I miss my subscribeEvents back from forge... but at least this hacky solution works on both loaders
//    @SubscribeEvent
//    public static void SpawnPlacement(SpawnPlacementRegisterEvent event){} //dis only work on forge sorry to rain on your parade you dum-dum gum-gum

    public record SpawnPlacement<T extends Mob>(EntityType<T> entityType, SpawnPlacements.Type spawnPlacementType, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate) {}

    public static <T extends Mob> void registerSpawnPlacements(Consumer<SpawnPlacement<T>> consumer) {}

    // EntityAttributeCreationEvent is totally slower to use, thanks architectury for fixing that garbage
    public static void registerEntityAttributes(BiConsumer<EntityType<? extends LivingEntity>, AttributeSupplier> consumer) {
        //consumer.accept(WYRMLING.get(),wyrmStatBuilder(fasterWyrm,1))
        //consumer.accept(PROBER.get(),wyrmStatBuilder(fastestWyrm,1))
        //consumer.accept(ROVER.get(),wyrmStatBuilder(fastWyrm,1))
        //consumer.accept(WARRIOR.get(),wyrmStatBuilder(fastWyrm,2))
        //consumer.accept(FUNGALLID.get(),wyrmStatBuilder(fastWyrm,5))
        //consumer.accept(WORKER.get(),wyrmStatBuilder(averageWyrm,2))
        //consumer.accept(RADIWYRM.get(),wyrmStatBuilder(averageWyrm,7))
        //consumer.accept(SOLDIER.get(),wyrmStatBuilder(slowerWyrm,4))
        //consumer.accept(GRUNT.get(),wyrmStatBuilder(slowestWyrm,8))
        //consumer.accept(VISITOR.get(),wyrmStatBuilder(slowestWyrm,20))
    }

    private double fastestWyrm=0.45D,fasterWyrm=0.41D,fastWyrm=0.39D,averageWyrm=0.35D,slowWyrm=0.33D,slowerWyrm=0.30D,slowestWyrm=0.25D;

    //TODO: Make wyrm tiers and stat modifiers configurable
    //Keep movespeed below 0.5D for even the smallest wyrms, and tier determines the wyrm's total stat makeup. Tier is determined by perceived size and danger level.
    public static AttributeSupplier.Builder wyrmStatBuilder(double moveSpeed, int tier) {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, moveSpeed)
                .add(Attributes.MAX_HEALTH, tier*7.0D) //T1 wyrms only have 7 HP, aka Probers
                .add(Attributes.ATTACK_DAMAGE, tier*0.5D)
                .add(Attributes.ARMOR, tier*1.0D)
                .add(Attributes.ATTACK_KNOCKBACK, tier*0.1D)
                .add(Attributes.KNOCKBACK_RESISTANCE, tier*0.33D);
    }
}
