package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class NeutralCreeper extends BaseCreeper implements Angerable {

    private static final TrackedData<Integer> DATA_REMAINING_ANGER_TIME = DataTracker.registerData(NeutralCreeper.class, TrackedDataHandlerRegistry.INTEGER);
    private static final UniformIntProvider PERSISTENT_ANGER_TIME = TimeHelper.betweenSeconds(20, 39);

    @Nullable
    private UUID persistentTarget;

    public NeutralCreeper(EntityType<? extends NeutralCreeper> entityType, World level, CreeperType type) {
        super(entityType, level, type);
    }

    @Override
    public void writeCustomDataToNbt(@NotNull NbtCompound compound) {
        super.writeCustomDataToNbt(compound);
        writeAngerToNbt(compound);
    }

    @Override
    public void readCustomDataFromNbt(@NotNull NbtCompound compound) {
        super.readCustomDataFromNbt(compound);
        readAngerFromNbt(this.world, compound);
    }

    @Override
    protected void mobTick() {
        if (this.world instanceof ServerWorld serverLevel) {
            this.tickAngerLogic(serverLevel, false);
        }
    }

    @Override
    protected void registerNearestAttackableTargetGoal() {
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true, this::shouldAngerAt));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DATA_REMAINING_ANGER_TIME, 0);
    }

    @Override
    public boolean canSwell() {
        return super.canSwell() && (this.hasAngerTime() || activated());
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setAngerTime(int time) {
        this.dataTracker.set(DATA_REMAINING_ANGER_TIME, time);
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return persistentTarget;
    }

    @Override
    public void setAngryAt(@Nullable UUID target) {
        persistentTarget = target;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(PERSISTENT_ANGER_TIME.get(this.random));
    }

    @Override
    public boolean isAngryAt(PlayerEntity player) {
        return this.shouldAngerAt(player);
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
    }
}
