package tech.thatgravyboat.creeperoverhaul.common.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.entity.CreeperTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.NeutralCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.PassiveCreeper;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Creepers.MODID);

    public static final RegistryObject<EntityType<BaseCreeper>> JUNGLE_CREEPER = ENTITY_TYPES.register("jungle_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.JUNGLE), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("jungle_creeper"));

    public static final RegistryObject<EntityType<NeutralCreeper>> BAMBOO_CREEPER = ENTITY_TYPES.register("bamboo_creeper",
            () -> EntityType.Builder.<NeutralCreeper>of((type, level) -> new NeutralCreeper(type, level, CreeperTypes.BAMBOO), MobCategory.MONSTER).sized(0.6F, 2F)
                    .clientTrackingRange(8).build("bamboo_creeper"));

    public static final RegistryObject<EntityType<BaseCreeper>> DESERT_CREEPER = ENTITY_TYPES.register("desert_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.DESERT), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("desert_creeper"));

    public static final RegistryObject<EntityType<BaseCreeper>> BADLANDS_CREEPER = ENTITY_TYPES.register("badlands_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.BADLANDS), MobCategory.MONSTER).sized(1F, 1.8F)
                    .clientTrackingRange(8).build("badlands_creeper"));

    public static final RegistryObject<EntityType<NeutralCreeper>> HILLS_CREEPER = ENTITY_TYPES.register("hills_creeper",
            () -> EntityType.Builder.<NeutralCreeper>of((type, level) -> new NeutralCreeper(type, level, CreeperTypes.HILLS), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("hills_creeper"));

    public static final RegistryObject<EntityType<NeutralCreeper>> SAVANNAH_CREEPER = ENTITY_TYPES.register("savannah_creeper",
            () -> EntityType.Builder.<NeutralCreeper>of((type, level) -> new NeutralCreeper(type, level, CreeperTypes.SAVANNAH), MobCategory.MONSTER).sized(0.6F, 2.2F)
                    .clientTrackingRange(8).build("savannah_creeper"));

    public static final RegistryObject<EntityType<NeutralCreeper>> MUSHROOM_CREEPER = ENTITY_TYPES.register("mushroom_creeper",
            () -> EntityType.Builder.<NeutralCreeper>of((type, level) -> new PassiveCreeper(type, level, CreeperTypes.MUSHROOM), MobCategory.CREATURE).sized(1F, 1.7F)
                    .clientTrackingRange(8).build("mushroom_creeper"));

    public static final RegistryObject<EntityType<BaseCreeper>> SWAMP_CREEPER = ENTITY_TYPES.register("swamp_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.SWAMP), MobCategory.MONSTER).sized(0.7F, 1.7F)
                    .clientTrackingRange(8).build("swamp_creeper"));

    public static final RegistryObject<EntityType<BaseCreeper>> DRIPSTONE_CREEPER = ENTITY_TYPES.register("dripstone_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.DRIPSTONE), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("dripstone_creeper"));

    public static final RegistryObject<EntityType<BaseCreeper>> CAVE_CREEPER = ENTITY_TYPES.register("cave_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.CAVE), MobCategory.MONSTER).sized(0.7F, 1.7F)
                    .clientTrackingRange(8).build("cave_creeper"));

    public static final RegistryObject<EntityType<BaseCreeper>> DARK_OAK_CREEPER = ENTITY_TYPES.register("dark_oak_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.DARK_OAK), MobCategory.MONSTER).sized(0.7F, 1.7F)
                    .clientTrackingRange(8).build("dark_oak_creeper"));

    public static final RegistryObject<EntityType<BaseCreeper>> SPRUCE_CREEPER = ENTITY_TYPES.register("spruce_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.SPRUCE), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("spruce_creeper"));

    public static final RegistryObject<EntityType<BaseCreeper>> BEACH_CREEPER = ENTITY_TYPES.register("beach_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.BEACH), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("beach_creeper"));

    public static final RegistryObject<EntityType<NeutralCreeper>> SNOWY_CREEPER = ENTITY_TYPES.register("snowy_creeper",
            () -> EntityType.Builder.<NeutralCreeper>of((type, level) -> new NeutralCreeper(type, level, CreeperTypes.SNOWY), MobCategory.MONSTER).sized(0.7F, 1.7F)
                    .clientTrackingRange(8).build("snowy_creeper"));
}
