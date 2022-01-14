package tech.thatgravyboat.creeperoverhaul.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

@Mixin(IronGolem.class)
public abstract class IronGolemMixin extends Mob {

    protected IronGolemMixin(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void onRegisterGoals(CallbackInfo ci) {
        for (WrappedGoal availableGoal : this.targetSelector.getAvailableGoals()) {
            if (availableGoal.getGoal() instanceof NearestAttackableTargetGoalAccessor accessor) {
                if (accessor.getTargetType().isAssignableFrom(Mob.class)){
                    double followDistance = this.getAttributeValue(Attributes.FOLLOW_RANGE);
                    accessor.setTargetConditions(TargetingConditions.forCombat()
                            .range(followDistance)
                            .selector(entity -> entity instanceof Enemy && !(entity instanceof Creeper) && !(entity instanceof BaseCreeper)));
                }
            }
        }
    }
}
