package tech.thatgravyboat.creeperoverhaul.common.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.config.SpawningConfig;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;
import tech.thatgravyboat.creeperoverhaul.common.entity.custom.PufferfishCreeper;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModItems;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModSounds;

import java.util.Locale;

public class CreeperTypes {

    public static final CreeperType JUNGLE = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/jungle/jungle_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/jungle/jungle_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor.png"))
            .setModel(modLoc("geo/jungle.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addReplacer(state -> state.is(Blocks.GRASS) || state.is(Blocks.FERN), random -> random.nextInt(1) == 0 ? Blocks.MELON.defaultBlockState() : null)
            .setDeathSounds(ModSounds.PLANT_DEATH)
            .setExplosionSounds(ModSounds.PLANT_EXPLOSION)
            .setHurtSounds(ModSounds.PLANT_HURT)
            .setPrimeSounds(ModSounds.PLANT_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowJungleCreeperSpawning)
            .addAttackingEntities(Player.class)
            .build();

    public static final CreeperType BAMBOO = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/bamboo/bamboo_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/bamboo/bamboo_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor.png"))
            .setModel(modLoc("geo/bamboo.geo.json"))
            .setAnimation(modLoc("animations/bamboo.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAfraidOf(EntityType.PANDA)
            .addAttribute(Attributes.MAX_HEALTH, 15)
            .addReplacer(state -> state.is(Blocks.GRASS) || state.is(Blocks.FERN), random -> random.nextInt(1) == 0 ? Blocks.BAMBOO.defaultBlockState() : null)
            .setDeathSounds(ModSounds.PLANT_DEATH)
            .setExplosionSounds(ModSounds.PLANT_EXPLOSION)
            .setHitSounds(ModSounds.PLANT_HIT)
            .setHurtSounds(ModSounds.PLANT_HURT)
            .setPrimeSounds(ModSounds.PLANT_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowBambooCreeperSpawning)
            .addAttackingEntities(Player.class)
            .build();

    public static final CreeperType DESERT = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/desert/desert_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/desert/desert_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_2.png"))
            .setModel(modLoc("geo/desert.geo.json"))
            .setShearedModel(modLoc("geo/desert_sheared.geo.json"))
            .setShearable(() -> new ItemStack(ModItems.TINY_CACTUS.get()))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            //.addImmunity(DamageSources)
            .addAttribute(Attributes.MAX_HEALTH, 30)
            .addAttribute(Attributes.KNOCKBACK_RESISTANCE, 1)
            .addReplacer(state -> state.is(Blocks.SAND) || state.is(Blocks.SANDSTONE), random -> random.nextInt(3) == 0 ? Blocks.CHISELED_SANDSTONE.defaultBlockState() : null)
            .setDeathSounds(ModSounds.SAND_DEATH)
            .setExplosionSounds(ModSounds.SAND_EXPLOSION)
            .setHurtSounds(ModSounds.SAND_HURT)
            .setPrimeSounds(ModSounds.SAND_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowDesertCreeperSpawning)
            .addAttackingEntities(Player.class)
            .build();

    public static final CreeperType BADLANDS = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/badlands/badlands_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/badlands/badlands_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_2.png"))
            .setModel(modLoc("geo/badlands.geo.json"))
            .setShearedModel(modLoc("geo/badlands_sheared.geo.json"))
            .setShearable(() -> new ItemStack(ModItems.TINY_CACTUS.get()))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            //.addImmunity(DamageSource.CACTUS)
            .addAttribute(Attributes.MAX_HEALTH, 30)
            .addAttribute(Attributes.KNOCKBACK_RESISTANCE, 1)
            .addReplacer(state -> state.is(Blocks.RED_SANDSTONE) || state.is(Blocks.RED_SAND), random -> random.nextInt(3) == 0 ? Blocks.CHISELED_RED_SANDSTONE.defaultBlockState() : null)
            .setDeathSounds(ModSounds.SAND_DEATH)
            .setExplosionSounds(ModSounds.SAND_EXPLOSION)
            .setHurtSounds(ModSounds.SAND_HURT)
            .setPrimeSounds(ModSounds.SAND_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowBadlandsCreeperSpawning)
            .addAttackingEntities(Player.class)
            .build();

    public static final CreeperType HILLS = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/hills/hills_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/hills/hills_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_2.png"))
            .setModel(modLoc("geo/hills.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAttribute(Attributes.MAX_HEALTH, 30)
            .addAttribute(Attributes.KNOCKBACK_RESISTANCE, 1)
            .addReplacer(state -> state.is(Blocks.GRASS) || state.is(Blocks.FERN), random -> random.nextInt(1) == 0 ? Blocks.PUMPKIN.defaultBlockState() : null)
            .setDeathSounds(ModSounds.STONE_DEATH)
            .setExplosionSounds(ModSounds.STONE_EXPLOSION)
            .setHurtSounds(ModSounds.STONE_HURT)
            .setPrimeSounds(ModSounds.STONE_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowHillsCreeperSpawning)
            .addAttackingEntities(Player.class)
            .build();

    public static final CreeperType SAVANNAH = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/savannah/savannah_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/savannah/savannah_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_3.png"))
            .setModel(modLoc("geo/savannah.geo.json"))
            .setAnimation(modLoc("animations/savannah.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAttribute(Attributes.MAX_HEALTH, 30)
            .addAttribute(Attributes.KNOCKBACK_RESISTANCE, 1)
            .addReplacer(state -> state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT), random -> random.nextInt(3) == 0 ? Blocks.COARSE_DIRT.defaultBlockState() : null)
            .setDeathSounds(ModSounds.WOOD_DEATH)
            .setExplosionSounds(ModSounds.WOOD_EXPLOSION)
            .setHurtSounds(ModSounds.WOOD_HURT)
            .setHitSounds(ModSounds.WOOD_HIT)
            .setPrimeSounds(ModSounds.WOOD_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowSavannahCreeperSpawning)
            .addAttackingEntities(Player.class)
            .build();

    public static final CreeperType MUSHROOM = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/mushroom/mushroom_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/mushroom/mushroom_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_4.png"))
            .setModel(modLoc("geo/mushroom.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addReplacer(state -> state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT), random -> random.nextInt(3) == 0 ? Blocks.MYCELIUM.defaultBlockState() : null)
            .setDeathSounds(ModSounds.PLANT_DEATH)
            .setExplosionSounds(ModSounds.PLANT_EXPLOSION)
            .setHurtSounds(ModSounds.PLANT_HURT)
            .setPrimeSounds(ModSounds.PLANT_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowMushroomCreeperSpawning)
            .addAttackingEntities(Player.class)
            .build();

    public static final CreeperType SWAMP = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/swamp/swamp_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/swamp/swamp_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor.png"))
            .setModel(modLoc("geo/swamp.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAttribute("swim_speed", 2)
            .addReplacer(state -> state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT), random -> random.nextInt(3) == 0 ? Blocks.MUD.defaultBlockState() : null)
            .setDeathSounds(ModSounds.PLANT_DEATH)
            .setExplosionSounds(ModSounds.PLANT_EXPLOSION)
            .setHurtSounds(ModSounds.PLANT_HURT)
            .setPrimeSounds(ModSounds.PLANT_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowSwampCreeperSpawning)
            .addAttackingEntities(Player.class)
            .build();

    public static final CreeperType DRIPSTONE = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/dripstone/dripstone_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/dripstone/dripstone_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_2.png"))
            .setModel(modLoc("geo/dripstone.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAttribute(Attributes.MAX_HEALTH, 30)
            .addAttribute(Attributes.KNOCKBACK_RESISTANCE, 1)
            .addReplacer(state -> state.is(Blocks.STONE) || state.is(Blocks.TUFF), random -> random.nextInt(3) == 0 ? Blocks.DRIPSTONE_BLOCK.defaultBlockState() : null)
            .setDeathSounds(ModSounds.STONE_DEATH)
            .setExplosionSounds(ModSounds.STONE_EXPLOSION)
            .setHurtSounds(ModSounds.STONE_HURT)
            .setPrimeSounds(ModSounds.STONE_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowDripstoneCreeperSpawning)
            .addAttackingEntities(Player.class)
            .build();

    public static final CreeperType CAVE = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/cave/cave_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/cave/cave_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_2.png"))
            .setModel(modLoc("geo/cave.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAttribute(Attributes.MAX_HEALTH, 30)
            .addAttribute(Attributes.KNOCKBACK_RESISTANCE, 1)
            .addReplacer(state -> state.is(Blocks.STONE), random -> random.nextInt(3) == 0 ? Blocks.COBBLESTONE.defaultBlockState() : null)
            .setDeathSounds(ModSounds.STONE_DEATH)
            .setExplosionSounds(ModSounds.STONE_EXPLOSION)
            .setHurtSounds(ModSounds.STONE_HURT)
            .setPrimeSounds(ModSounds.STONE_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowCaveCreeperSpawning)
            .addAttackingEntities(Player.class)
            .build();

    public static final CreeperType DARK_OAK = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/dark_oak/dark_oak_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/dark_oak/dark_oak_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_3.png"))
            .setModel(modLoc("geo/dark_oak.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addReplacer(state -> state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT), random -> random.nextInt(3) == 0 ? Blocks.MOSSY_COBBLESTONE.defaultBlockState() : null)
            .setDeathSounds(ModSounds.WOOD_DEATH)
            .setExplosionSounds(ModSounds.WOOD_EXPLOSION)
            .setHurtSounds(ModSounds.WOOD_HURT)
            .setPrimeSounds(ModSounds.WOOD_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowDarkOakCreeperSpawning)
            .addAttackingEntities(Player.class)
            .build();

    public static final CreeperType SPRUCE = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/spruce/spruce_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/spruce/spruce_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_2.png"))
            .setModel(modLoc("geo/spruce.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addReplacer(state -> state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT), random -> random.nextInt(3) == 0 ? Blocks.PODZOL.defaultBlockState() : null)
            .setDeathSounds(ModSounds.STONE_DEATH)
            .setExplosionSounds(ModSounds.STONE_EXPLOSION)
            .setHurtSounds(ModSounds.STONE_HURT)
            .setPrimeSounds(ModSounds.STONE_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowSpruceCreeperSpawning)
            .addAttackingEntities(Player.class)
            .build();

    public static final CreeperType BEACH = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/beach/beach_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/beach/beach_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_2.png"))
            .setModel(modLoc("geo/beach.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            //.addImmunity(DamageSource.DROWN)
            .addAttribute(Attributes.MAX_HEALTH, 15)
            .addAttribute("swim_speed", 2)
            .addReplacer(state -> state.is(Blocks.SAND) || state.is(Blocks.GRAVEL), random -> random.nextInt(3) == 0 ? Blocks.WATER.defaultBlockState() : null)
            .setDeathSounds(ModSounds.SAND_DEATH)
            .setExplosionSounds(ModSounds.SAND_EXPLOSION)
            .setHurtSounds(ModSounds.SAND_HURT)
            .setPrimeSounds(ModSounds.SAND_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowBeachCreeperSpawning)
            .addAttackingEntities(Player.class)
            .build();

    public static final CreeperType SNOWY = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/snowy/snowy_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/snowy/snowy_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor.png"))
            .setModel(modLoc("geo/snowy.geo.json"))
            .setAnimation(modLoc("animations/snowy.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addReplacer(state -> state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT), random -> random.nextInt(3) == 0 ? Blocks.POWDER_SNOW.defaultBlockState() : null)
            .setDeathSounds(ModSounds.PLANT_DEATH)
            .setExplosionSounds(ModSounds.PLANT_EXPLOSION)
            .setHurtSounds(ModSounds.PLANT_HURT)
            .setPrimeSounds(ModSounds.PLANT_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowSnowyCreeperSpawning)
            .addAttackingEntities(Player.class)
            .build();

    public static final CreeperType OCEAN = new CreeperType.Builder()
            .setTexture(creeper -> {
                if (creeper instanceof PufferfishCreeper pufferfish) {
                    int id = pufferfish.getPuffId();
                    PufferfishCreeper.Variant variant = pufferfish.getVariant();
                    return modLoc("textures/entity/ocean/" + variant.name().toLowerCase(Locale.ROOT) + "_" + id + ".png");
                }
                return modLoc("textures/entity/ocean/brown_1.png");
            })
            .setGlowingTexture(modLoc("textures/entity/ocean/glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_4.png"))
            .setModel(creeper -> {
                if (creeper instanceof PufferfishCreeper pufferfish) {
                    int id = pufferfish.getPuffId();
                    return modLoc("geo/ocean_" + id + ".geo.json");
                }
                return modLoc("geo/ocean_1.geo.json");
            })
            .setAnimation(modLoc("animations/ocean.animation.json"))
            .addAttribute(Attributes.MAX_HEALTH, 16)
            .addAttribute(Attributes.MOVEMENT_SPEED, 2)
            .addAttribute("reach_distance", 3)
            .addAttribute("swim_speed", 2)
            .setDeathSounds(ModSounds.OCEAN_DEATH)
            .setHurtSound(creeper -> {
                if (creeper instanceof PufferfishCreeper fish && fish.getPuffId() == 3) {
                    return ModSounds.OCEAN_HURT_INFLATED.get();
                }
                return ModSounds.OCEAN_HURT_DEFLATED.get();
            })
            .setFlopSounds(() -> SoundEvents.PUFFER_FISH_FLOP)
            .setCanSpawn(() -> SpawningConfig.allowOceanCreeperSpawning)
            .build();

    private static ResourceLocation modLoc(String string) {
        return new ResourceLocation(Creepers.MODID, string);
    }
}
