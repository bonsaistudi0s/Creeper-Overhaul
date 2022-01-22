package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class PassiveCreeper extends NeutralCreeper {

    public PassiveCreeper(EntityType<? extends NeutralCreeper> entityType, World level, CreeperType type) {
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
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        BlockState state = world.getBlockState(pos.down());
        boolean isGrassLike = state.isOf(Blocks.GRASS_BLOCK) || state.isOf(Blocks.PODZOL) || state.isOf(Blocks.MYCELIUM);
        return isGrassLike ? 10.0F : world.getBrightness(pos) - 0.5F;
    }
}
