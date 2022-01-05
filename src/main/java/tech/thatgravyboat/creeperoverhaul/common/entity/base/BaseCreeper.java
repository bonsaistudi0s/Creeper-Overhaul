package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
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
import tech.thatgravyboat.creeperoverhaul.common.registry.ModAttributes;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModItems;

import java.util.Collection;

public class BaseCreeper extends HostileEntity implements SkinOverlayOwner, IAnimatable {

    private static final TrackedData<Integer> DATA_SWELL_DIRECTION = DataTracker.registerData(BaseCreeper.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> DATA_IS_POWERED = DataTracker.registerData(BaseCreeper.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> DATA_IS_IGNITED = DataTracker.registerData(BaseCreeper.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> DATA_IS_ATTACKING = DataTracker.registerData(BaseCreeper.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> DATA_IS_SHEARED = DataTracker.registerData(BaseCreeper.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int explosionRadius = 3;
    private int maxSwell = 30;
    private int swell;
    private int oldSwell;
    private int droppedSkulls;

    private final AnimationFactory factory = new AnimationFactory(this);

    private final CreeperType type;

    public BaseCreeper(EntityType<? extends BaseCreeper> entityType, World level, CreeperType type) {
        super(entityType, level);
        this.type = type;
        if (!level.isClient) this.type.entities().forEach(e ->
                this.targetSelector.add(1, new ActiveTargetGoal<>(this, e, true)));
    }

    //region Goals And Data
    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new CreeperSwellGoal(this));
        this.goalSelector.add(3, new CreeperAvoidEntitiesGoal(this,6.0F, 1.0D, 1.2D));
        this.goalSelector.add(4, new CreeperMeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        registerNearestAttackableTargetGoal();
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    protected void registerNearestAttackableTargetGoal() {
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DATA_IS_POWERED, false);
        this.dataTracker.startTracking(DATA_SWELL_DIRECTION, -1);
        this.dataTracker.startTracking(DATA_IS_IGNITED, false);
        this.dataTracker.startTracking(DATA_IS_ATTACKING, false);
        this.dataTracker.startTracking(DATA_IS_SHEARED, false);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return CreeperEntity.createCreeperAttributes().add(ModAttributes.REACH_DISTANCE, 0).add(ModAttributes.SWIM_SPEED);
    }
    //endregion

    //region Nbt
    @Override
    public void writeCustomDataToNbt(@NotNull NbtCompound compound) {
        super.writeCustomDataToNbt(compound);
        if (this.dataTracker.get(DATA_IS_POWERED)) compound.putBoolean("Powered", true);
        compound.putShort("Fuse", (short) maxSwell);
        compound.putByte("ExplosionRadius", (byte) explosionRadius);
        if (this.dataTracker.get(DATA_IS_IGNITED)) compound.putBoolean("ignited", true);
        if (this.dataTracker.get(DATA_IS_SHEARED)) compound.putBoolean("Sheared", true);
    }

    @Override
    public void readCustomDataFromNbt(@NotNull NbtCompound compound) {
        super.readCustomDataFromNbt(compound);
        this.dataTracker.set(DATA_IS_POWERED, compound.getBoolean("Powered"));
        if (compound.contains("Fuse", NbtElement.NUMBER_TYPE)) maxSwell = compound.getShort("Fuse");
        if (compound.contains("ExplosionRadius", NbtElement.NUMBER_TYPE)) explosionRadius = compound.getByte("ExplosionRadius");
        this.dataTracker.set(DATA_IS_IGNITED, compound.getBoolean("ignited"));
        this.dataTracker.set(DATA_IS_SHEARED, compound.getBoolean("Sheared"));
    }
    //endregion

    //region interactions

    //region Fabric Specific Fixes
    @Override
    protected void knockDownwards() {
        this.setVelocity(this.getVelocity().add(0.0D, -0.04D * this.getAttributeValue(ModAttributes.SWIM_SPEED), 0.0D));
    }

    @Override
    protected void swimUpward(Tag<Fluid> fluid) {
        this.setVelocity(this.getVelocity().add(0.0D, 0.04D * this.getAttributeValue(ModAttributes.SWIM_SPEED), 0.0D));
    }

    @Override
    public void updateVelocity(float speed, Vec3d movementInput) {
        FluidState fluidState = this.world.getFluidState(this.getBlockPos());
        if (this.isTouchingWater() && this.shouldSwimInFluids() && !this.canWalkOnFluid(fluidState.getFluid())){
            super.updateVelocity(speed * (float)this.getAttributeValue(ModAttributes.SWIM_SPEED), movementInput);
            return;
        }
        super.updateVelocity(speed, movementInput);
    }
    //endregion

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (this.type.melee() == 0 && itemStack.isOf(Items.FLINT_AND_STEEL)) {
            this.world.playSound(player, this.getBlockPos(), SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.world.isClient) {
                this.dataTracker.set(DATA_IS_IGNITED, true);
                itemStack.damage(1, player, (p) -> p.sendToolBreakStatus(hand));
            }

            return ActionResult.success(this.world.isClient);
        }
        if (this.type.shearable() && !isSheared() && itemStack.isOf(Items.SHEARS)) {
            this.world.playSound(player, this.getBlockPos(), SoundEvents.ENTITY_SNOW_GOLEM_SHEAR, this.getSoundCategory(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.world.isClient) {
                this.dataTracker.set(DATA_IS_SHEARED, true);
                itemStack.damage(1, player, (p) -> p.sendToolBreakStatus(hand));
                this.dropStack(new ItemStack(ModItems.TINY_CACTUS), 1.7F);
            }

            return ActionResult.success(this.world.isClient);
        }
        return super.interactMob(player, hand);
    }


    public boolean isSheared() {
        return this.dataTracker.get(DATA_IS_SHEARED);
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            if (this.getSwellDirection() > 0 && swell == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0f, 0.5f);
                this.emitGameEvent(GameEvent.PRIME_FUSE);
            }

            this.updateSwell(Math.max(swell + this.getSwellDirection(), 0));

            if (swell >= maxSwell) {
                this.updateSwell(maxSwell);
                this.explode();
            }
        }
        super.tick();
    }

    public void explode() {
        if (!this.world.isClient()) {
            Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
            this.dead = true;
            Explosion explosion = this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), (float)explosionRadius * (this.isPowered() ? 2.0F : 1.0F), destructionType);
            this.discard();
            explosion.getAffectedPlayers().keySet().forEach(player -> {
                Collection<StatusEffectInstance> inflictingPotions = this.type.inflictingPotions().stream().map(StatusEffectInstance::new).toList();
                inflictingPotions.forEach(player::addStatusEffect);
            });
            Collection<StatusEffectInstance> collection = this.getStatusEffects().stream().map(StatusEffectInstance::new).toList();
            summonCloudWithEffects(collection);
            summonCloudWithEffects(getCreeperType().potionsWhenDead().stream().map(StatusEffectInstance::new).toList());
        }
    }

