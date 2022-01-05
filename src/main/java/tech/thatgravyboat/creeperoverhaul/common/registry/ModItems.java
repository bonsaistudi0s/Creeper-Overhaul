package tech.thatgravyboat.creeperoverhaul.common.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.item.TinyCactusItem;

public class ModItems {

    public static final Item JUNGLE_SPAWN_EGG = new SpawnEggItem(ModEntities.JUNGLE_CREEPER, 0x507541, 0x59461A, getSpawnEggProperties());

    public static final Item BAMBOO_SPAWN_EGG = new SpawnEggItem(ModEntities.BAMBOO_CREEPER, 0x599003, 0x1F3322, getSpawnEggProperties());

    public static final Item DESERT_SPAWN_EGG = new SpawnEggItem(ModEntities.DESERT_CREEPER, 0xD1BA8A, 0x966E3D, getSpawnEggProperties());

    public static final Item BADLANDS_SPAWN_EGG = new SpawnEggItem(ModEntities.BADLANDS_CREEPER, 0xA36C4B, 0xE8C943, getSpawnEggProperties());

    public static final Item HILLS_SPAWN_EGG = new SpawnEggItem(ModEntities.HILLS_CREEPER, 0x9A9B88, 0x4F5E53, getSpawnEggProperties());

    public static final Item SAVANNAH_SPAWN_EGG = new SpawnEggItem(ModEntities.SAVANNAH_CREEPER, 0xAD5D32, 0x6E3A29, getSpawnEggProperties());

    public static final Item MUSHROOM_SPAWN_EGG = new SpawnEggItem(ModEntities.MUSHROOM_CREEPER, 0x60526A, 0xD15A4B, getSpawnEggProperties());

    public static final Item SWAMP_SPAWN_EGG = new SpawnEggItem(ModEntities.SWAMP_CREEPER, 0x4F5835, 0x1B2824, getSpawnEggProperties());

    public static final Item DRIPSTONE_SPAWN_EGG = new SpawnEggItem(ModEntities.DRIPSTONE_CREEPER, 0x836356, 0xA08D71, getSpawnEggProperties());

    public static final Item CAVE_SPAWN_EGG = new SpawnEggItem(ModEntities.CAVE_CREEPER, 0x7E847C, 0x3A3F3E, getSpawnEggProperties());

    public static final Item DARK_OAK_SPAWN_EGG = new SpawnEggItem(ModEntities.DARK_OAK_CREEPER, 0x3F311D, 0x1E1519, getSpawnEggProperties());

    public static final Item SPRUCE_SPAWN_EGG = new SpawnEggItem(ModEntities.SPRUCE_CREEPER, 0x888788, 0x738552, getSpawnEggProperties());

    public static final Item BEACH_SPAWN_EGG = new SpawnEggItem(ModEntities.BEACH_CREEPER, 0xDAC896, 0x704E3A, getSpawnEggProperties());

    public static final Item SNOWY_SPAWN_EGG = new SpawnEggItem(ModEntities.SNOWY_CREEPER, 0xBECDD8, 0xE8F8F9, getSpawnEggProperties());

    public static final Item TINY_CACTUS = new TinyCactusItem(ModBlocks.TINY_CACTUS, getSpawnEggProperties().equipmentSlot(stack -> EquipmentSlot.HEAD));

    private static FabricItemSettings getSpawnEggProperties() {
        return new FabricItemSettings().group(Creepers.TAB);
    }

    public static void reigster() {
        Registry.register(Registry.ITEM, new Identifier(Creepers.MODID, "jungle_creeper_spawn_egg"), JUNGLE_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Creepers.MODID, "bamboo_creeper_spawn_egg"), BAMBOO_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Creepers.MODID, "desert_creeper_spawn_egg"), DESERT_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Creepers.MODID, "badlands_creeper_spawn_egg"), BADLANDS_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Creepers.MODID, "hills_creeper_spawn_egg"), HILLS_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Creepers.MODID, "savannah_creeper_spawn_egg"), SAVANNAH_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Creepers.MODID, "mushroom_creeper_spawn_egg"), MUSHROOM_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Creepers.MODID, "swamp_creeper_spawn_egg"), SWAMP_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Creepers.MODID, "dripstone_creeper_spawn_egg"), DRIPSTONE_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Creepers.MODID, "cave_creeper_spawn_egg"), CAVE_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Creepers.MODID, "dark_oak_creeper_spawn_egg"), DARK_OAK_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Creepers.MODID, "spruce_creeper_spawn_egg"), SPRUCE_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Creepers.MODID, "beach_creeper_spawn_egg"), BEACH_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Creepers.MODID, "snowy_creeper_spawn_egg"), SNOWY_SPAWN_EGG);
        Registry.register(Registry.ITEM, new Identifier(Creepers.MODID, "tiny_cactus"), TINY_CACTUS);
    }

}
