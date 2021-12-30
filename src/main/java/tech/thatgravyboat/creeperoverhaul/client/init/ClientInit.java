package tech.thatgravyboat.creeperoverhaul.client.init;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.checkerframework.checker.units.qual.C;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.client.renderer.normal.CreeperModel;
import tech.thatgravyboat.creeperoverhaul.client.renderer.normal.CreeperRenderer;
import tech.thatgravyboat.creeperoverhaul.client.renderer.replaced.ReplacedCreeperRenderer;
import tech.thatgravyboat.creeperoverhaul.common.entity.CreeperTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModBlocks;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModEntities;

import java.io.IOException;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Creepers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientInit {

    private static final ResourceLocation ENERGY_SWIRL_RENDERTYPE = new ResourceLocation(Creepers.MODID, "rendertype_energy_swirl");

    private static ShaderInstance energySwirlShader;

    @SubscribeEvent
    public static void onShadersRegistered(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceManager(), ENERGY_SWIRL_RENDERTYPE, DefaultVertexFormat.NEW_ENTITY),
                shader -> energySwirlShader = shader);
    }

    @SubscribeEvent
    public static void onClient(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.JUNGLE_CREEPER.get(), createRenderer(CreeperTypes.JUNGLE));
        EntityRenderers.register(ModEntities.BAMBOO_CREEPER.get(), createRenderer(CreeperTypes.BAMBOO));
        EntityRenderers.register(ModEntities.DESERT_CREEPER.get(), createRenderer(CreeperTypes.DESERT));
        EntityRenderers.register(ModEntities.BADLANDS_CREEPER.get(), createRenderer(CreeperTypes.BADLANDS));
        EntityRenderers.register(ModEntities.HILLS_CREEPER.get(), createRenderer(CreeperTypes.HILLS));
        EntityRenderers.register(ModEntities.SAVANNAH_CREEPER.get(), createRenderer(CreeperTypes.SAVANNAH));
        EntityRenderers.register(ModEntities.MUSHROOM_CREEPER.get(), createRenderer(CreeperTypes.MUSHROOM));
        EntityRenderers.register(ModEntities.SWAMP_CREEPER.get(), createRenderer(CreeperTypes.SWAMP));
        EntityRenderers.register(ModEntities.DRIPSTONE_CREEPER.get(), createRenderer(CreeperTypes.DRIPSTONE));
        EntityRenderers.register(ModEntities.CAVE_CREEPER.get(), createRenderer(CreeperTypes.CAVE));
        EntityRenderers.register(ModEntities.DARK_OAK_CREEPER.get(), createRenderer(CreeperTypes.DARK_OAK));
        EntityRenderers.register(ModEntities.SPRUCE_CREEPER.get(), createRenderer(CreeperTypes.SPRUCE));
        EntityRenderers.register(ModEntities.BEACH_CREEPER.get(), createRenderer(CreeperTypes.BEACH));
        EntityRenderers.register(ModEntities.SNOWY_CREEPER.get(), createRenderer(CreeperTypes.SNOWY));
        EntityRenderers.register(EntityType.CREEPER, ReplacedCreeperRenderer::new);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TINY_CACTUS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TINY_CACTUS_POT.get(), RenderType.cutout());
    }
    
    private static <E extends BaseCreeper> EntityRendererProvider<E> createRenderer(CreeperType type) {
        return manager -> new CreeperRenderer<>(manager, new CreeperModel<>(type));
    }

    public static class RenderTypes extends RenderType {

        private static final ShaderStateShard ENERGY_SWIRL_SHARD = new ShaderStateShard(() -> energySwirlShader);

        public RenderTypes(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
            super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
        }

        public static RenderType getSwirl(ResourceLocation location, float u, float v) {
            return RenderType.create(ENERGY_SWIRL_RENDERTYPE.toString(),
                    DefaultVertexFormat.NEW_ENTITY,
                    VertexFormat.Mode.QUADS,
                    256,
                    false,
                    true,
                    RenderType.CompositeState.builder()
                            .setShaderState(ENERGY_SWIRL_SHARD)
                            .setTextureState(new RenderStateShard.TextureStateShard(location, false, false))
                            .setTexturingState(new RenderStateShard.OffsetTexturingStateShard(u, v))
                            .setTransparencyState(ADDITIVE_TRANSPARENCY)
                            .setCullState(NO_CULL)
                            .setLightmapState(LIGHTMAP)
                            .setOverlayState(OVERLAY)
                            .createCompositeState(false)
            );
        }
    }
}
