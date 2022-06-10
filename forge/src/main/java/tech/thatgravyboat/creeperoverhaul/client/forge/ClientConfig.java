package tech.thatgravyboat.creeperoverhaul.client.forge;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public static final ForgeConfigSpec.BooleanValue REPLACE_DEFAULT_CREEPER;

    public static final ForgeConfigSpec CLIENT_CONFIG;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("General Options");
        REPLACE_DEFAULT_CREEPER = builder.comment("\nChange the Vanilla Creeper to a new and improved texture with better animations.")
                .define("replaceDefaultCreeper", true);
        builder.pop();

        CLIENT_CONFIG = builder.build();
    }
}
