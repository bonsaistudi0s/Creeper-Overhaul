package tech.thatgravyboat.creeperoverhaul.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;

public class TinyCactusBlock extends BushBlock implements Wearable {

    private static final VoxelShape SHAPE = Block.box(6, 0, 6, 10, 4, 10);

    public TinyCactusBlock() {
        super(Properties.of(Material.CACTUS).strength(0.4F).sound(SoundType.WOOL).dynamicShape());
    }

    @Override
    public BlockBehaviour.@NotNull OffsetType getOffsetType() {
        return BlockBehaviour.OffsetType.XZ;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        Vec3 vec3 = state.getOffset(level, pos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.DESERT;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return pState.is(Blocks.CACTUS) || pState.is(Blocks.SAND) || pState.is(Blocks.RED_SAND) || pState.is(Blocks.TERRACOTTA) || pState.is(Blocks.WHITE_TERRACOTTA) || pState.is(Blocks.ORANGE_TERRACOTTA) || pState.is(Blocks.MAGENTA_TERRACOTTA) || pState.is(Blocks.LIGHT_BLUE_TERRACOTTA) || pState.is(Blocks.YELLOW_TERRACOTTA) || pState.is(Blocks.LIME_TERRACOTTA) || pState.is(Blocks.PINK_TERRACOTTA) || pState.is(Blocks.GRAY_TERRACOTTA) || pState.is(Blocks.LIGHT_GRAY_TERRACOTTA) || pState.is(Blocks.CYAN_TERRACOTTA) || pState.is(Blocks.PURPLE_TERRACOTTA) || pState.is(Blocks.BLUE_TERRACOTTA) || pState.is(Blocks.BROWN_TERRACOTTA) || pState.is(Blocks.GREEN_TERRACOTTA) || pState.is(Blocks.RED_TERRACOTTA) || pState.is(Blocks.BLACK_TERRACOTTA) || pState.is(BlockTags.DIRT);
    }


}
