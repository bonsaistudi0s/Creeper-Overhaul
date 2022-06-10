package tech.thatgravyboat.creeperoverhaul.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import tech.thatgravyboat.creeperoverhaul.common.block.TinyCactusBlock;

import java.util.function.Supplier;

public class ModBlocks {

    public static final Supplier<Block> TINY_CACTUS = registerBlock("tiny_cactus", TinyCactusBlock::new);
    public static final Supplier<Block> POTTED_TINY_CACTUS = registerBlock("potted_tiny_cactus", () -> new FlowerPotBlock(TINY_CACTUS.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_CACTUS)));

    public static void init() {
        //Init class
    }

    @ExpectPlatform
    public static <T extends Block> Supplier<T> registerBlock(String id, Supplier<T> block) {
        throw new AssertionError();
    }
}
