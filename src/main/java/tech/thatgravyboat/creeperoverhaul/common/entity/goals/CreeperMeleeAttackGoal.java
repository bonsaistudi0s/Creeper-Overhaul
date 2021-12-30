package tech.thatgravyboat.creeperoverhaul.common.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.creeperoverhaul.common.entity.CreeperTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;


public class CreeperMeleeAttackGoal extends MeleeAttackGoal {

    private final BaseCreeper creeper;

    public CreeperMeleeAttackGoal(BaseCreeper creeper, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(creeper, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.creeper = creeper;
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity enemy, double pDistToEnemySqr) {
        double d0 = this.getAttackReachSqr(enemy);
        if (pDistToEnemySqr <= d0 && this.isTimeToAttack()) {
            this.resetAttackCooldown();
            this.mob.doHurtTarget(enemy);
        } else if (pDistToEnemySqr <= d0) {
            creeper.setAttack(this.getTicksUntilNextAttack() <= creeper.getCreeperType().melee());
        } else {
            this.resetAttackCooldown();
            creeper.setAttack(false);
        }
    }

    @Override
    protected int adjustedTickDelay(int p_186072_) {
        return super.adjustedTickDelay(p_186072_) + creeper.getCreeperType().melee() - 4;
    }

    public void stop() {
        creeper.setAttack(false);
        super.stop();
    }

    @Override
    protected double getAttackReachSqr(@NotNull LivingEntity target) {
        AttributeInstance attribute = this.creeper.getAttribute(ForgeMod.REACH_DISTANCE.get());
        return super.getAttackReachSqr(target) + (attribute != null ? attribute.getValue() : 0);
    }
}
