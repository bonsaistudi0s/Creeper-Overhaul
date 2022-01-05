package tech.thatgravyboat.creeperoverhaul.client.init;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.client.renderer.normal.CreeperModel;
import tech.thatgravyboat.creeperoverhaul.client.renderer.normal.CreeperRenderer;
import tech.thatgravyboat.creeperoverhaul.client.renderer.replaced.ReplacedCreeperRenderer;
import tech.thatgravyboat.creeperoverhaul.common.entity.CreeperTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModBlocks;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModEntities;

public class ClientInit implements ClientModInitializer {

    public static Shader energySwirlShader;

    @Override
    public void onInitializeClient() {
        registerRenderers();
    }

    public static void registerRenderers() {
        EntityRendererRegistry.register(ModEntities.JUNGLE_CREEPER, createRenderer(CreeperTypes.JUNGLE));
        EntityRendererRegistry.register(ModEntities.BAMBOO_CREEPER, createRenderer(CreeperTypes.BAMBOO));
        EntityRendererRegistry.register(ModEntities.DESERT_CREEPER, createRenderer(CreeperTypes.DESERT));
        EntityRendererRegistry.register(ModEntities.BADLANDS_CREEPER, createRenderer(CreeperTypes.BADLANDS));
        EntityRendererRegistry.register(ModEntities.HILLS_CREEPER, createRenderer(CreeperTypes.HILLS));
        EntityRendererRegistry.register(ModEntities.SAVANNAH_CREEPER, createRenderer(CreeperTypes.SAVANNAH));
        EntityRendererRegistry.register(ModEntities.MUSHROOM_CREEPER, createRenderer(CreeperTypes.MUSHROOM));
        EntityRendererRegistry.register(ModEntities.SWAMP_CREEPER, createRenderer(CreeperTypes.SWAMP));
        EntityRendererRegistry.register(ModEntities.DRIPSTONE_CREEPER, createRenderer(CreeperTypes.DRIPSTONE));
        EntityRendererRegistry.register(ModEntities.CAVE_CREEPER, createRenderer(CreeperTypes.CAVE));
        EntityRendererRegistry.register(ModEntities.DARK_OAK_CREEPER, createRenderer(CreeperTypes.DARK_OAK));
        EntityRendererRegistry.register(ModEntities.SPRUCE_CREEPER, createRenderer(CreeperTypes.SPRUCE));
        EntityRendererRegistry.register(ModEntities.BEACH_CREEPER, createRenderer(CreeperTypes.BEACH));
        EntityRendererRegistry.register(ModEntities.SNOWY_CREEPER, createRenderer(CreeperTypes.SNOWY));
        EntityRendererRegistry.register(EntityType.CREEPER, ReplacedCreeperRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TINY_CACTUS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TINY_CACTUS_POT, RenderLayer.getCutout());
    }
    
    private static <E extends BaseCreeper> EntityRendererFactory<E> createRenderer(CreeperType type) {
        return manager -> new CreeperRenderer<>(manager, new CreeperModel<>(type));
    }

    public static class RenderTypes extends RenderLayer {

        private static final Shader ENERGY_SWIRL_SHARD = new Shader(() -> energySwirlShader);

        public RenderTypes(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
            super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
        }


        public static RenderLayer getSwirl(Identifier location, float u, float v) {
            return RenderLayer.of("creeperoverhaul_rendertype_energy_swirl",
                    VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL,
                    VertexFormat.DrawMode.QUADS,
                    256,
                    false,
                    true,
                    RenderLayer.MultiPhaseParameters.builder()
                            .shader(ENERGY_SWIRL_SHARD)
                            .texture(new Texture(location, false, false))
                            .texturing(new OffsetTexturing(u, v))
                            .transparency(ADDITIVE_TRANSPARENCY)
                            .cull(DISABLE_CULLING)
                            .lightmap(ENABLE_LIGHTMAP)
                            .overlay(ENABLE_OVERLAY_COLOR)
                            .build(false)
            );
        }
    }
}
