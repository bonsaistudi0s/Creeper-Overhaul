package tech.thatgravyboat.creeperoverhaul.common.registry.fabric;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import tech.thatgravyboat.creeperoverhaul.Creepers;

import java.util.function.Supplier;

public class ModEntitiesImpl {

    public static <E extends Entity, T extends EntityType<E>> Supplier<T> registerEntity(String id, Supplier<T> entity) {
        var output = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(Creepers.MODID, id), entity.get());
        return () -> output;
    }
}
