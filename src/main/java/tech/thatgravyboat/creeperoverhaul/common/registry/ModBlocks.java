package tech.thatgravyboat.creeperoverhaul.common.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.block.TinyCactusBlock;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Creepers.MODID);

    public static final RegistryObject<Block> TINY_CACTUS = BLOCKS.register("tiny_cactus", TinyCactusBlock::new);

    public static final RegistryObject<Block> TINY_CACTUS_POT = BLOCKS.register("potted_tiny_cactus", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, TINY_CACTUS, BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
}


