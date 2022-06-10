package tech.thatgravyboat.creeperoverhaul.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

public class ModSpawns {

    public static void addSpawnRules() {
        register(ModEntities.JUNGLE_CREEPER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, ModSpawns::checkMonsterSpawnRulesAbove);
        register(ModEntities.BAMBOO_CREEPER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, ModSpawns::checkDayMonsterSpawnRulesAbove);
        register(ModEntities.DESERT_CREEPER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawns::checkMonsterSpawnRulesAbove);
        register(ModEntities.BADLANDS_CREEPER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawns::checkMonsterSpawnRulesAbove);
        register(ModEntities.HILLS_CREEPER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawns::checkMonsterSpawnRulesAbove);
        register(ModEntities.SAVANNAH_CREEPER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawns::checkMonsterSpawnRulesAbove);
        register(ModEntities.MUSHROOM_CREEPER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawns::checkDayMonsterSpawnRulesAbove);
        register(ModEntities.SWAMP_CREEPER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawns::checkMonsterSpawnRulesAbove);
        register(ModEntities.DRIPSTONE_CREEPER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawns::checkMonsterSpawnRulesCave);
        register(ModEntities.CAVE_CREEPER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawns::checkMonsterSpawnRulesCave);
        register(ModEntities.DARK_OAK_CREEPER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawns::checkMonsterSpawnRulesAbove);
        register(ModEntities.SPRUCE_CREEPER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawns::checkMonsterSpawnRulesAbove);
        register(ModEntities.BEACH_CREEPER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawns::checkMonsterSpawnRulesAbove);
        register(ModEntities.SNOWY_CREEPER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModSpawns::checkDayMonsterSpawnRulesAbove);
    }

    public static boolean checkMonsterSpawnRulesCave(EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        return pPos.getY() < pLevel.getSeaLevel() && !pLevel.getBlockState(pPos.below()).is(Blocks.GRASS_BLOCK) && pLevel.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(pLevel, pPos, pRandom) && Monster.checkMobSpawnRules(pType, pLevel, pReason, pPos, pRandom);
    }

    public static boolean checkMonsterSpawnRulesAbove(EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        return pPos.getY() > pLevel.getSeaLevel() && pLevel.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(pLevel, pPos, pRandom) && Monster.checkMobSpawnRules(pType, pLevel, pReason, pPos, pRandom);
    }

    public static boolean checkDayMonsterSpawnRulesAbove(EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        BlockState state = pLevel.getBlockState(pPos.below());
        boolean isGrassLike = state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.PODZOL) || state.is(Blocks.MYCELIUM) || state.is(Blocks.DIRT);
        return pPos.getY() > pLevel.getSeaLevel() && pLevel.getDifficulty() != Difficulty.PEACEFUL && Monster.checkMobSpawnRules(pType, pLevel, pReason, pPos, pRandom) &&
                (isGrassLike || state.is(BlockTags.BASE_STONE_OVERWORLD) || state.getBlock() instanceof LeavesBlock);
    }


    @ExpectPlatform
    public static <T extends Mob> void register(Supplier<EntityType<T>> entityType, SpawnPlacements.Type type, Heightmap.Types types, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {

    }
}
