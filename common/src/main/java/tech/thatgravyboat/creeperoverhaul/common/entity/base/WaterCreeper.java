package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import tech.thatgravyboat.creeperoverhaul.common.entity.goals.WaterCreeperMoveControl;

public class WaterCreeper extends BaseCreeper {

    public WaterCreeper(EntityType<? extends Creeper> entityType, Level level, CreeperType type) {
        super(entityType, level, type);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new WaterCreeperMoveControl(this);
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader levelReader) {
        return levelReader.isUnobstructed(this);
    }

    @Override
    protected void registerMovementGoals() {
        this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1.0, 40));
    }

    @Override
    protected PathNavigation createNavigation(@NotNull Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    @Override
    public MobType getMobType() {
        return MobType.WATER;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void baseTick() {
        int i = this.getAirSupply();
        super.baseTick();
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(i - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(DamageSource.DROWN, 2.0F);
            }
        } else {
            this.setAirSupply(300);
        }
    }

    @Override
    public void travel(@NotNull Vec3 vec3) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.01F, vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.9, 0.5, 0.9));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(vec3);
        }
    }

    @Override
    public void aiStep() {
        if (!this.isInWater() && this.onGround && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
            this.onGround = false;
            this.hasImpulse = true;
            this.playSound(type.getFlopSound(this).orElse(SoundEvents.COD_FLOP), this.getSoundVolume(), this.getVoicePitch());
        }

        super.aiStep();
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected <E extends IAnimatable> PlayState action(AnimationEvent<E> event) {
        Animation animation = event.getController().getCurrentAnimation();
        if (isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creeper.attack", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            return PlayState.CONTINUE;
        } else if (animation != null && animation.animationName.equals("animation.creeper.attack") && event.getController().getAnimationState().equals(AnimationState.Running)) {
            return PlayState.CONTINUE;
        } else if (!isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creeper.flop", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        } else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.creeper.swim", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }
}
