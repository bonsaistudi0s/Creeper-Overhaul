package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.creeperoverhaul.client.RenderTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.utils.PlatformUtils;

public class CreeperPowerLayer<E extends BaseCreeper> extends GeoRenderLayer<E> {

    public CreeperPowerLayer(GeoRenderer<E> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack stack, E creeper, BakedGeoModel bakedModel, RenderType type, MultiBufferSource buffers, VertexConsumer buffer, float partialTicks, int packedLight, int packedOverlay) {
        if (!creeper.isPowered() || PlatformUtils.shouldHidePowerLayer()) return;

        float f = (float)creeper.tickCount + partialTicks;

        BakedGeoModel chargedModel = this.getGeoModel().getBakedModel(creeper.type.model());
        RenderType renderType = RenderTypes.getSwirl(creeper.type.chargedTexture(), f * 0.005F % 1F,  f * 0.005F % 1F);
        VertexConsumer armorConsumer = buffers.getBuffer(renderType);

        getRenderer().reRender(chargedModel, stack, buffers, creeper,
                null, armorConsumer, partialTicks,
                packedLight, OverlayTexture.NO_OVERLAY,
                1f, 1f, 1f, f);
    }
}
