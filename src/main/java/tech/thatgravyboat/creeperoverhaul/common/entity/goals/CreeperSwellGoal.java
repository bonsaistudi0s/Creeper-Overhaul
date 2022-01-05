package tech.thatgravyboat.creeperoverhaul.common.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.Angerable;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

import java.util.EnumSet;

public class CreeperSwellGoal extends Goal {

    private LivingEntity target;
    private final BaseCreeper creeper;

    public CreeperSwellGoal(BaseCreeper creeper) {
        this.creeper = creeper;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        LivingEntity livingTarget = creeper.getTarget();
        if (creeper instanceof Angerable angerable) livingTarget = angerable.getTarget();
        return creeper.canSwell() && (creeper.activated() || creeper.getSwellDirection() > 0 || (livingTarget != null && creeper.squaredDistanceTo(livingTarget) < 9.0D));
    }

    @Override
    public void start() {
        this.creeper.getNavigation().stop();
        this.target = this.creeper.getTarget();
    }

    @Override
    public void stop() {
        this.target = null;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        this.creeper.changeSwellDirection(updateState());
    }

    private int updateState() {
        if (creeper.activated()) return 1;
        if (target == null) return -1;
        if (creeper.squaredDistanceTo(target) > 49.0D) return -1;
        if (!this.creeper.getVisibilityCache().canSee(this.target)) return -1;
        return 1;
    }
}
