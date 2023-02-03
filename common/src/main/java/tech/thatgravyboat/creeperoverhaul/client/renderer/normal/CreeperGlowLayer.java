package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.creeperoverhaul.client.RenderTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;

import static net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords;

public class CreeperGlowLayer<E extends BaseCreeper> extends GeoRenderLayer<E> {

    public CreeperGlowLayer(GeoRenderer<E> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack stack, E creeper, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource buffers, VertexConsumer buffer, float partialTicks, int packedLight, int packedOverlay) {
        float f = creeper.getSwelling(partialTicks);
        if (f > 0f || creeper.isPowered()) {
            if (creeper.isPowered()) f = 1f;

            CreeperType type = creeper.type;

            BakedGeoModel normalModel = this.getGeoModel().getBakedModel(type.model());
            VertexConsumer glowConsumer = buffers.getBuffer(RenderTypes.getTransparentEyes(type.glowingTexture()));

            getRenderer().reRender(normalModel, stack, buffers, creeper,
                    null, glowConsumer, partialTicks,
                    packedLight, getOverlayCoords(creeper, f),
                    1f, 1f, 1f, f);
        }
    }
}
