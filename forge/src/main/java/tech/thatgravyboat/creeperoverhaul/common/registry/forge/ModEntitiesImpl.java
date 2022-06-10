package tech.thatgravyboat.creeperoverhaul.common.registry.forge;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tech.thatgravyboat.creeperoverhaul.Creepers;

import java.util.function.Supplier;

public class ModEntitiesImpl {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Creepers.MODID);

    public static <E extends Entity, T extends EntityType<E>> Supplier<T> registerEntity(String id, Supplier<T> entity) {
        return ENTITIES.register(id, entity);
    }
}
