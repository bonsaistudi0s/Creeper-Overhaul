package tech.thatgravyboat.creeperoverhaul.common.utils.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.creeperoverhaul.client.forge.ClientConfig;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.forge.CommonConfig;

import java.util.function.Supplier;

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

    public static CreativeModeTab createTab(ResourceLocation loc, Supplier<ItemStack> icon) {
        return new CreativeModeTab(loc.getNamespace() + "." + loc.getPath()) {
            @Override
            public @NotNull ItemStack makeIcon() {
                return icon.get();
            }
        };
    }

    public static Explosion.BlockInteraction getInteractionForCreeper(BaseCreeper creeper) {
        boolean destroyBlocks = ForgeEventFactory.getMobGriefingEvent(creeper.level, creeper) && CommonConfig.DESTROY_BLOCKS.get().equals(Boolean.TRUE);
        return destroyBlocks ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
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
