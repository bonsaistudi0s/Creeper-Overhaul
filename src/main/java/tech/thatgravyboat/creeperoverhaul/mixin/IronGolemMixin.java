package tech.thatgravyboat.creeperoverhaul.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

@Mixin(IronGolemEntity.class)
public abstract class IronGolemMixin extends MobEntity {

    protected IronGolemMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    private void onRegisterGoals(CallbackInfo ci) {
        for (PrioritizedGoal goal : this.targetSelector.getGoals()) {
            if (goal.getGoal() instanceof ActiveTargetGoalAccessor accessor) {
                if (accessor.getTargetClass().isAssignableFrom(MobEntity.class)){
                    double followDistance = this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
                    accessor.setTargetPredicate(TargetPredicate.createAttackable().setBaseMaxDistance(followDistance)
                            .setPredicate(entity -> entity instanceof Monster && !(entity instanceof CreeperEntity) && !(entity instanceof BaseCreeper)));
                }
            }
        }
    }
}
