package tech.thatgravyboat.creeperoverhaul.forge;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    public static final ForgeConfigSpec.BooleanValue DESTROY_BLOCKS;

    public static final ForgeConfigSpec COMMON_CONFIG;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("General Options");
        DESTROY_BLOCKS = builder.comment("\nChange the Creeper Overhaul creepers to destroy blocks or not.")
                .define("destroyBlocks", true);
        builder.pop();

        COMMON_CONFIG = builder.build();
    }
}
