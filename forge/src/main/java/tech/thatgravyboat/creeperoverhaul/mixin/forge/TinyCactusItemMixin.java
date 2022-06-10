package tech.thatgravyboat.creeperoverhaul.mixin.forge;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import tech.thatgravyboat.creeperoverhaul.common.item.TinyCactusItem;

@Mixin(TinyCactusItem.class)
public class TinyCactusItemMixin extends Item {

    public TinyCactusItemMixin(Properties arg) {
        super(arg);
    }

    @Override
    public @Nullable EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }
}
