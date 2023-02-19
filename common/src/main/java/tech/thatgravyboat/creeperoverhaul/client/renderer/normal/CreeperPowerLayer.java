package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import tech.thatgravyboat.creeperoverhaul.client.RenderTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.utils.PlatformUtils;

public class CreeperPowerLayer<E extends BaseCreeper> extends GeoLayerRenderer<E> {

    public CreeperPowerLayer(IGeoRenderer<E> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLightIn, E creeper, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!creeper.isPowered() || PlatformUtils.shouldHidePowerLayer()) return;

        float f = (float)creeper.tickCount + partialTicks;

        GeoModel chargedModel = this.getEntityModel().getModel(creeper.type.model().apply(creeper));
        RenderType renderType = RenderTypes.getSwirl(creeper.type.chargedTexture().apply(creeper), f * 0.005F % 1F,  f * 0.005F % 1F);
        VertexConsumer armorConsumer = buffer.getBuffer(renderType);

        getRenderer().render(chargedModel, creeper, partialTicks,
                null, stack, null, armorConsumer,
                packedLightIn, OverlayTexture.NO_OVERLAY,
                1f, 1f, 1f, 1f);
    }
}
