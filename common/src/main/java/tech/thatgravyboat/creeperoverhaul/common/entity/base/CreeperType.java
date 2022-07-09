package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import tech.thatgravyboat.creeperoverhaul.common.utils.PlatformUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public record CreeperType(
        ResourceLocation texture,
        ResourceLocation glowingTexture,
        ResourceLocation chargedTexture,
        ResourceLocation model,
        ResourceLocation shearedModel,
        ResourceLocation animation,
        int melee,
        Map<Predicate<BlockState>, Function<RandomSource, BlockState>> replacer,
        Collection<EntityType<?>> entitiesAfraidOf,
        Collection<MobEffectInstance> inflictingPotions,
        Collection<MobEffectInstance> potionsWhenDead,
        Collection<Class<? extends LivingEntity>> entities,
        Collection<DamageSource> immunities,
        AttributeSupplier attributes,
        boolean shearable,

        Supplier<SoundEvent> deathSound,
        Supplier<SoundEvent> explosionSound,
        Supplier<SoundEvent> hitSound,
        Supplier<SoundEvent> hurtSound,
        Supplier<SoundEvent> primeSound
) {

    public Optional<SoundEvent> getDeathSound() {
        return Optional.ofNullable(deathSound.get());
    }

    public Optional<SoundEvent> getExplosionSound() {
        return Optional.ofNullable(explosionSound.get());
    }

    public Optional<SoundEvent> getHitSound() {
        return Optional.ofNullable(hitSound.get());
    }

    public Optional<SoundEvent> getHurtSound() {
        return Optional.ofNullable(hurtSound.get());
    }

    public Optional<SoundEvent> getPrimeSound() {
        return Optional.ofNullable(primeSound.get());
    }

    public static class Builder {
        private ResourceLocation texture;
        private ResourceLocation glowingTexture;
        private ResourceLocation chargedTexture;
        private ResourceLocation model;
        private ResourceLocation shearedModel;
        private ResourceLocation animation;
        private int melee = 0;
        private final List<EntityType<?>> afraidOf = new ArrayList<>();
        private final List<MobEffectInstance> inflictingPotions = new ArrayList<>();
        private final List<MobEffectInstance> potionsWhenDying = new ArrayList<>();
        private final List<Class<? extends LivingEntity>> attackingEntities = new ArrayList<>();
        private final List<DamageSource> immunities = new ArrayList<>();
        private final Map<Predicate<BlockState>, Function<RandomSource, BlockState>> replacer = new HashMap<>();
        private final AttributeSupplier.Builder attributes = Creeper.createAttributes()
                .add(PlatformUtils.getModAttribute("reach_distance"), 0)
                .add(PlatformUtils.getModAttribute("swim_speed"));
        private boolean shearable;

        private Supplier<SoundEvent> deathSound = () -> SoundEvents.CREEPER_DEATH;
        private Supplier<SoundEvent> explosionSound = null;
        private Supplier<SoundEvent> hitSound = null;
        private Supplier<SoundEvent> hurtSound = () -> SoundEvents.CREEPER_HURT;
        private Supplier<SoundEvent> primeSound = () -> SoundEvents.CREEPER_PRIMED;

        public Builder setTexture(ResourceLocation texture) {
            this.texture = texture;
            return this;
        }

        public Builder setGlowingTexture(ResourceLocation glowingTexture) {
            this.glowingTexture = glowingTexture;
            return this;
        }

        public Builder setChargedTexture(ResourceLocation chargedTexture) {
            this.chargedTexture = chargedTexture;
            return this;
        }

        public Builder setModel(ResourceLocation model) {
            this.model = model;
            return this;
        }

        public Builder setShearedModel(ResourceLocation shearedModel) {
            this.shearedModel = shearedModel;
            return this;
        }

        public Builder setAnimation(ResourceLocation animation) {
            this.animation = animation;
            return this;
        }

        public Builder setMelee(int melee) {
            this.melee = melee;
            return this;
        }

        public Builder addReplacer(Predicate<BlockState> predicate, Function<RandomSource, BlockState> function) {
            this.replacer.put(predicate, function);
            return this;
        }

        public Builder addAfraidOf(EntityType<?> entity) {
            this.afraidOf.add(entity);
            return this;
        }

        public Builder addInflictingPotion(MobEffectInstance potion) {
            this.inflictingPotions.add(potion);
            return this;
        }

        public Builder addPotionsWhenDying(MobEffectInstance potion) {
            this.potionsWhenDying.add(potion);
            return this;
        }

        public Builder addAttackingEntities(Class<? extends LivingEntity> attackingEntities) {
            this.attackingEntities.add(attackingEntities);
            return this;
        }

        public Builder addImmunity(DamageSource source) {
            this.immunities.add(source);
            return this;
        }

        public Builder addAttribute(String attribute, double value) {
            Attribute modAttribute = PlatformUtils.getModAttribute(attribute);
            if (modAttribute == null) {
                throw new IllegalArgumentException("Mod Attribute " + attribute + " does not exist");
            }
            this.attributes.add(modAttribute, value);
            return this;
        }
        public Builder addAttribute(Attribute attribute, double value) {
            this.attributes.add(attribute, value);
            return this;
        }

        public Builder setShearable(boolean shearable) {
            this.shearable = shearable;
            return this;
        }

        public Builder setDeathSounds(Supplier<SoundEvent> sound) {
            this.deathSound = sound;
            return this;
        }

        public Builder setExplosionSounds(Supplier<SoundEvent> sound) {
            this.explosionSound = sound;
            return this;
        }

        public Builder setHitSounds(Supplier<SoundEvent> sound) {
            this.hitSound = sound;
            return this;
        }

        public Builder setHurtSounds(Supplier<SoundEvent> sound) {
            this.hurtSound = sound;
            return this;
        }

        public Builder setPrimeSounds(Supplier<SoundEvent> sound) {
            this.primeSound = sound;
            return this;
        }

        public CreeperType build() {
            return new CreeperType(texture, glowingTexture, chargedTexture, model, shearedModel, animation, melee, replacer, afraidOf, inflictingPotions, potionsWhenDying, attackingEntities, immunities, attributes.build(), shearable, deathSound, explosionSound, hitSound, hurtSound, primeSound);
        }
    }

}
