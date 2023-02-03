package tech.thatgravyboat.creeperoverhaul.common.utils.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolAction;
import tech.thatgravyboat.creeperoverhaul.client.forge.ClientConfig;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.forge.CommonConfig;

public class PlatformUtilsImpl {

    private static final ToolAction SHEARS_ENTITY_USE = ToolAction.get("shears_entity_use");
    private static final ToolAction IGNITE = ToolAction.get("ignite");

    private static Boolean usingOptifine = null;

    public static boolean shouldHidePowerLayer() {
        if (usingOptifine == null) {
            try {
                Class.forName("optifine.Installer");
                usingOptifine = true;
            }catch (Exception ignored) {
                usingOptifine = false;
            }
        }
        return usingOptifine;
    }

    public static Level.ExplosionInteraction getInteractionForCreeper(BaseCreeper creeper) {
        return CommonConfig.DESTROY_BLOCKS.get().equals(Boolean.TRUE) ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE;
    }

    public static String formatShaderId(ResourceLocation location) {
        return location.toString();
    }

    public static boolean isShears(ItemStack stack) {
        return stack.getItem() instanceof ShearsItem || stack.canPerformAction(SHEARS_ENTITY_USE);
    }

    public static boolean isFlintAndSteel(ItemStack stack) {
        return stack.getItem() instanceof FlintAndSteelItem || stack.canPerformAction(IGNITE);
    }


    public static boolean isVanillaReplaced() {
        return Boolean.TRUE.equals(ClientConfig.REPLACE_DEFAULT_CREEPER.get());
    }

    public static Attribute getModAttribute(String name) {
        return switch (name) {
            case "swim_speed" -> ForgeMod.SWIM_SPEED.get();
            case "reach_distance" -> ForgeMod.REACH_DISTANCE.get();
            default -> null;
        };
    }
}
