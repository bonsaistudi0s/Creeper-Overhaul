package tech.thatgravyboat.creeperoverhaul.common.registry.forge;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tech.thatgravyboat.creeperoverhaul.Creepers;

import java.util.function.Supplier;

public class ModBlocksImpl {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Creepers.MODID);

    public static <T extends Block> Supplier<T> registerBlock(String id, Supplier<T> block) {
        return BLOCKS.register(id, block);
    }
}
