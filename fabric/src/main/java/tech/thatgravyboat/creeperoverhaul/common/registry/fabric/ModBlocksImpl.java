package tech.thatgravyboat.creeperoverhaul.common.registry.fabric;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import tech.thatgravyboat.creeperoverhaul.Creepers;

import java.util.function.Supplier;

public class ModBlocksImpl {
    public static <T extends Block> Supplier<T> registerBlock(String id, Supplier<T> block) {
        var output = Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(Creepers.MODID, id), block.get());
        return () -> output;
    }
}
