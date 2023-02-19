package tech.thatgravyboat.creeperoverhaul.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModEntities;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModSpawns;
import tech.thatgravyboat.creeperoverhaul.common.registry.fabric.FabricAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CreepersFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        try {
            Config.loadConfig();
        } catch (Exception e) {
            System.out.println("[Creeper Overhaul] Failed to load Config.");
        }
        Creepers.init();
        FabricAttributes.register();
        Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> attributes = new HashMap<>();
        Creepers.registerAttributes(attributes);
        attributes.forEach(FabricDefaultAttributeRegistry::register);
        addCreepers();
        removeCreepers();
        ModSpawns.addSpawnRules();

        ServerLifecycleEvents.SERVER_STARTING.register(event -> PluginLoader.load());
    }

    public void addCreepers() {
        addCreeper(tag(BiomeTags.IS_BEACH), ModEntities.BEACH_CREEPER);
        addCreeper(tag(BiomeTags.IS_BEACH), ModEntities.CAVE_CREEPER);

        addCreeper(isSnowing(), ModEntities.SNOWY_CREEPER);
        addCreeper(isSnowing(), ModEntities.CAVE_CREEPER);

        addCreeper(tag(BiomeTags.HAS_DESERT_PYRAMID), ModEntities.DESERT_CREEPER);
        addCreeper(tag(BiomeTags.HAS_DESERT_PYRAMID), ModEntities.CAVE_CREEPER);

        addCreeper(tag(BiomeTags.IS_HILL), ModEntities.HILLS_CREEPER);
        addCreeper(tag(BiomeTags.IS_HILL), ModEntities.CAVE_CREEPER);

        addCreeper(tag(BiomeTags.IS_SAVANNA), ModEntities.SAVANNAH_CREEPER);
        addCreeper(tag(BiomeTags.IS_SAVANNA), ModEntities.CAVE_CREEPER);

        addCreeper(tag(BiomeTags.HAS_MINESHAFT_MESA), ModEntities.BADLANDS_CREEPER);
        addCreeper(tag(BiomeTags.HAS_MINESHAFT_MESA), ModEntities.CAVE_CREEPER);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.MUSHROOM_FIELDS), MobCategory.CREATURE, ModEntities.MUSHROOM_CREEPER.get(), 10, 1, 2);

        addCreeper(tag(BiomeTags.IS_TAIGA), ModEntities.SPRUCE_CREEPER);
        addCreeper(tag(BiomeTags.IS_TAIGA), ModEntities.CAVE_CREEPER);

        addCreeper(tag(BiomeTags.IS_MOUNTAIN).and(Predicate.not(isSnowing())), ModEntities.HILLS_CREEPER);
        addCreeper(tag(BiomeTags.IS_MOUNTAIN), ModEntities.CAVE_CREEPER);

        addCreeper(BiomeSelectors.includeByKey(Biomes.DRIPSTONE_CAVES, Biomes.LUSH_CAVES), ModEntities.DRIPSTONE_CREEPER);
        addCreeper(BiomeSelectors.includeByKey(Biomes.DRIPSTONE_CAVES, Biomes.LUSH_CAVES), ModEntities.CAVE_CREEPER);

        addCreeper(tag(BiomeTags.IS_JUNGLE), ModEntities.BAMBOO_CREEPER);
        addCreeper(tag(BiomeTags.IS_JUNGLE), ModEntities.JUNGLE_CREEPER);
        addCreeper(tag(BiomeTags.IS_JUNGLE), ModEntities.CAVE_CREEPER);

        addCreeper(tag(BiomeTags.HAS_RUINED_PORTAL_SWAMP), ModEntities.SWAMP_CREEPER);
        addCreeper(tag(BiomeTags.HAS_RUINED_PORTAL_SWAMP), ModEntities.CAVE_CREEPER);

        addCreeper(BiomeSelectors.includeByKey(Biomes.DARK_FOREST), ModEntities.DARK_OAK_CREEPER);
        addCreeper(BiomeSelectors.includeByKey(Biomes.DARK_FOREST), ModEntities.CAVE_CREEPER);

        addCreeper(BiomeSelectors.includeByKey(Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN), ModEntities.OCEAN_CREEPER, 2);
        addCreeper(BiomeSelectors.includeByKey(Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN), ModEntities.CAVE_CREEPER);
    }

    public void removeCreepers() {
        Predicate<BiomeSelectionContext> creepersToRemove = tag(BiomeTags.IS_BEACH)
                .or(isSnowing())
                .or(tag(BiomeTags.HAS_DESERT_PYRAMID))
                .or(tag(BiomeTags.IS_HILL))
                .or(tag(BiomeTags.IS_SAVANNA))
                .or(tag(BiomeTags.HAS_MINESHAFT_MESA))
                .or(BiomeSelectors.includeByKey(Biomes.MUSHROOM_FIELDS))
                .or(tag(BiomeTags.IS_TAIGA))
                .or(tag(BiomeTags.IS_MOUNTAIN))
                .or(BiomeSelectors.includeByKey(Biomes.DRIPSTONE_CAVES, Biomes.LUSH_CAVES))
                .or(tag(BiomeTags.IS_JUNGLE))
                .or(tag(BiomeTags.HAS_RUINED_PORTAL_SWAMP))
                .or(BiomeSelectors.includeByKey(Biomes.DARK_FOREST))
                .or(BiomeSelectors.includeByKey(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN));

        removeCreeper(creepersToRemove);
    }

    private static Predicate<BiomeSelectionContext> tag(TagKey<Biome> tag) {
        return BiomeSelectors.tag(tag);
    }

    private static Predicate<BiomeSelectionContext> isSnowing() {
        return ctx -> ctx.getBiome().getPrecipitation().equals(Biome.Precipitation.SNOW);
    }

    private <E extends BaseCreeper> void addCreeper(Predicate<BiomeSelectionContext> selectors, Supplier<EntityType<E>> entityType) {
        addCreeper(selectors, entityType, 75);
    }

    private <E extends BaseCreeper> void addCreeper(Predicate<BiomeSelectionContext> selectors, Supplier<EntityType<E>> entityType, int weight) {
        BiomeModifications.addSpawn(selectors.and(BiomeSelectors.foundInOverworld()), entityType.get().getCategory(), entityType.get(), weight, 2, 2);
    }

    private void removeCreeper(Predicate<BiomeSelectionContext> biomeSelector) {
        ResourceLocation id = Registry.ENTITY_TYPE.getKey(EntityType.CREEPER);

        BiomeModifications.create(id).add(ModificationPhase.REMOVALS, biomeSelector,
                context -> context.getSpawnSettings().removeSpawnsOfEntityType(EntityType.CREEPER));
    }
}
