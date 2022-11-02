package tech.thatgravyboat.creeperoverhaul.common.registry.forge;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.creeperoverhaul.Creepers;

import java.util.function.Supplier;

public class ModItemsImpl {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Creepers.MODID);

    public static <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return ITEMS.register(id, item);
    }

    public static <E extends Mob, T extends EntityType<E>> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<T> entity, int primary, int second, Item.Properties properties) {
        return registerItem(id, () -> new ForgeSpawnEggItem(entity, primary, second, properties));
    }

    public static BlockItem createCactus(Block block, Item.Properties properties) {
        return new BlockItem(block, properties) {
            public @NotNull EquipmentSlot getEquipmentSlot(ItemStack stack) {
                return EquipmentSlot.HEAD;
            }
        };
    }
}
