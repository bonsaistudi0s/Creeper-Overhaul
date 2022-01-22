package tech.thatgravyboat.creeperoverhaul;

import com.google.common.base.Preconditions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;
import tech.thatgravyboat.creeperoverhaul.common.Events;
import tech.thatgravyboat.creeperoverhaul.common.entity.CreeperTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModAttributes;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModBlocks;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModEntities;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModItems;

import java.util.Random;
import java.util.function.Predicate;

public class Creepers implements ModInitializer {

    public static final String MODID = "creeperoverhaul";

    public static final Events EVENT = Events.getCurrentEvent();

    public static final ItemGroup TAB = FabricItemGroupBuilder.build(new Identifier(Creepers.MODID, "item_group"), () -> new ItemStack(ModItems.JUNGLE_SPAWN_EGG));

    @Override
    public void onInitialize() {
        GeckoLibMod.DISABLE_IN_DEV = true;
        GeckoLib.initialize();
        ModBlocks.register();
        ModEntities.register();
        ModItems.reigster();
        ModAttributes.register();
        addAttributes();
        addCreepers();
        removeCreepers();
        addSpawnRules();
    }

    public void addAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.JUNGLE_CREEPER, CreeperTypes.JUNGLE.attributes());
        FabricDefaultAttributeRegistry.register(ModEntities.BAMBOO_CREEPER, CreeperTypes.BAMBOO.attributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DESERT_CREEPER, CreeperTypes.DESERT.attributes());
        FabricDefaultAttributeRegistry.register(ModEntities.BADLANDS_CREEPER, CreeperTypes.BADLANDS.attributes());
        FabricDefaultAttributeRegistry.register(ModEntities.HILLS_CREEPER, CreeperTypes.HILLS.attributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SAVANNAH_CREEPER, CreeperTypes.SAVANNAH.attributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MUSHROOM_CREEPER, CreeperTypes.MUSHROOM.attributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SWAMP_CREEPER, CreeperTypes.SWAMP.attributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DRIPSTONE_CREEPER, CreeperTypes.DRIPSTONE.attributes());
        FabricDefaultAttributeRegistry.register(ModEntities.CAVE_CREEPER, CreeperTypes.CAVE.attributes());
        FabricDefaultAttributeRegistry.register(ModEntities.DARK_OAK_CREEPER, CreeperTypes.DARK_OAK.attributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SPRUCE_CREEPER, CreeperTypes.SPRUCE.attributes());
        FabricDefaultAttributeRegistry.register(ModEntities.BEACH_CREEPER, CreeperTypes.BEACH.attributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SNOWY_CREEPER, CreeperTypes.SNOWY.attributes());
    }

    public void addSpawnRules() {
        SpawnRestrictionAccessor.callRegister(ModEntities.JUNGLE_CREEPER,
                SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, Creepers::checkDayMonsterSpawnRulesAbove);
        SpawnRestrictionAccessor.callRegister(ModEntities.BAMBOO_CREEPER,
                SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING, Creepers::checkDayMonsterSpawnRulesAbove);
        SpawnRestrictionAccessor.callRegister(ModEntities.DESERT_CREEPER,
            SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Creepers::checkMonsterSpawnRulesAbove);
        SpawnRestrictionAccessor.callRegister(ModEntities.BADLANDS_CREEPER,
            SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Creepers::checkMonsterSpawnRulesAbove);
        SpawnRestrictionAccessor.callRegister(ModEntities.HILLS_CREEPER,
            SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Creepers::checkMonsterSpawnRulesAbove);
        SpawnRestrictionAccessor.callRegister(ModEntities.SAVANNAH_CREEPER,
            SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Creepers::checkMonsterSpawnRulesAbove);
        SpawnRestrictionAccessor.callRegister(ModEntities.MUSHROOM_CREEPER,
            SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Creepers::checkDayMonsterSpawnRulesAbove);
        SpawnRestrictionAccessor.callRegister(ModEntities.SWAMP_CREEPER,
            SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Creepers::checkMonsterSpawnRulesAbove);
        SpawnRestrictionAccessor.callRegister(ModEntities.DRIPSTONE_CREEPER,
            SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Creepers::checkMonsterSpawnRulesCave);
        SpawnRestrictionAccessor.callRegister(ModEntities.CAVE_CREEPER,
            SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Creepers::checkMonsterSpawnRulesCave);
        SpawnRestrictionAccessor.callRegister(ModEntities.DARK_OAK_CREEPER,
            SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Creepers::checkMonsterSpawnRulesAbove);
        SpawnRestrictionAccessor.callRegister(ModEntities.SPRUCE_CREEPER,
            SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Creepers::checkMonsterSpawnRulesAbove);
        SpawnRestrictionAccessor.callRegister(ModEntities.BEACH_CREEPER,
            SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Creepers::checkMonsterSpawnRulesAbove);
        SpawnRestrictionAccessor.callRegister(ModEntities.SNOWY_CREEPER,
            SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Creepers::checkDayMonsterSpawnRulesAbove);
    }

    public void addCreepers() {
        addCreeper(BiomeSelectors.categories(Biome.Category.BEACH), ModEntities.BEACH_CREEPER);
        addCreeper(BiomeSelectors.categories(Biome.Category.BEACH), ModEntities.CAVE_CREEPER);

        addCreeper(BiomeSelectors.categories(Biome.Category.ICY), ModEntities.SNOWY_CREEPER);
        addCreeper(BiomeSelectors.categories(Biome.Category.ICY), ModEntities.CAVE_CREEPER);

        addCreeper(BiomeSelectors.categories(Biome.Category.DESERT), ModEntities.DESERT_CREEPER);
        addCreeper(BiomeSelectors.categories(Biome.Category.DESERT), ModEntities.CAVE_CREEPER);

        addCreeper(BiomeSelectors.categories(Biome.Category.EXTREME_HILLS), ModEntities.HILLS_CREEPER);
        addCreeper(BiomeSelectors.categories(Biome.Category.EXTREME_HILLS), ModEntities.CAVE_CREEPER);

        addCreeper(BiomeSelectors.categories(Biome.Category.SAVANNA), ModEntities.SAVANNAH_CREEPER);
        addCreeper(BiomeSelectors.categories(Biome.Category.SAVANNA), ModEntities.CAVE_CREEPER);

        addCreeper(BiomeSelectors.categories(Biome.Category.MESA), ModEntities.BADLANDS_CREEPER);
        addCreeper(BiomeSelectors.categories(Biome.Category.MESA), ModEntities.CAVE_CREEPER);

        BiomeModifications.addSpawn(BiomeSelectors.categories(Biome.Category.MUSHROOM), SpawnGroup.CREATURE, ModEntities.MUSHROOM_CREEPER, 10, 1, 2);

        addCreeper(BiomeSelectors.categories(Biome.Category.TAIGA), ModEntities.SPRUCE_CREEPER);
        addCreeper(BiomeSelectors.categories(Biome.Category.TAIGA), ModEntities.CAVE_CREEPER);

        addCreeper(BiomeSelectors.categories(Biome.Category.MOUNTAIN)
                .and(ctx -> ctx.getBiome().getPrecipitation().equals(Biome.Precipitation.SNOW)), ModEntities.SNOWY_CREEPER);
        addCreeper(BiomeSelectors.categories(Biome.Category.MOUNTAIN)
                .and(ctx -> !ctx.getBiome().getPrecipitation().equals(Biome.Precipitation.SNOW)), ModEntities.HILLS_CREEPER);
        addCreeper(BiomeSelectors.categories(Biome.Category.MOUNTAIN), ModEntities.CAVE_CREEPER);

        addCreeper(BiomeSelectors.categories(Biome.Category.UNDERGROUND), ModEntities.DRIPSTONE_CREEPER);
        addCreeper(BiomeSelectors.categories(Biome.Category.UNDERGROUND), ModEntities.CAVE_CREEPER);

        addCreeper(BiomeSelectors.categories(Biome.Category.JUNGLE), ModEntities.JUNGLE_CREEPER);
        addCreeper(BiomeSelectors.categories(Biome.Category.JUNGLE), ModEntities.JUNGLE_CREEPER);
        addCreeper(BiomeSelectors.categories(Biome.Category.JUNGLE), ModEntities.CAVE_CREEPER);

        addCreeper(BiomeSelectors.categories(Biome.Category.SWAMP), ModEntities.SWAMP_CREEPER);
        addCreeper(BiomeSelectors.categories(Biome.Category.SWAMP), ModEntities.CAVE_CREEPER);

        addCreeper(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST), ModEntities.DARK_OAK_CREEPER);
        addCreeper(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST), ModEntities.CAVE_CREEPER);
    }

    public void removeCreepers() {
        Predicate<BiomeSelectionContext> creepersToRemove = BiomeSelectors.categories(
                Biome.Category.BEACH,
                Biome.Category.ICY,
                Biome.Category.DESERT, Biome.Category.EXTREME_HILLS, Biome.Category.SAVANNA, Biome.Category.MESA, Biome.Category.MUSHROOM,
                Biome.Category.TAIGA, Biome.Category.MOUNTAIN, Biome.Category.UNDERGROUND, Biome.Category.JUNGLE, Biome.Category.SWAMP)
                .or(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST));

        removeCreeper(creepersToRemove);
    }

    public static boolean checkMonsterSpawnRulesCave(EntityType<? extends HostileEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return pos.getY() < world.getSeaLevel() && !world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK) && world.getDifficulty() != Difficulty.PEACEFUL && HostileEntity.isSpawnDark(world, pos, random) && HostileEntity.canMobSpawn(type, world, spawnReason, pos, random);
    }

    public static boolean checkMonsterSpawnRulesAbove(EntityType<? extends HostileEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return pos.getY() > world.getSeaLevel() && world.getDifficulty() != Difficulty.PEACEFUL && HostileEntity.isSpawnDark(world, pos, random) && HostileEntity.canMobSpawn(type, world, spawnReason, pos, random);
    }

    public static boolean checkDayMonsterSpawnRulesAbove(EntityType<? extends HostileEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockState state = world.getBlockState(pos.down());
        boolean isGrassLike = state.isOf(Blocks.GRASS_BLOCK) || state.isOf(Blocks.PODZOL) || state.isOf(Blocks.MYCELIUM) || state.isOf(Blocks.DIRT);
        return pos.getY() > world.getSeaLevel() && world.getDifficulty() != Difficulty.PEACEFUL && HostileEntity.canMobSpawn(type, world, spawnReason, pos, random) &&
                (isGrassLike || state.isIn(BlockTags.BASE_STONE_OVERWORLD) || state.getBlock() instanceof LeavesBlock);
    }

    private <E extends BaseCreeper> void addCreeper(Predicate<BiomeSelectionContext> selectors, EntityType<E> entityType) {
        BiomeModifications.addSpawn(selectors, SpawnGroup.MONSTER, entityType, 75, 2, 2);
    }

    private void removeCreeper(Predicate<BiomeSelectionContext> biomeSelector) {
        Identifier id = Registry.ENTITY_TYPE.getId(EntityType.CREEPER);
        Preconditions.checkState(id != Registry.ENTITY_TYPE.getDefaultId(), "Unregistered entity type: %s", EntityType.CREEPER);

        BiomeModifications.create(id).add(ModificationPhase.REMOVALS, biomeSelector,
                context -> context.getSpawnSettings().removeSpawnsOfEntityType(EntityType.CREEPER));
    }
}
