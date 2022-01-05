package tech.thatgravyboat.creeperoverhaul.common.registry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.block.TinyCactusBlock;

public class ModBlocks {



    public static final Block TINY_CACTUS = new TinyCactusBlock();

    public static final Block TINY_CACTUS_POT = new FlowerPotBlock(TINY_CACTUS, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());

    public static void register() {
        Registry.register(Registry.BLOCK, new Identifier(Creepers.MODID, "tiny_cactus"), TINY_CACTUS);
        Registry.register(Registry.BLOCK, new Identifier(Creepers.MODID, "potted_tiny_cactus"), TINY_CACTUS_POT);
    }

}


