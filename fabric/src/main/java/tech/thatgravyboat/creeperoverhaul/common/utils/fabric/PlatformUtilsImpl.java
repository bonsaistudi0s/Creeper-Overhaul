package tech.thatgravyboat.creeperoverhaul.common.utils.fabric;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import tech.thatgravyboat.creeperoverhaul.fabric.Config;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.registry.fabric.FabricAttributes;

import java.util.function.Supplier;

public class PlatformUtilsImpl {
    public static boolean shouldHidePowerLayer() {
        return false;
    }

    public static CreativeModeTab createTab(ResourceLocation loc, Supplier<ItemStack> icon) {
        return FabricItemGroupBuilder.build(loc, icon);
    }

    public static Explosion.BlockInteraction getInteractionForCreeper(BaseCreeper creeper) {
        boolean destroyBlocks = creeper.level.getGameRules().getRule(GameRules.RULE_MOBGRIEFING).get() && Config.destroyBlocks;
        return destroyBlocks ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
    }

    public static String formatShaderId(ResourceLocation location) {
        return location.getNamespace() + "_" + location.getPath();
    }

    public static boolean isShears(ItemStack stack) {
        return stack.getItem() instanceof ShearsItem;
    }

    public static boolean isFlintAndSteel(ItemStack stack) {
        return stack.getItem() instanceof FlintAndSteelItem;
    }

    public static boolean isVanillaReplaced() {
        return Config.replaceDefaultCreeper;
    }

    public static Attribute getModAttribute(String name) {
        return switch (name) {
            case "swim_speed" -> FabricAttributes.SWIM_SPEED;
            case "reach_distance" -> FabricAttributes.REACH_DISTANCE;
            default -> null;
        };
    }
}
