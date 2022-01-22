package tech.thatgravyboat.creeperoverhaul;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;
import tech.thatgravyboat.creeperoverhaul.client.ClientConfig;
import tech.thatgravyboat.creeperoverhaul.common.Events;
import tech.thatgravyboat.creeperoverhaul.common.entity.CreeperTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModBlocks;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModEntities;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModItems;

import java.util.Random;

@Mod(Creepers.MODID)
public class Creepers {

    public static final String MODID = "creeperoverhaul";

    private static Boolean usingOptifine = null;

    public static final Events EVENT = Events.getCurrentEvent();

    public Creepers() {
        GeckoLibMod.DISABLE_IN_DEV = true;
        GeckoLib.initialize();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_CONFIG);

        modEventBus.addListener(this::addAttributes);
        modEventBus.addListener(this::onComplete);
        modEventBus.addListener(this::onCommonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static boolean isUsingOptifine() {
        if (usingOptifine == null) {
            try {
                Class.forName("optifine.Installer");
                usingOptifine = true;
            }catch (Exception ignored) {
                usingOptifine = false;
            }
        }
        return usingOptifine;
    }

    @SubscribeEvent
    public void onMobDrops(LivingDropsEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        Level level = event.getEntityLiving().level;
        BlockPos pos = event.getEntityLiving().blockPosition();
        if (level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)){
            Entity killer = event.getSource().getEntity();
            if ((killer instanceof BaseCreeper baseCreeper && baseCreeper.canDropMobsSkull()) || (killer instanceof Creeper creeper && creeper.canDropMobsSkull())) {
                Item skull = null;
                if (livingEntity instanceof Skeleton) skull = Items.SKELETON_SKULL;
                if (livingEntity instanceof Creeper || livingEntity instanceof BaseCreeper) skull = Items.CREEPER_HEAD;
                if (livingEntity instanceof WitherSkeleton) skull = Items.WITHER_SKELETON_SKULL;
                if (livingEntity instanceof Zombie) skull = Items.ZOMBIE_HEAD;
                if (skull != null) {
                    if (killer instanceof BaseCreeper baseCreeper) baseCreeper.increaseDroppedSkulls();
                    if (killer instanceof Creeper creeper) creeper.increaseDroppedSkulls();
                    event.getDrops().add(new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(skull)));
                }
            }
        }
    }

    public void addAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.JUNGLE_CREEPER.get(), CreeperTypes.JUNGLE.attributes());
        event.put(ModEntities.BAMBOO_CREEPER.get(), CreeperTypes.BAMBOO.attributes());
        event.put(ModEntities.DESERT_CREEPER.get(), CreeperTypes.DESERT.attributes());
        event.put(ModEntities.BADLANDS_CREEPER.get(), CreeperTypes.BADLANDS.attributes());
        event.put(ModEntities.HILLS_CREEPER.get(), CreeperTypes.HILLS.attributes());
        event.put(ModEntities.SAVANNAH_CREEPER.get(), CreeperTypes.SAVANNAH.attributes());
        event.put(ModEntities.MUSHROOM_CREEPER.get(), CreeperTypes.MUSHROOM.attributes());
        event.put(ModEntities.SWAMP_CREEPER.get(), CreeperTypes.SWAMP.attributes());
        event.put(ModEntities.DRIPSTONE_CREEPER.get(), CreeperTypes.DRIPSTONE.attributes());
        event.put(ModEntities.CAVE_CREEPER.get(), CreeperTypes.CAVE.attributes());
        event.put(ModEntities.DARK_OAK_CREEPER.get(), CreeperTypes.DARK_OAK.attributes());
        event.put(ModEntities.SPRUCE_CREEPER.get(), CreeperTypes.SPRUCE.attributes());
        event.put(ModEntities.BEACH_CREEPER.get(), CreeperTypes.BEACH.attributes());
        event.put(ModEntities.SNOWY_CREEPER.get(), CreeperTypes.SNOWY.attributes());
    }

    public void onComplete(FMLLoadCompleteEvent event) {
        SpawnPlacements.register(ModEntities.JUNGLE_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Creepers::checkMonsterSpawnRulesAbove);
        SpawnPlacements.register(ModEntities.BAMBOO_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Creepers::checkDayMonsterSpawnRulesAbove);
        SpawnPlacements.register(ModEntities.DESERT_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Creepers::checkMonsterSpawnRulesAbove);
        SpawnPlacements.register(ModEntities.BADLANDS_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Creepers::checkMonsterSpawnRulesAbove);
        SpawnPlacements.register(ModEntities.HILLS_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Creepers::checkMonsterSpawnRulesAbove);
        SpawnPlacements.register(ModEntities.SAVANNAH_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Creepers::checkMonsterSpawnRulesAbove);
        SpawnPlacements.register(ModEntities.MUSHROOM_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Creepers::checkDayMonsterSpawnRulesAbove);
        SpawnPlacements.register(ModEntities.SWAMP_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Creepers::checkMonsterSpawnRulesAbove);
        SpawnPlacements.register(ModEntities.DRIPSTONE_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Creepers::checkMonsterSpawnRulesCave);
        SpawnPlacements.register(ModEntities.CAVE_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Creepers::checkMonsterSpawnRulesCave);
        SpawnPlacements.register(ModEntities.DARK_OAK_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Creepers::checkMonsterSpawnRulesAbove);
        SpawnPlacements.register(ModEntities.SPRUCE_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Creepers::checkMonsterSpawnRulesAbove);
        SpawnPlacements.register(ModEntities.BEACH_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Creepers::checkMonsterSpawnRulesAbove);
        SpawnPlacements.register(ModEntities.SNOWY_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Creepers::checkDayMonsterSpawnRulesAbove);
    }

    public void onCommonSetup(FMLCommonSetupEvent event) {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation(MODID, "tiny_cactus"), ModBlocks.TINY_CACTUS_POT);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void addCreepers(BiomeLoadingEvent event) {
        switch (event.getCategory()) {
            case BEACH -> {
                addCreeper(event, ModEntities.BEACH_CREEPER);
                addCreeper(event, ModEntities.CAVE_CREEPER);
            }
            case ICY -> {
                addCreeper(event, ModEntities.SNOWY_CREEPER);
                addCreeper(event, ModEntities.CAVE_CREEPER);
            }
            case DESERT -> {
                addCreeper(event, ModEntities.DESERT_CREEPER);
                addCreeper(event, ModEntities.CAVE_CREEPER);
            }
            case EXTREME_HILLS -> {
                addCreeper(event, ModEntities.HILLS_CREEPER);
                addCreeper(event, ModEntities.CAVE_CREEPER);
            }
            case SAVANNA -> {
                addCreeper(event, ModEntities.SAVANNAH_CREEPER);
                addCreeper(event, ModEntities.CAVE_CREEPER);
            }
            case MESA -> {
                addCreeper(event, ModEntities.BADLANDS_CREEPER);
                addCreeper(event, ModEntities.CAVE_CREEPER);
            }
            case MUSHROOM -> event.getSpawns().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.MUSHROOM_CREEPER.get(),10, 1, 2));
            case TAIGA -> {
                addCreeper(event, ModEntities.SPRUCE_CREEPER);
                addCreeper(event, ModEntities.CAVE_CREEPER);
            }
            case MOUNTAIN -> {
                addCreeper(event, event.getClimate().precipitation.equals(Biome.Precipitation.SNOW) ? ModEntities.SNOWY_CREEPER : ModEntities.HILLS_CREEPER);
                addCreeper(event, ModEntities.CAVE_CREEPER);
            }
            case UNDERGROUND -> {
                addCreeper(event, ModEntities.DRIPSTONE_CREEPER);
                addCreeper(event, ModEntities.CAVE_CREEPER);
            }
            case JUNGLE -> {
                addCreeper(event, ModEntities.JUNGLE_CREEPER);
                addCreeper(event, ModEntities.BAMBOO_CREEPER);
                addCreeper(event, ModEntities.CAVE_CREEPER);
            }
            case SWAMP -> {
                addCreeper(event, ModEntities.SWAMP_CREEPER);
                addCreeper(event, ModEntities.CAVE_CREEPER);
            }
            default -> {
                if (event.getName() != null && event.getName().getPath().equals("dark_forest")){
                    addCreeper(event, ModEntities.DARK_OAK_CREEPER);
                    addCreeper(event, ModEntities.CAVE_CREEPER);
                }
            }
        }
    }

    @SubscribeEvent
    public void removeCreepers(BiomeLoadingEvent event) {
        switch (event.getCategory()) {
            case BEACH, ICY, DESERT, EXTREME_HILLS, SAVANNA, MESA, MUSHROOM, TAIGA, MOUNTAIN, UNDERGROUND, JUNGLE, SWAMP ->
                    event.getSpawns().getSpawner(MobCategory.MONSTER).removeIf(data -> data.type.equals(EntityType.CREEPER));
            default -> {
                if (event.getName() != null && event.getName().getPath().equals("dark_forest")){
                    event.getSpawns().getSpawner(MobCategory.MONSTER).removeIf(data -> data.type.equals(EntityType.CREEPER));
                }
            }
        }
    }

    public static boolean checkMonsterSpawnRulesCave(EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, Random pRandom) {
        return pPos.getY() < pLevel.getSeaLevel() && !pLevel.getBlockState(pPos.below()).is(Blocks.GRASS_BLOCK) && pLevel.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(pLevel, pPos, pRandom) && Monster.checkMobSpawnRules(pType, pLevel, pReason, pPos, pRandom);
    }

    public static boolean checkMonsterSpawnRulesAbove(EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, Random pRandom) {
        return pPos.getY() > pLevel.getSeaLevel() && pLevel.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(pLevel, pPos, pRandom) && Monster.checkMobSpawnRules(pType, pLevel, pReason, pPos, pRandom);
    }

    public static boolean checkDayMonsterSpawnRulesAbove(EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, Random pRandom) {
        BlockState state = pLevel.getBlockState(pPos.below());
        boolean isGrassLike = state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.PODZOL) || state.is(Blocks.MYCELIUM) || state.is(Blocks.DIRT);
        return pPos.getY() > pLevel.getSeaLevel() && pLevel.getDifficulty() != Difficulty.PEACEFUL && Monster.checkMobSpawnRules(pType, pLevel, pReason, pPos, pRandom) &&
                (isGrassLike || state.is(BlockTags.BASE_STONE_OVERWORLD) || state.getBlock() instanceof LeavesBlock);
    }

    private <E extends BaseCreeper> void addCreeper(BiomeLoadingEvent event, RegistryObject<EntityType<E>> entityType) {
        event.getSpawns().addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(entityType.get(),75, 2, 2));
    }
}
