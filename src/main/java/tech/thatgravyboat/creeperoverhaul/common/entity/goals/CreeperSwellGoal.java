package tech.thatgravyboat.creeperoverhaul.common.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

import java.util.EnumSet;

public class CreeperSwellGoal extends Goal {

    private LivingEntity target;
    private final BaseCreeper creeper;

    public CreeperSwellGoal(BaseCreeper creeper) {
        this.creeper = creeper;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        LivingEntity livingTarget = creeper.getTarget();
        return creeper.canSwell() && (creeper.activated() || creeper.getSwellDirection() > 0 || (livingTarget != null && creeper.distanceToSqr(livingTarget) < 9.0D));
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
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        this.creeper.changeSwellDirection(updateState());
    }

    private int updateState() {
        if (creeper.activated()) return 1;
        if (target == null) return -1;
        if (creeper.distanceToSqr(target) > 49.0D) return -1;
        if (!this.creeper.getSensing().hasLineOfSight(this.target)) return -1;
        return 1;
    }
}
