package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.ForgeEventFactory;
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

import java.util.Collection;

public class BaseCreeper extends Monster implements PowerableMob, IAnimatable {

    private static final EntityDataAccessor<Integer> DATA_SWELL_DIRECTION = SynchedEntityData.defineId(BaseCreeper.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_IS_POWERED = SynchedEntityData.defineId(BaseCreeper.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(BaseCreeper.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_ATTACKING = SynchedEntityData.defineId(BaseCreeper.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_SHEARED = SynchedEntityData.defineId(BaseCreeper.class, EntityDataSerializers.BOOLEAN);

    private int explosionRadius = 3;
    private int maxSwell = 30;
    private int swell;
    private int oldSwell;
    private int droppedSkulls;

    private final AnimationFactory factory = new AnimationFactory(this);

    private final CreeperType type;

    public BaseCreeper(EntityType<? extends BaseCreeper> entityType, Level level, CreeperType type) {
        super(entityType, level);
        this.type = type;
        this.type.entities().forEach(e -> this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, e, true)));
    }

    //region Goals And Data
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new CreeperSwellGoal(this));
        this.goalSelector.addGoal(3, new CreeperAvoidEntitiesGoal(this,6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(4, new CreeperMeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        registerNearestAttackableTargetGoal();
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    protected void registerNearestAttackableTargetGoal() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_POWERED, false);
        this.entityData.define(DATA_SWELL_DIRECTION, -1);
        this.entityData.define(DATA_IS_IGNITED, false);
        this.entityData.define(DATA_IS_ATTACKING, false);
        this.entityData.define(DATA_IS_SHEARED, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Creeper.createAttributes().add(ForgeMod.REACH_DISTANCE.get(), 0);
    }
    //endregion

    //region Nbt
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.entityData.get(DATA_IS_POWERED)) compound.putBoolean("Powered", true);
        compound.putShort("Fuse", (short) maxSwell);
        compound.putByte("ExplosionRadius", (byte) explosionRadius);
        if (this.entityData.get(DATA_IS_IGNITED)) compound.putBoolean("ignited", true);
        if (this.entityData.get(DATA_IS_SHEARED)) compound.putBoolean("Sheared", true);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(DATA_IS_POWERED, compound.getBoolean("Powered"));
        if (compound.contains("Fuse", Tag.TAG_ANY_NUMERIC)) maxSwell = compound.getShort("Fuse");
        if (compound.contains("ExplosionRadius", Tag.TAG_ANY_NUMERIC)) explosionRadius = compound.getByte("ExplosionRadius");
        this.entityData.set(DATA_IS_IGNITED, compound.getBoolean("ignited"));
        this.entityData.set(DATA_IS_SHEARED, compound.getBoolean("Sheared"));
    }
    //endregion

    //region interactions
    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.type.melee() == 0 && itemstack.is(Items.FLINT_AND_STEEL)) {
            this.level.playSound(player, this.blockPosition(), SoundEvents.FLINTANDSTEEL_USE, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.level.isClientSide) {
                this.entityData.set(DATA_IS_IGNITED, true);
                itemstack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        if (this.type.shearable() && !isSheared() && itemstack.is(Items.SHEARS)) {
            this.level.playSound(player, this.blockPosition(), SoundEvents.SNOW_GOLEM_SHEAR, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.level.isClientSide) {
                this.entityData.set(DATA_IS_SHEARED, true);
                itemstack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                this.spawnAtLocation(new ItemStack(ModItems.TINY_CACTUS.get()), 1.7F);
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    public boolean isSheared() {
        return this.entityData.get(DATA_IS_SHEARED);
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            if (this.getSwellDirection() > 0 && swell == 0) {
                this.playSound(SoundEvents.CREEPER_PRIMED, 1.0f, 0.5f);
                this.gameEvent(GameEvent.PRIME_FUSE);
            }

            this.updateSwell(Math.max(swell + this.getSwellDirection(), 0));

            if (swell >= maxSwell) {
                this.updateSwell(maxSwell);
                this.explode();
            }
        }
        super.tick();
    }

    @Override
    public void die(@NotNull DamageSource source) {
        super.die(source);
        if (!getCreeperType().potionsWhenDead().isEmpty()){
            //copy the effects
            summonCloudWithEffects(getCreeperType().potionsWhenDead().stream().map(MobEffectInstance::new).toList());
        }
    }

    public void explode() {
        if (!this.level.isClientSide) {
            Explosion.BlockInteraction interaction = ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
            this.dead = true;
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 3f * (this.isPowered() ? 2.0F : 1.0F), interaction);
            this.discard();

            Collection<MobEffectInstance> collection = this.getActiveEffects().stream().map(MobEffectInstance::new).toList();
            summonCloudWithEffects(collection);
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

    @Override
    public void thunderHit(@NotNull ServerLevel level, @NotNull LightningBolt bolt) {
        super.thunderHit(level, bolt);
        this.entityData.set(DATA_IS_POWERED, true);
    }

    @Override
    public double getAttributeValue(@NotNull Attribute attribute) {
        double val = super.getAttributeValue(attribute);
        return attribute.equals(Attributes.ATTACK_DAMAGE) && isPowered() ? val * 10 : val;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        return this.type.melee() == 0 || super.doHurtTarget(entity);
    }

    public CreeperType getCreeperType() {
        return type;
    }

    @Override
    public float getSpeed() {
        if (!isPowered()) return super.getSpeed();
        return super.getSpeed() * 1.5f;
    }

    public boolean isAfraidOf(Entity entity) {
        return type.entitiesAfraidOf().contains(entity.getType());
    }

    public void setAttack(boolean attack) {
        this.entityData.set(DATA_IS_ATTACKING, attack);
    }

    //endregion

    //region Accessors and mutators
    @Override
    public boolean isPowered() {
        return this.entityData.get(DATA_IS_POWERED);
    }

    public boolean activated() {
        return this.entityData.get(DATA_IS_IGNITED);
    }

    public int getSwellDirection() {
        return this.entityData.get(DATA_SWELL_DIRECTION);
    }

    public void changeSwellDirection(int direction) {
        this.entityData.set(DATA_SWELL_DIRECTION, direction);
    }

    public void updateSwell(int amount) {
        this.oldSwell = swell;
        this.swell = amount;
    }

    public float getSwelling(float partialTicks) {
        return Mth.lerp(partialTicks, (float)oldSwell, (float)swell) / (float)(maxSwell - 2);
    }

    public boolean canSwell() {
        return this.type.melee() == 0;
    }

    public boolean canDropMobsSkull() {
        return this.isPowered() && this.droppedSkulls < 1;
    }

    public void increaseDroppedSkulls() {
        ++this.droppedSkulls;
    }
    //endregion

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
