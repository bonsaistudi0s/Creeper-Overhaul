package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PassiveCreeper extends NeutralCreeper {

    public PassiveCreeper(EntityType<? extends NeutralCreeper> entityType, Level level, CreeperType type) {
        super(entityType, level, type);
    }

    @Override
    protected void registerNearestAttackableTargetGoal() {
        //We dont attack here!
    }

    @Override
    protected boolean shouldRevenge() {
        return false;
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        BlockState state = level.getBlockState(pos.below());
        boolean isGrassLike = state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.PODZOL) || state.is(Blocks.MYCELIUM);
        return isGrassLike ? 10f : level.getBrightness(pos) -0.5f;
    }
}
