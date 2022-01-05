package tech.thatgravyboat.creeperoverhaul.common.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.entity.CreeperTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.NeutralCreeper;

public class ModEntities {

    public static final EntityType<BaseCreeper> JUNGLE_CREEPER = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    createCreeper(CreeperTypes.JUNGLE))
            .dimensions(EntityDimensions.fixed(0.6F, 1.7F))
            .trackRangeBlocks(8).build();

    public static final EntityType<NeutralCreeper> BAMBOO_CREEPER = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    createNeutralCreeper(CreeperTypes.BAMBOO))
            .dimensions(EntityDimensions.fixed(0.6F, 2F))
            .trackRangeBlocks(8).build();

    public static final EntityType<BaseCreeper> DESERT_CREEPER = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    createCreeper(CreeperTypes.DESERT))
            .dimensions(EntityDimensions.fixed(0.6F, 1.7F))
            .trackRangeBlocks(8).build();

    public static final EntityType<BaseCreeper> BADLANDS_CREEPER = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    createCreeper(CreeperTypes.BADLANDS))
            .dimensions(EntityDimensions.fixed(1F, 1.8F))
            .trackRangeBlocks(8).build();

    public static final EntityType<NeutralCreeper> HILLS_CREEPER = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    createNeutralCreeper(CreeperTypes.HILLS))
            .dimensions(EntityDimensions.fixed(0.6F, 1.7F))
            .trackRangeBlocks(8).build();

    public static final EntityType<NeutralCreeper> SAVANNAH_CREEPER = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    createNeutralCreeper(CreeperTypes.SAVANNAH))
            .dimensions(EntityDimensions.fixed(0.6F, 2.2F))
            .trackRangeBlocks(8).build();

    public static final EntityType<NeutralCreeper> MUSHROOM_CREEPER = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    createNeutralCreeper(CreeperTypes.MUSHROOM))
            .dimensions(EntityDimensions.fixed(1F, 1.7F))
            .trackRangeBlocks(8).build();

    public static final EntityType<BaseCreeper> SWAMP_CREEPER = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    createCreeper(CreeperTypes.SWAMP))
            .dimensions(EntityDimensions.fixed(0.7F, 1.7F))
            .trackRangeBlocks(8).build();

    public static final EntityType<BaseCreeper> DRIPSTONE_CREEPER = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    createCreeper(CreeperTypes.DRIPSTONE))
            .dimensions(EntityDimensions.fixed(0.6F, 1.7F))
            .trackRangeBlocks(8).build();

    public static final EntityType<BaseCreeper> CAVE_CREEPER = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    createCreeper(CreeperTypes.CAVE))
            .dimensions(EntityDimensions.fixed(0.7F, 1.7F))
            .trackRangeBlocks(8).build();

    public static final EntityType<BaseCreeper> DARK_OAK_CREEPER = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    createCreeper(CreeperTypes.DARK_OAK))
            .dimensions(EntityDimensions.fixed(0.7F, 1.7F))
            .trackRangeBlocks(8).build();

    public static final EntityType<BaseCreeper> SPRUCE_CREEPER = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    createCreeper(CreeperTypes.SPRUCE))
            .dimensions(EntityDimensions.fixed(0.6F, 1.7F))
            .trackRangeBlocks(8).build();

    public static final EntityType<BaseCreeper> BEACH_CREEPER = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    createCreeper(CreeperTypes.BEACH))
            .dimensions(EntityDimensions.fixed(0.6F, 1.7F))
            .trackRangeBlocks(8).build();

    public static final EntityType<NeutralCreeper> SNOWY_CREEPER = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER,
                    createNeutralCreeper(CreeperTypes.SNOWY))
            .dimensions(EntityDimensions.fixed(0.7F, 1.7F))
            .trackRangeBlocks(8).build();

    private static EntityType.EntityFactory<BaseCreeper> createCreeper(CreeperType creeperType) {
        return (type, level) -> new BaseCreeper(type, level, creeperType);
    }

    private static EntityType.EntityFactory<NeutralCreeper> createNeutralCreeper(CreeperType creeperType) {
        return (type, level) -> new NeutralCreeper(type, level, creeperType);
    }

    public static void register() {
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Creepers.MODID, "jungle_creeper"), JUNGLE_CREEPER);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Creepers.MODID, "bamboo_creeper"), BAMBOO_CREEPER);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Creepers.MODID, "desert_creeper"), DESERT_CREEPER);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Creepers.MODID, "badlands_creeper"), BADLANDS_CREEPER);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Creepers.MODID, "hills_creeper"), HILLS_CREEPER);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Creepers.MODID, "savannah_creeper"), SAVANNAH_CREEPER);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Creepers.MODID, "mushroom_creeper"), MUSHROOM_CREEPER);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Creepers.MODID, "swamp_creeper"), SWAMP_CREEPER);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Creepers.MODID, "dripstone_creeper"), DRIPSTONE_CREEPER);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Creepers.MODID, "cave_creeper"), CAVE_CREEPER);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Creepers.MODID, "dark_oak_creeper"), DARK_OAK_CREEPER);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Creepers.MODID, "spruce_creeper"), SPRUCE_CREEPER);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Creepers.MODID, "beach_creeper"), BEACH_CREEPER);
        Registry.register(Registry.ENTITY_TYPE, new Identifier(Creepers.MODID, "snowy_creeper"), SNOWY_CREEPER);
    }


}
