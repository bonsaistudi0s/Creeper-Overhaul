package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.client.RenderTypes;

public class ReplacedCreeperGlowLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {

    private static final ResourceLocation PLAINS_GLOW_TEXTURE = new ResourceLocation(Creepers.MODID, "textures/entity/plains/plains_creeper_glow.png");

    public ReplacedCreeperGlowLayer(GeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack stack, T entity, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource buffers, VertexConsumer buffer, float partialTicks, int packedLight, int packedOverlay) {
        if (entity instanceof Creeper creeper) {
            float f = creeper.getSwelling(partialTicks);

            if (f > 0f || creeper.isPowered()) {
                if (creeper.isPowered()) f = 1f;

                BakedGeoModel normalModel = this.getGeoModel().getBakedModel(this.getGeoModel().getModelResource(null));
                VertexConsumer glowConsumer = buffers.getBuffer(RenderTypes.getTransparentEyes(PLAINS_GLOW_TEXTURE));

                getRenderer().reRender(normalModel, stack, buffers, entity,
                        null, glowConsumer, partialTicks,
                        packedLight, OverlayTexture.NO_OVERLAY,
                        1f, 1f, 1f, f);
            }
        }
    }
}
