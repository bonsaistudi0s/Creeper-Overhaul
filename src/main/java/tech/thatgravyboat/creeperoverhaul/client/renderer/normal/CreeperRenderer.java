package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

public class CreeperRenderer<E extends BaseCreeper> extends GeoEntityRenderer<E> {

    public CreeperRenderer(EntityRendererFactory.Context renderManager, AnimatedGeoModel<E> modelProvider) {
        super(renderManager, modelProvider);
        addLayer(new CreeperGlowLayer<>(this));
        addLayer(new CreeperPowerLayer<>(this));
    }

    @Override
    public void render(@NotNull E creeper, float entityYaw, float partialTicks, MatrixStack stack, @NotNull VertexConsumerProvider bufferIn, int packedLightIn) {
        float f = creeper.getSwelling(partialTicks);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        stack.scale(f2, f3, f2);
        super.render(creeper, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Override
    public void render(GeoModel model, E creeper, float partialTicks, RenderLayer type, MatrixStack matrixStackIn, @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        float f = creeper.getSwelling(partialTicks);
        f = (int) (f * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(f, 0.5F, 1.0F);
        RenderSystem.enableBlend();
        super.render(model, creeper, partialTicks,
                type, matrixStackIn, renderTypeBuffer, vertexBuilder,
                packedLightIn, OverlayTexture.getUv(f, creeper.hurtTime > 0 || creeper.deathTime > 0),
                red, green, blue, alpha);
    }

    @Override
    public RenderLayer getRenderType(E animatable, float partialTicks, MatrixStack stack, @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation);
    }
}
