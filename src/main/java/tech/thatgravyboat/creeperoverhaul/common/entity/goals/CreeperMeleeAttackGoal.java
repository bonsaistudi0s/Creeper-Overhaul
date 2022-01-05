package tech.thatgravyboat.creeperoverhaul.common.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModAttributes;


public class CreeperMeleeAttackGoal extends MeleeAttackGoal {

    private final BaseCreeper creeper;

    public CreeperMeleeAttackGoal(BaseCreeper creeper, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(creeper, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.creeper = creeper;
    }

    @Override
    protected void attack(@NotNull LivingEntity enemy, double pDistToEnemySqr) {
        double d0 = this.getSquaredMaxAttackDistance(enemy);
        if (pDistToEnemySqr <= d0 && this.isCooledDown()) {
            this.resetCooldown();
            this.mob.tryAttack(enemy);
        } else if (pDistToEnemySqr <= d0) {
            creeper.setAttack(this.getCooldown() <= creeper.getCreeperType().melee());
        } else {
            this.resetCooldown();
            creeper.setAttack(false);
        }
    }

    @Override
    protected int getTickCount(int p_186072_) {
        return super.getTickCount(p_186072_) + creeper.getCreeperType().melee() - 4;
    }

    public void stop() {
        creeper.setAttack(false);
        super.stop();
    }

    @Override
    protected double getSquaredMaxAttackDistance(@NotNull LivingEntity target) {
        EntityAttributeInstance attribute = this.creeper.getAttributeInstance(ModAttributes.REACH_DISTANCE);
        return super.getSquaredMaxAttackDistance(target) + (attribute != null ? attribute.getValue() : 0);
    }
}
