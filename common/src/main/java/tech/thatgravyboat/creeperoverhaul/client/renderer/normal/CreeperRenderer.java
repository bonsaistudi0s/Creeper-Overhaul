package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

import static net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords;

public class CreeperRenderer<E extends BaseCreeper> extends GeoEntityRenderer<E> {

    public CreeperRenderer(EntityRendererProvider.Context renderManager, GeoModel<E> modelProvider) {
        super(renderManager, modelProvider);
        this.addRenderLayer(new CreeperGlowLayer<>(this));
        this.addRenderLayer(new CreeperPowerLayer<>(this));
    }

    @Override
    public void render(@NotNull E creeper, float entityYaw, float partialTicks, PoseStack stack, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        float swellFactor = creeper.getSwelling(partialTicks);
        float swellMod = 1 + Mth.sin(swellFactor * 100f) * swellFactor * 0.01f;
        swellFactor = Mth.clamp(swellFactor, 0.0F, 1.0F);
        swellFactor = (swellFactor * swellFactor) * (swellFactor * swellFactor);
        float horizontalSwell = (1 + swellFactor * 0.4f) * swellMod;
        float verticalSwell = (1 + swellFactor * 0.1f) / swellMod;
        stack.scale(horizontalSwell, verticalSwell, horizontalSwell);
        super.render(creeper, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull E entity) {
        return this.model.getTextureResource(entity);
    }

    @Override
    public void reRender(BakedGeoModel model, PoseStack poseStack, MultiBufferSource bufferSource, E creeper, RenderType renderType, VertexConsumer buffer, float partialTicks, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        float f = creeper.getSwelling(partialTicks);
        f = (int) (f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
        super.reRender(model, poseStack, bufferSource, creeper, renderType, buffer, partialTicks, packedLight, getOverlayCoords(creeper, f), red, green, blue, alpha);
    }

    @Override
    public RenderType getRenderType(E animatable, ResourceLocation textureLocation, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(textureLocation);
    }
}
