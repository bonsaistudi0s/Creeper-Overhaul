package tech.thatgravyboat.creeperoverhaul.common.block;

import net.minecraft.block.*;
import net.minecraft.item.Wearable;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;

public class TinyCactusBlock extends PlantBlock implements Wearable {

    private static final VoxelShape SHAPE = Block.createCuboidShape(6, 0, 6, 10, 4, 10);

    public TinyCactusBlock() {
        super(Settings.of(Material.CACTUS).strength(0.4F).sounds(BlockSoundGroup.WOOL).dynamicBounds());
    }

    @Override
    public @NotNull OffsetType getOffsetType() {
        return OffsetType.XZ;
    }


    @Override
    public @NotNull VoxelShape getOutlineShape(BlockState state, @NotNull BlockView level, @NotNull BlockPos pos, @NotNull ShapeContext ctx) {
        Vec3d vec3 = state.getModelOffset(level, pos);
        return SHAPE.offset(vec3.x, vec3.y, vec3.z);
    }

    @Override
    protected boolean canPlantOnTop(BlockState pState, @NotNull BlockView level, @NotNull BlockPos pos) {
        return pState.isOf(Blocks.CACTUS) || pState.isOf(Blocks.SAND) || pState.isOf(Blocks.RED_SAND) || pState.isOf(Blocks.TERRACOTTA) || pState.isOf(Blocks.WHITE_TERRACOTTA) || pState.isOf(Blocks.ORANGE_TERRACOTTA) || pState.isOf(Blocks.MAGENTA_TERRACOTTA) || pState.isOf(Blocks.LIGHT_BLUE_TERRACOTTA) || pState.isOf(Blocks.YELLOW_TERRACOTTA) || pState.isOf(Blocks.LIME_TERRACOTTA) || pState.isOf(Blocks.PINK_TERRACOTTA) || pState.isOf(Blocks.GRAY_TERRACOTTA) || pState.isOf(Blocks.LIGHT_GRAY_TERRACOTTA) || pState.isOf(Blocks.CYAN_TERRACOTTA) || pState.isOf(Blocks.PURPLE_TERRACOTTA) || pState.isOf(Blocks.BLUE_TERRACOTTA) || pState.isOf(Blocks.BROWN_TERRACOTTA) || pState.isOf(Blocks.GREEN_TERRACOTTA) || pState.isOf(Blocks.RED_TERRACOTTA) || pState.isOf(Blocks.BLACK_TERRACOTTA) || pState.isIn(BlockTags.DIRT);
    }


}
