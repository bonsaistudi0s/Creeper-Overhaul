package tech.thatgravyboat.creeperoverhaul.client;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public static ForgeConfigSpec.BooleanValue REPLACE_DEFAULT_CREEPER;

    public static final ForgeConfigSpec CLIENT_CONFIG;

    static {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        CLIENT_BUILDER.push("General Options");
        REPLACE_DEFAULT_CREEPER = CLIENT_BUILDER.comment("\nChange the Vanilla Creeper to a new and improved texture with better animations.")
                .define("replaceDefaultCreeper", true);
        CLIENT_BUILDER.pop();

        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }
}
