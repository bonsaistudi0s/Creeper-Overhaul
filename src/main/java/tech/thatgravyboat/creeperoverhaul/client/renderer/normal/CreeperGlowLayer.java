package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import tech.thatgravyboat.creeperoverhaul.client.init.ClientInit;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;

public class CreeperGlowLayer<E extends BaseCreeper> extends GeoLayerRenderer<E> {

    public CreeperGlowLayer(IGeoRenderer<E> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLightIn, E creeper, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!creeper.isPowered()) return;

        CreeperType type = creeper.getCreeperType();

        GeoModel normalModel = this.getEntityModel().getModel(type.model());
        VertexConsumer glowConsumer = buffer.getBuffer(ClientInit.RenderTypes.getTransparentEyes(type.glowingTexture()));

        float f = creeper.getSwelling(partialTicks);
        getRenderer().render(normalModel, creeper, partialTicks,
                null, stack, null, glowConsumer,
                packedLightIn, OverlayTexture.NO_OVERLAY,
                1f, 1f, 1f, f);

    }
}
