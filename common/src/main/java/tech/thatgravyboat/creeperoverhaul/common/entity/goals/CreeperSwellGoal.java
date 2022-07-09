package tech.thatgravyboat.creeperoverhaul.common.entity.goals;

import net.minecraft.world.entity.ai.goal.SwellGoal;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

public class CreeperSwellGoal extends SwellGoal {

    private final BaseCreeper baseCreeper;

    public CreeperSwellGoal(BaseCreeper creeper) {
        super(creeper);
        this.baseCreeper = creeper;
    }

    @Override
    public boolean canUse() {
        return baseCreeper.canSwell() && (baseCreeper.isIgnited() || super.canUse());
    }
}