    private void summonCloudWithEffects(Collection<StatusEffectInstance> effects) {
        if (!effects.isEmpty()) {
            AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
            cloud.setRadius(2.5F);
            cloud.setRadiusOnUse(-0.5F);
            cloud.setWaitTime(10);
            cloud.setDuration(cloud.getDuration() / 2);
            cloud.setRadiusOnUse(cloud.getRadius() / cloud.getDuration());
            effects.forEach(cloud::addEffect);

            this.world.spawnEntity(cloud);
        }
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        super.onStruckByLightning(world, lightning);
        this.dataTracker.set(DATA_IS_POWERED, true);
    }

    @Override
    public double getAttributeValue(EntityAttribute attribute) {
        double val = super.getAttributeValue(attribute);
        return attribute.equals(EntityAttributes.GENERIC_ATTACK_DAMAGE) && isPowered() ? val * 10 : val;
    }

    @Override
    public boolean tryAttack(Entity target) {
        return this.type.melee() == 0 || super.tryAttack(target);
    }

    public CreeperType getCreeperType() {
        return type;
    }

    @Override
    public float getMovementSpeed() {
        if (!isPowered()) return super.getMovementSpeed();
        return super.getMovementSpeed() * 1.5F;
    }

    public boolean isAfraidOf(Entity entity) {
        return type.entitiesAfraidOf().contains(entity.getType());
    }

    public void setAttack(boolean attack) {
        this.dataTracker.set(DATA_IS_ATTACKING, attack);
    }

    //endregion

    //region Accessors and mutators


    @Override
    public boolean shouldRenderOverlay() {
        return this.dataTracker.get(DATA_IS_POWERED);
    }

    public boolean isPowered() {
        return this.dataTracker.get(DATA_IS_POWERED);
    }

    public boolean activated() {
        return this.dataTracker.get(DATA_IS_IGNITED);
    }

    public int getSwellDirection() {
        return this.dataTracker.get(DATA_SWELL_DIRECTION);
    }

    public void changeSwellDirection(int direction) {
        this.dataTracker.set(DATA_SWELL_DIRECTION, direction);
    }

    public void updateSwell(int amount) {
        this.oldSwell = swell;
        this.swell = amount;
    }

    public float getSwelling(float partialTicks) {
        return MathHelper.lerp(partialTicks, (float)oldSwell, (float)swell) / (float)(maxSwell - 2);
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
        if (dataTracker.get(DATA_IS_ATTACKING)) {
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
