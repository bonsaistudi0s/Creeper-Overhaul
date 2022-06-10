package tech.thatgravyboat.creeperoverhaul.common.registry.fabric;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import tech.thatgravyboat.creeperoverhaul.Creepers;

import java.util.function.Supplier;

public class ModItemsImpl {

    public static <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        var output = Registry.register(Registry.ITEM, new ResourceLocation(Creepers.MODID, id), item.get());
        return () -> output;
    }

    public static <E extends Mob, T extends EntityType<E>> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<T> entity, int primary, int second, Item.Properties properties) {
        return registerItem(id, () -> new SpawnEggItem(entity.get(), primary, second, properties));
    }
}
