package tech.thatgravyboat.creeperoverhaul.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ActiveTargetGoal.class)
public interface ActiveTargetGoalAccessor {

    @Accessor
    Class<? extends LivingEntity> getTargetClass();

    @Accessor
    void setTargetPredicate(TargetPredicate predicate);
}
