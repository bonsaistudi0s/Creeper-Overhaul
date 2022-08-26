package tech.thatgravyboat.creeperoverhaul.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.client.forge.ClientConfig;
import tech.thatgravyboat.creeperoverhaul.client.forge.CreepersForgeClient;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModBlocks;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModSpawns;
import tech.thatgravyboat.creeperoverhaul.common.registry.forge.*;

import java.util.HashMap;
import java.util.Map;

@Mod(Creepers.MODID)
public class CreepersForge {

    public CreepersForge() {
        Creepers.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItemsImpl.ITEMS.register(bus);
        ModBlocksImpl.BLOCKS.register(bus);
        ModEntitiesImpl.ENTITIES.register(bus);
        ModSoundsImpl.SOUNDS.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.COMMON_CONFIG);

        bus.addListener(this::onAttributes);
        bus.addListener(this::onComplete);
        bus.addListener(this::onCommonSetup);
        bus.addListener(CreepersForgeClient::onShaderRegister);
        bus.addListener(CreepersForgeClient::onClient);
    }

    public void onAttributes(EntityAttributeCreationEvent event) {
        Map<EntityType<? extends LivingEntity>, AttributeSupplier> attributes = new HashMap<>();
        Creepers.registerAttributes(attributes);
        attributes.forEach(event::put);
    }

    public void onComplete(FMLLoadCompleteEvent event) {
        ModSpawns.addSpawnRules();
    }

    public void onCommonSetup(FMLCommonSetupEvent event) {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation(Creepers.MODID, "tiny_cactus"), ModBlocks.POTTED_TINY_CACTUS);
    }
}
