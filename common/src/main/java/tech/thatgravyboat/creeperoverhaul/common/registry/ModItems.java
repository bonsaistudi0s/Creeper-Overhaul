package tech.thatgravyboat.creeperoverhaul.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.utils.PlatformUtils;

import java.util.function.Supplier;

public class ModItems {

    public static final CreativeModeTab TAB = PlatformUtils.createTab(new ResourceLocation(Creepers.MODID, "item_group"), Items.CREEPER_HEAD::getDefaultInstance);

    public static final Supplier<SpawnEggItem> JUNGLE_SPAWN_EGG = registerSpawnEgg("jungle_creeper_spawn_egg",
            ModEntities.JUNGLE_CREEPER, 0x507541, 0x59461A, getModProperties());

    public static final Supplier<SpawnEggItem> BAMBOO_SPAWN_EGG = registerSpawnEgg("bamboo_creeper_spawn_egg",
            ModEntities.BAMBOO_CREEPER, 0x599003, 0x1F3322, getModProperties());

    public static final Supplier<SpawnEggItem> DESERT_SPAWN_EGG = registerSpawnEgg("desert_creeper_spawn_egg",
            ModEntities.DESERT_CREEPER, 0xD1BA8A, 0x966E3D, getModProperties());

    public static final Supplier<SpawnEggItem> BADLANDS_SPAWN_EGG = registerSpawnEgg("badlands_creeper_spawn_egg",
            ModEntities.BADLANDS_CREEPER, 0xA36C4B, 0xE8C943, getModProperties());

    public static final Supplier<SpawnEggItem> HILLS_SPAWN_EGG = registerSpawnEgg("hills_creeper_spawn_egg",
            ModEntities.HILLS_CREEPER, 0x9A9B88, 0x4F5E53, getModProperties());

    public static final Supplier<SpawnEggItem> SAVANNAH_SPAWN_EGG = registerSpawnEgg("savannah_creeper_spawn_egg",
            ModEntities.SAVANNAH_CREEPER, 0xAD5D32, 0x6E3A29, getModProperties());

    public static final Supplier<SpawnEggItem> MUSHROOM_SPAWN_EGG = registerSpawnEgg("mushroom_creeper_spawn_egg",
            ModEntities.MUSHROOM_CREEPER, 0x60526A, 0xD15A4B, getModProperties());

    public static final Supplier<SpawnEggItem> SWAMP_SPAWN_EGG = registerSpawnEgg("swamp_creeper_spawn_egg",
            ModEntities.SWAMP_CREEPER, 0x4F5835, 0x1B2824, getModProperties());

    public static final Supplier<SpawnEggItem> DRIPSTONE_SPAWN_EGG = registerSpawnEgg("dripstone_creeper_spawn_egg",
            ModEntities.DRIPSTONE_CREEPER, 0x836356, 0xA08D71, getModProperties());

    public static final Supplier<SpawnEggItem> CAVE_SPAWN_EGG = registerSpawnEgg("cave_creeper_spawn_egg",
            ModEntities.CAVE_CREEPER, 0x7E847C, 0x3A3F3E, getModProperties());

    public static final Supplier<SpawnEggItem> DARK_OAK_SPAWN_EGG = registerSpawnEgg("dark_oak_creeper_spawn_egg",
            ModEntities.DARK_OAK_CREEPER, 0x3F311D, 0x1E1519, getModProperties());

    public static final Supplier<SpawnEggItem> SPRUCE_SPAWN_EGG = registerSpawnEgg("spruce_creeper_spawn_egg",
            ModEntities.SPRUCE_CREEPER, 0x888788, 0x738552, getModProperties());

    public static final Supplier<SpawnEggItem> BEACH_SPAWN_EGG = registerSpawnEgg("beach_creeper_spawn_egg",
            ModEntities.BEACH_CREEPER, 0xDAC896, 0x704E3A, getModProperties());

    public static final Supplier<SpawnEggItem> SNOWY_SPAWN_EGG = registerSpawnEgg("snowy_creeper_spawn_egg",
            ModEntities.SNOWY_CREEPER, 0xBECDD8, 0xE8F8F9, getModProperties());

    public static final Supplier<Item> TINY_CACTUS = registerItem("tiny_cactus", () -> createCactus(ModBlocks.TINY_CACTUS.get(), getModProperties()));

    public static void init() {
        //Init Class
    }

    private static Item.Properties getModProperties() {
        return new Item.Properties().tab(TAB);
    }

    @ExpectPlatform
    public static <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <E extends Mob, T extends EntityType<E>> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<T> entity, int primary, int second, Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockItem createCactus(Block block, Item.Properties properties) {
        throw new AssertionError();
    }
}
