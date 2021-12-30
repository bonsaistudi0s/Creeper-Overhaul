package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public record CreeperType(
        ResourceLocation texture,
        ResourceLocation glowingTexture,
        ResourceLocation chargedTexture,
        ResourceLocation model,
        ResourceLocation shearedModel,
        ResourceLocation animation,
        int melee,
        Collection<EntityType<?>> entitiesAfraidOf,
        Collection<MobEffectInstance> potionsWhenDead,
        Collection<Class<? extends LivingEntity>> entities,
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
        private List<EntityType<?>> afraidOf = new ArrayList<>();
        private List<MobEffectInstance> potionsWhenDying = new ArrayList<>();
        private List<Class<? extends LivingEntity>> attackingEntities = new ArrayList<>();
        private AttributeSupplier attributes;
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

        public Builder addAfraidOf(EntityType<?> entity) {
            this.afraidOf.add(entity);
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

        public Builder setAttributes(AttributeSupplier attributes) {
            this.attributes = attributes;
            return this;
        }

        public Builder setShearable(boolean shearable) {
            this.shearable = shearable;
            return this;
        }

        public CreeperType build() {
            return new CreeperType(texture, glowingTexture, chargedTexture, model, shearedModel, animation, melee, afraidOf, potionsWhenDying, attackingEntities, attributes, shearable);
        }
    }

}
