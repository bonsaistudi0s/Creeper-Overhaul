package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record CreeperType(
        Identifier texture,
        Identifier glowingTexture,
        Identifier chargedTexture,
        Identifier model,
        Identifier shearedModel,
        Identifier animation,
        int melee,
        Collection<EntityType<?>> entitiesAfraidOf,
        Collection<StatusEffectInstance> inflictingPotions,
        Collection<StatusEffectInstance> potionsWhenDead,
        Collection<Class<? extends LivingEntity>> entities,
        Collection<DamageSource> immunities,
        DefaultAttributeContainer.Builder attributes,
        boolean shearable
    ) {

    public static class Builder {
        private Identifier texture;
        private Identifier glowingTexture;
        private Identifier chargedTexture;
        private Identifier model;
        private Identifier shearedModel;
        private Identifier animation;
        private int melee = 0;
        private final List<EntityType<?>> afraidOf = new ArrayList<>();
        private final List<StatusEffectInstance> inflictingPotions = new ArrayList<>();
        private final List<StatusEffectInstance> potionsWhenDying = new ArrayList<>();
        private final List<Class<? extends LivingEntity>> attackingEntities = new ArrayList<>();
        private final List<DamageSource> immunities = new ArrayList<>();
        private final DefaultAttributeContainer.Builder attributes = BaseCreeper.createAttributes();
        private boolean shearable;

        public Builder setTexture(Identifier texture) {
            this.texture = texture;
            return this;
        }

        public Builder setGlowingTexture(Identifier glowingTexture) {
            this.glowingTexture = glowingTexture;
            return this;
        }

        public Builder setChargedTexture(Identifier chargedTexture) {
            this.chargedTexture = chargedTexture;
            return this;
        }

        public Builder setModel(Identifier model) {
            this.model = model;
            return this;
        }

        public Builder setShearedModel(Identifier shearedModel) {
            this.shearedModel = shearedModel;
            return this;
        }

        public Builder setAnimation(Identifier animation) {
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

        public Builder addInflictingPotion(StatusEffectInstance potion) {
            this.inflictingPotions.add(potion);
            return this;
        }

        public Builder addPotionsWhenDying(StatusEffectInstance potion) {
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

        public Builder addAttribute(EntityAttribute attribute, double value) {
            this.attributes.add(attribute, value);
            return this;
        }

        public Builder setShearable(boolean shearable) {
            this.shearable = shearable;
            return this;
        }

        public CreeperType build() {
            return new CreeperType(texture, glowingTexture, chargedTexture, model, shearedModel, animation, melee, afraidOf, inflictingPotions, potionsWhenDying, attackingEntities, immunities, attributes, shearable);
        }
    }

}
