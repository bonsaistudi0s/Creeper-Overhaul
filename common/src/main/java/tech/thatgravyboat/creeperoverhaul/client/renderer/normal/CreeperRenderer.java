package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

import static net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords;

public class CreeperRenderer<E extends BaseCreeper> extends GeoEntityRenderer<E> {

    public CreeperRenderer(EntityRendererProvider.Context renderManager, AnimatedGeoModel<E> modelProvider) {
        super(renderManager, modelProvider);
        addLayer(new CreeperGlowLayer<>(this));
        addLayer(new CreeperPowerLayer<>(this));
    }

    @Override
    public void render(@NotNull E creeper, float entityYaw, float partialTicks, PoseStack stack, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        float f = creeper.getSwelling(partialTicks);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        stack.scale(f2, f3, f2);
        super.render(creeper, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull E entity) {
        return this.modelProvider.getTextureResource(entity);
    }

    @Override
    public void render(GeoModel model, E creeper, float partialTicks, RenderType type, PoseStack matrixStackIn, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        float f = creeper.getSwelling(partialTicks);
        f = (int) (f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
        super.render(model, creeper, partialTicks,
                type, matrixStackIn, renderTypeBuffer, vertexBuilder,
                packedLightIn, getOverlayCoords(creeper, f),
                red, green, blue, alpha);
    }

    @Override
    public RenderType getRenderType(E animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(textureLocation);
    }
}
