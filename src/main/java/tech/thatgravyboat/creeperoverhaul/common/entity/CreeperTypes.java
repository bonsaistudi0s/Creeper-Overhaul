package tech.thatgravyboat.creeperoverhaul.common.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.util.Identifier;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModAttributes;

public class CreeperTypes {

    public static final CreeperType JUNGLE = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/jungle/jungle_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/jungle/jungle_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor.png"))
            .setModel(modLoc("geo/jungle.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .build();

    public static final CreeperType BAMBOO = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/bamboo/bamboo_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/bamboo/bamboo_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor.png"))
            .setModel(modLoc("geo/bamboo.geo.json"))
            .setAnimation(modLoc("animations/bamboo.animation.json"))
            .setMelee(9)
            .addAfraidOf(EntityType.PANDA)
            .addAttribute(EntityAttributes.GENERIC_MAX_HEALTH, 15)
            .addAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2)
            .addAttribute(ModAttributes.REACH_DISTANCE, 2)
            .build();

    public static final CreeperType DESERT = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/desert/desert_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/desert/desert_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_2.png"))
            .setModel(modLoc("geo/desert.geo.json"))
            .setShearedModel(modLoc("geo/desert_sheared.geo.json"))
            .setShearable(true)
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addImmunity(DamageSource.CACTUS)
            .addAttribute(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5)
            .build();

    public static final CreeperType BADLANDS = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/badlands/badlands_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/badlands/badlands_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_2.png"))
            .setModel(modLoc("geo/badlands.geo.json"))
            .setShearedModel(modLoc("geo/badlands_sheared.geo.json"))
            .setShearable(true)
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addImmunity(DamageSource.CACTUS)
            .addAttribute(EntityAttributes.GENERIC_MAX_HEALTH, 30)
            .addAttribute(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1)
            .build();

    public static final CreeperType HILLS = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/hills/hills_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/hills/hills_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_2.png"))
            .setModel(modLoc("geo/hills.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAttribute(EntityAttributes.GENERIC_MAX_HEALTH, 30)
            .addAttribute(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5)
            .build();

    public static final CreeperType SAVANNAH = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/savannah/savannah_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/savannah/savannah_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_3.png"))
            .setModel(modLoc("geo/savannah.geo.json"))
            .setAnimation(modLoc("animations/savannah.animation.json"))
            .setMelee(5)
            .addAttribute(EntityAttributes.GENERIC_MAX_HEALTH, 25)
            .addAttribute(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5)
            .addAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3)
            .build();

    public static final CreeperType MUSHROOM = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/mushroom/mushroom_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/mushroom/mushroom_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_4.png"))
            .setModel(modLoc("geo/mushroom.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addPotionsWhenDying(new StatusEffectInstance(StatusEffects.POISON, 100, 1))
            .setDirtReplacement(() -> Blocks.MYCELIUM)
            .build();

    public static final CreeperType SWAMP = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/swamp/swamp_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/swamp/swamp_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor.png"))
            .setModel(modLoc("geo/swamp.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAttribute(ModAttributes.SWIM_SPEED, 2)
            .build();

    public static final CreeperType DRIPSTONE = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/dripstone/dripstone_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/dripstone/dripstone_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_2.png"))
            .setModel(modLoc("geo/dripstone.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAttribute(EntityAttributes.GENERIC_MAX_HEALTH, 12)
            .build();

    public static final CreeperType CAVE = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/cave/cave_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/cave/cave_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_2.png"))
            .setModel(modLoc("geo/cave.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAttribute(EntityAttributes.GENERIC_MAX_HEALTH, 25)
            .addAttribute(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.5)
            .build();

    public static final CreeperType DARK_OAK = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/dark_oak/dark_oak_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/dark_oak/dark_oak_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_3.png"))
            .setModel(modLoc("geo/dark_oak.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addInflictingPotion(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 0))
            .build();

    public static final CreeperType SPRUCE = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/spruce/spruce_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/spruce/spruce_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_2.png"))
            .setModel(modLoc("geo/spruce.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .build();

    public static final CreeperType BEACH = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/beach/beach_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/beach/beach_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor_2.png"))
            .setModel(modLoc("geo/beach.geo.json"))
            .setAnimation(modLoc("animations/creeper.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addImmunity(DamageSource.DROWN)
            .addAttribute(EntityAttributes.GENERIC_MAX_HEALTH, 15)
            .addAttribute(ModAttributes.SWIM_SPEED, 2)
            .build();

    public static final CreeperType SNOWY = new CreeperType.Builder()
            .setTexture(modLoc("textures/entity/snowy/snowy_creeper.png"))
            .setGlowingTexture(modLoc("textures/entity/snowy/snowy_creeper_glow.png"))
            .setChargedTexture(modLoc("textures/entity/armor/creeper_armor.png"))
            .setModel(modLoc("geo/snowy.geo.json"))
            .setAnimation(modLoc("animations/snowy.animation.json"))
            .setMelee(5)
            .addAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4)
            .addAttackingEntities(StrayEntity.class)
            .build();

    private static Identifier modLoc(String string) {
        return new Identifier(Creepers.MODID, string);
    }
}
