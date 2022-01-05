package tech.thatgravyboat.creeperoverhaul.common.entity.goals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

import java.util.EnumSet;
import java.util.List;

public class CreeperAvoidEntitiesGoal extends Goal {

    private final BaseCreeper creeper;
    private final float maxDistance;
    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;

    @Nullable
    private Entity entityToAvoid;
    @Nullable
    private Path path;

    public CreeperAvoidEntitiesGoal(BaseCreeper creeper, float maxDistance, double walkSpeedModifier, double sprintSpeedModifier) {
        this.creeper = creeper;
        this.maxDistance = maxDistance;
        this.walkSpeedModifier = walkSpeedModifier;
        this.sprintSpeedModifier = sprintSpeedModifier;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        List<Entity> afraidEntities = this.creeper.world.getOtherEntities(this.creeper, this.creeper.getBoundingBox().expand(this.maxDistance, 3D, this.maxDistance), this.creeper::isAfraidOf);
        this.entityToAvoid = getNearestEntity(afraidEntities, this.creeper);
        if (this.entityToAvoid == null) return false;
        Vec3d posAway = NoPenaltyTargeting.findFrom(this.creeper, 16, 7, this.entityToAvoid.getPos());
        if (posAway == null) return false;
        if (this.entityToAvoid.squaredDistanceTo(posAway) < this.entityToAvoid.squaredDistanceTo(this.creeper)) return false;
        this.path = this.creeper.getNavigation().findPathTo(posAway.x, posAway.y, posAway.z, 0);
        return this.path != null;
    }

    @Override
    public boolean shouldContinue() {
        return !this.creeper.getNavigation().isIdle();
    }

    @Override
    public void start() {
        this.creeper.getNavigation().startMovingAlong(this.path, this.walkSpeedModifier);
    }

    @Override
    public void stop() {
        this.entityToAvoid = null;
    }

    @Override
    public void tick() {
        if (this.entityToAvoid != null) {
            this.creeper.getNavigation().setSpeed(this.creeper.squaredDistanceTo(this.entityToAvoid) < 49D ? this.sprintSpeedModifier : this.walkSpeedModifier);
        }
    }

    @Nullable
    private Entity getNearestEntity(List<Entity> entities, Entity self){
        if (entities.isEmpty()) return null;
        double lastDist = -1.0D;
        Entity lastEntity = null;
        for (Entity entity : entities) {
            double dist = entity.squaredDistanceTo(self.getX(), self.getY(), self.getZ());
            if (lastDist == -1.0D || dist < lastDist) {
                lastEntity = entity;
                lastDist = dist;
            }
        }
        return lastEntity;
    }
}
