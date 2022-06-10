package tech.thatgravyboat.creeperoverhaul.common.registry.forge;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
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
}
