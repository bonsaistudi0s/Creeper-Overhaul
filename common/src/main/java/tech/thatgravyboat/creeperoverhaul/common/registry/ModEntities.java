package tech.thatgravyboat.creeperoverhaul.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tech.thatgravyboat.creeperoverhaul.common.entity.CreeperTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.NeutralCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.PassiveCreeper;

import java.util.function.Supplier;

public class ModEntities {


    public static final Supplier<EntityType<BaseCreeper>> JUNGLE_CREEPER = registerEntity("jungle_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.JUNGLE), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("jungle_creeper"));

    public static final Supplier<EntityType<NeutralCreeper>> BAMBOO_CREEPER = registerEntity("bamboo_creeper",
            () -> EntityType.Builder.<NeutralCreeper>of((type, level) -> new NeutralCreeper(type, level, CreeperTypes.BAMBOO), MobCategory.MONSTER).sized(0.6F, 2F)
                    .clientTrackingRange(8).build("bamboo_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> DESERT_CREEPER = registerEntity("desert_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.DESERT), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("desert_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> BADLANDS_CREEPER = registerEntity("badlands_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.BADLANDS), MobCategory.MONSTER).sized(1F, 1.8F)
                    .clientTrackingRange(8).build("badlands_creeper"));

    public static final Supplier<EntityType<NeutralCreeper>> HILLS_CREEPER = registerEntity("hills_creeper",
            () -> EntityType.Builder.<NeutralCreeper>of((type, level) -> new NeutralCreeper(type, level, CreeperTypes.HILLS), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("hills_creeper"));

    public static final Supplier<EntityType<NeutralCreeper>> SAVANNAH_CREEPER = registerEntity("savannah_creeper",
            () -> EntityType.Builder.<NeutralCreeper>of((type, level) -> new NeutralCreeper(type, level, CreeperTypes.SAVANNAH), MobCategory.MONSTER).sized(0.6F, 2.2F)
                    .clientTrackingRange(8).build("savannah_creeper"));

    public static final Supplier<EntityType<NeutralCreeper>> MUSHROOM_CREEPER = registerEntity("mushroom_creeper",
            () -> EntityType.Builder.<NeutralCreeper>of((type, level) -> new PassiveCreeper(type, level, CreeperTypes.MUSHROOM), MobCategory.CREATURE).sized(1F, 1.7F)
                    .clientTrackingRange(8).build("mushroom_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> SWAMP_CREEPER = registerEntity("swamp_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.SWAMP), MobCategory.MONSTER).sized(0.7F, 1.7F)
                    .clientTrackingRange(8).build("swamp_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> DRIPSTONE_CREEPER = registerEntity("dripstone_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.DRIPSTONE), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("dripstone_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> CAVE_CREEPER = registerEntity("cave_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.CAVE), MobCategory.MONSTER).sized(0.7F, 1.7F)
                    .clientTrackingRange(8).build("cave_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> DARK_OAK_CREEPER = registerEntity("dark_oak_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.DARK_OAK), MobCategory.MONSTER).sized(0.7F, 1.7F)
                    .clientTrackingRange(8).build("dark_oak_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> SPRUCE_CREEPER = registerEntity("spruce_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.SPRUCE), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("spruce_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> BEACH_CREEPER = registerEntity("beach_creeper",
            () -> EntityType.Builder.<BaseCreeper>of((type, level) -> new BaseCreeper(type, level, CreeperTypes.BEACH), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("beach_creeper"));

    public static final Supplier<EntityType<NeutralCreeper>> SNOWY_CREEPER = registerEntity("snowy_creeper",
            () -> EntityType.Builder.<NeutralCreeper>of((type, level) -> new NeutralCreeper(type, level, CreeperTypes.SNOWY), MobCategory.MONSTER).sized(0.7F, 1.7F)
                    .clientTrackingRange(8).build("snowy_creeper"));

    public static void init() {
        //Init Class
    }
    @ExpectPlatform
    public static <E extends Entity, T extends EntityType<E>> Supplier<T> registerEntity(String id, Supplier<T> entity) {
        throw new AssertionError();
    }
}
