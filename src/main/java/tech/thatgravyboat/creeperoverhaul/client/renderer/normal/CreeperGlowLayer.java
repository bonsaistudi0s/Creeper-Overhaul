package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;

public class CreeperGlowLayer<E extends BaseCreeper> extends GeoLayerRenderer<E> {

    public CreeperGlowLayer(IGeoRenderer<E> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack stack, VertexConsumerProvider buffer, int packedLightIn, E creeper, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!creeper.isPowered()) return;

        CreeperType type = creeper.getCreeperType();

        GeoModel normalModel = this.getEntityModel().getModel(type.model());
        VertexConsumer glowConsumer = buffer.getBuffer(RenderLayer.getEyes(type.glowingTexture()));

        getRenderer().render(normalModel, creeper, partialTicks,
                null, stack, null, glowConsumer,
                packedLightIn, OverlayTexture.DEFAULT_UV,
                1f, 1f, 1f, 1f);

    }
}
