package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import tech.thatgravyboat.creeperoverhaul.common.entity.goals.CreeperAvoidEntitiesGoal;
import tech.thatgravyboat.creeperoverhaul.common.entity.goals.CreeperMeleeAttackGoal;
import tech.thatgravyboat.creeperoverhaul.common.entity.goals.CreeperSwellGoal;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModItems;
import tech.thatgravyboat.creeperoverhaul.common.utils.PlatformUtils;

import java.util.Collection;

public class BaseCreeper extends Creeper implements IAnimatable {

    private static final EntityDataAccessor<Boolean> DATA_IS_ATTACKING = SynchedEntityData.defineId(BaseCreeper.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_SHEARED = SynchedEntityData.defineId(BaseCreeper.class, EntityDataSerializers.BOOLEAN);

    private final AnimationFactory factory = new AnimationFactory(this);

    private int oldSwell;
    private int swell;
    private int maxSwell = 30;
    private final CreeperType type;

    public BaseCreeper(EntityType<? extends Creeper> entityType, Level level, CreeperType type) {
        super(entityType, level);
        this.type = type;
        if (!level.isClientSide) this.type.entities().forEach(e ->
                this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, e, true)));
    }

    //region Goals
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new CreeperSwellGoal(this));
        this.goalSelector.addGoal(3, new CreeperAvoidEntitiesGoal(this, 6.0F, 1.0, 1.2));
        this.goalSelector.addGoal(4, new CreeperMeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
       registerAttackGoals();
        if (shouldRevenge()) this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    protected void registerAttackGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    //endregion

    //region State Management
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_IS_ATTACKING, false);
        this.getEntityData().define(DATA_IS_SHEARED, false);
    }

    public void setAttacking(boolean attacking) {
        this.getEntityData().set(DATA_IS_ATTACKING, attacking);
    }

    public boolean isSheared() {
        return this.entityData.get(DATA_IS_SHEARED);
    }

    public void setSheared(boolean sheared) {
        this.getEntityData().set(DATA_IS_SHEARED, sheared);
    }

    //endregion

    //region NBT
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Sheared", isSheared());
        tag.putShort("Fuse", (short)this.maxSwell);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setSheared(tag.getBoolean("Sheared"));
        if (tag.contains("Fuse", 99)) {
            this.maxSwell = tag.getShort("Fuse");
        }
    }
    //endregion

    //region Swelling
    public boolean causeFallDamage(float f, float g, @NotNull DamageSource source) {
        boolean bl = super.causeFallDamage(f, g, source);
        this.swell += (int)(f * 1.5F);
        if (this.swell > this.maxSwell - 5) {
            this.swell = this.maxSwell - 5;
        }

        return bl;
    }

    public void tick() {
        if (this.isAlive()) {
            this.oldSwell = this.swell;
            if (this.isIgnited()) {
                this.setSwellDir(1);
            }

            int i = this.getSwellDir();
            if (i > 0 && this.swell == 0) {
                this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
                this.gameEvent(GameEvent.PRIME_FUSE);
            }

            this.swell += i;
            if (this.swell < 0) {
                this.swell = 0;
            }

            if (this.swell >= this.maxSwell) {
                this.swell = this.maxSwell;
                explode();
            }
        }

        super.tick();
    }

    public void explode() {
        if (!this.level.isClientSide) {
            Explosion.BlockInteraction interaction = PlatformUtils.getInteractionForCreeper(this);
            this.dead = true;
            Explosion explosion = this.level.explode(this, this.getX(), this.getY(), this.getZ(), (float)3 * (this.isPowered() ? 2.0F : 1.0F), interaction);
            this.discard();
            if (!this.type.inflictingPotions().isEmpty()) {
                explosion.getHitPlayers().keySet().forEach(player -> {
                    Collection<MobEffectInstance> inflictingPotions = this.type.inflictingPotions().stream().map(MobEffectInstance::new).toList();
                    inflictingPotions.forEach(player::addEffect);
                });
            }
            if (this.type.dirtReplacement() != null) {
                Block replacement = this.type.dirtReplacement().get();
                explosion.getToBlow().stream()
                        .map(BlockPos::below)
                        .filter(pos -> {
                            BlockState state = level.getBlockState(pos);
                            return (state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT)) && this.random.nextInt(3) == 0;
                        })
                        .forEach(pos -> level.setBlock(pos, replacement.defaultBlockState(), Block.UPDATE_ALL));
            }
            Collection<MobEffectInstance> collection = this.getActiveEffects().stream().map(MobEffectInstance::new).toList();
            summonCloudWithEffects(collection);
            summonCloudWithEffects(this.type.potionsWhenDead().stream().map(MobEffectInstance::new).toList());
        }
    }

    private void summonCloudWithEffects(Collection<MobEffectInstance> effects) {
        if (!effects.isEmpty()) {
            AreaEffectCloud cloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
            cloud.setRadius(2.5F);
            cloud.setRadiusOnUse(-0.5F);
            cloud.setWaitTime(10);
            cloud.setDuration(cloud.getDuration() / 2);
            cloud.setRadiusPerTick(-cloud.getRadius() / cloud.getDuration());
            effects.forEach(cloud::addEffect);

            this.level.addFreshEntity(cloud);
        }
    }

    public float getSwelling(float f) {
        return Mth.lerp(f, (float)this.oldSwell, (float)this.swell) / (float)(this.maxSwell - 2);
    }
    //endregion

    //region Interactions


    @Override
    protected InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (this.type.melee() == 0 && PlatformUtils.isFlintAndSteel(stack)) {
            this.level.playSound(player, this.blockPosition(), SoundEvents.FLINTANDSTEEL_USE, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.level.isClientSide) {
                ignite();
                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        if (this.type.shearable() && !isSheared() && PlatformUtils.isShears(stack)) {
            this.level.playSound(player, this.blockPosition(), SoundEvents.SNOW_GOLEM_SHEAR, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.level.isClientSide) {
                this.entityData.set(DATA_IS_SHEARED, true);
                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                this.spawnAtLocation(new ItemStack(ModItems.TINY_CACTUS.get()), 1.7F);
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        return InteractionResult.PASS;
    }
    //endregion

    public boolean canSwell() {
        return this.type.melee() == 0;
    }

    public boolean shouldRevenge() {
        return true;
    }

    @Override
    public double getAttributeValue(@NotNull Attribute attribute) {
        double val = super.getAttributeValue(attribute);
        return attribute.equals(Attributes.ATTACK_DAMAGE) && isPowered() ? val * 10 : val;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if (this.type.melee() == 0) return true;
        double f = this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        double g = this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (entity instanceof LivingEntity livingEntity) {
            f += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), livingEntity.getMobType());
            g += EnchantmentHelper.getKnockbackBonus(this);
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            entity.setSecondsOnFire(i * 4);
        }

        boolean bl = entity.hurt(DamageSource.mobAttack(this), (float) f);
        if (bl) {
            if (g > 0.0 && entity instanceof LivingEntity living) {
                living.knockback(g * 0.5F, Mth.sin(this.getYRot() * 0.017453292F), -Mth.cos(this.getYRot() * 0.017453292F));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }

            this.doEnchantDamageEffects(this, entity);
            this.setLastHurtMob(entity);
        }

        return bl;
    }

    @Override
    public float getSpeed() {
        if (!isPowered()) return super.getSpeed();
        return super.getSpeed() * 1.5f;
    }

    public boolean isAfraidOf(Entity entity) {
        return type.entitiesAfraidOf().contains(entity.getType());
    }

    public CreeperType getCreeperType() {
        return this.type;
    }

    //region Animation
    private <E extends IAnimatable> PlayState idle(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creeper.idle", true));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState action(AnimationEvent<E> event) {
        Animation animation = event.getController().getCurrentAnimation();
        if (entityData.get(DATA_IS_ATTACKING)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creeper.attack", false));
            return PlayState.CONTINUE;
        } else if (animation != null && animation.animationName.equals("animation.creeper.attack") && event.getController().getAnimationState().equals(AnimationState.Running)) {
            return PlayState.CONTINUE;
        } else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creeper.walk", true));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "action_controller", 3, this::action));
        data.addAnimationController(new AnimationController<>(this, "idle_controller", 0, this::idle));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
    //endregion
}
