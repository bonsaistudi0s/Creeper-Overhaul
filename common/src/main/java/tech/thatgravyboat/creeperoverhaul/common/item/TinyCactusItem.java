package tech.thatgravyboat.creeperoverhaul.common.item;

import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class TinyCactusItem extends BlockItem {

    public TinyCactusItem(Block block, Properties properties) {
        super(block, properties);
    }

    @PlatformOnly("forge")
    public @Nullable EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }
}
