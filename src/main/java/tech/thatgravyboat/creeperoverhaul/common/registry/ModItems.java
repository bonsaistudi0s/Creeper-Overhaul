package tech.thatgravyboat.creeperoverhaul.common.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.item.TinyCactusItem;

public class ModItems {

    public static final CreativeModeTab TAB = new CreativeModeTab(Creepers.MODID) {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(JUNGLE_SPAWN_EGG.get());
        }
    };

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Creepers.MODID);

    public static final RegistryObject<Item> JUNGLE_SPAWN_EGG = ITEMS.register("jungle_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JUNGLE_CREEPER, 0x507541, 0x59461A, getSpawnEggProperties()));

    public static final RegistryObject<Item> BAMBOO_SPAWN_EGG = ITEMS.register("bamboo_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BAMBOO_CREEPER, 0x599003, 0x1F3322, getSpawnEggProperties()));

    public static final RegistryObject<Item> DESERT_SPAWN_EGG = ITEMS.register("desert_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.DESERT_CREEPER, 0xD1BA8A, 0x966E3D, getSpawnEggProperties()));

    public static final RegistryObject<Item> BADLANDS_SPAWN_EGG = ITEMS.register("badlands_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BADLANDS_CREEPER, 0xA36C4B, 0xE8C943, getSpawnEggProperties()));

    public static final RegistryObject<Item> HILLS_SPAWN_EGG = ITEMS.register("hills_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.HILLS_CREEPER, 0x9A9B88, 0x4F5E53, getSpawnEggProperties()));

    public static final RegistryObject<Item> SAVANNAH_SPAWN_EGG = ITEMS.register("savannah_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SAVANNAH_CREEPER, 0xAD5D32, 0x6E3A29, getSpawnEggProperties()));

    public static final RegistryObject<Item> MUSHROOM_SPAWN_EGG = ITEMS.register("mushroom_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.MUSHROOM_CREEPER, 0x60526A, 0xD15A4B, getSpawnEggProperties()));

    public static final RegistryObject<Item> SWAMP_SPAWN_EGG = ITEMS.register("swamp_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SWAMP_CREEPER, 0x4F5835, 0x1B2824, getSpawnEggProperties()));

    public static final RegistryObject<Item> DRIPSTONE_SPAWN_EGG = ITEMS.register("dripstone_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.DRIPSTONE_CREEPER, 0x836356, 0xA08D71, getSpawnEggProperties()));

    public static final RegistryObject<Item> CAVE_SPAWN_EGG = ITEMS.register("cave_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CAVE_CREEPER, 0x7E847C, 0x3A3F3E, getSpawnEggProperties()));

    public static final RegistryObject<Item> DARK_OAK_SPAWN_EGG = ITEMS.register("dark_oak_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.DARK_OAK_CREEPER, 0x3F311D, 0x1E1519, getSpawnEggProperties()));

    public static final RegistryObject<Item> SPRUCE_SPAWN_EGG = ITEMS.register("spruce_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SPRUCE_CREEPER, 0x888788, 0x738552, getSpawnEggProperties()));

    public static final RegistryObject<Item> BEACH_SPAWN_EGG = ITEMS.register("beach_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BEACH_CREEPER, 0xDAC896, 0x704E3A, getSpawnEggProperties()));

    public static final RegistryObject<Item> SNOWY_SPAWN_EGG = ITEMS.register("snowy_creeper_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SNOWY_CREEPER, 0xBECDD8, 0xE8F8F9, getSpawnEggProperties()));

    public static final RegistryObject<Item> TINY_CACTUS = ITEMS.register("tiny_cactus",
            () -> new TinyCactusItem(ModBlocks.TINY_CACTUS.get(), getSpawnEggProperties()));

    private static Item.Properties getSpawnEggProperties() {
        return new Item.Properties().tab(TAB);
    }

}
