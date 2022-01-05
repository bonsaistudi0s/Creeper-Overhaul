package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public record CreeperType(
        ResourceLocation texture,
        ResourceLocation glowingTexture,
        ResourceLocation chargedTexture,
        ResourceLocation model,
        ResourceLocation shearedModel,
        ResourceLocation animation,
        int melee,
        Supplier<Block> dirtReplacement,
        Collection<EntityType<?>> entitiesAfraidOf,
        Collection<MobEffectInstance> inflictingPotions,
        Collection<MobEffectInstance> potionsWhenDead,
        Collection<Class<? extends LivingEntity>> entities,
        Collection<DamageSource> immunities,
        AttributeSupplier attributes,
        boolean shearable
    ) {

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
        private Supplier<Block> dirtReplacement = null;
        private final AttributeSupplier.Builder attributes = BaseCreeper.createAttributes();
        private boolean shearable;

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

        public Builder setDirtReplacement(Supplier<Block> replacement) {
            this.dirtReplacement = replacement;
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

        public Builder addAttribute(Attribute attribute, double value) {
            this.attributes.add(attribute, value);
            return this;
        }

        public Builder setShearable(boolean shearable) {
            this.shearable = shearable;
            return this;
        }

        public CreeperType build() {
            return new CreeperType(texture, glowingTexture, chargedTexture, model, shearedModel, animation, melee, dirtReplacement, afraidOf, inflictingPotions, potionsWhenDying, attackingEntities, immunities, attributes.build(), shearable);
        }
    }

}
