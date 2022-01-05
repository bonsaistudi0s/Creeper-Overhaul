package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.Events;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ReplacedCreeperRenderer extends GeoReplacedEntityRenderer<ReplacedCreeper> {

    public ReplacedCreeperRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ReplacedCreeperModel(), new ReplacedCreeper());
        GeoReplacedEntityRenderer.registerReplacedEntity(ReplacedCreeper.class, this);
        addLayer(new ReplacedCreeperGlowLayer(this));
        addLayer(new ReplacedCreeperPowerLayer(this));
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        boolean shouldRender;
        switch (bone.name) {
            case "Santa" -> shouldRender = Creepers.EVENT.equals(Events.CHRISTMAS);
            case "Witch" -> shouldRender = Creepers.EVENT.equals(Events.HALLOWEEN);
            case "Stpat" -> shouldRender = Creepers.EVENT.equals(Events.ST_PATRICKS_DAY);
            default -> shouldRender = true;
        }
        if (shouldRender) super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected void preRenderCallback(LivingEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
        CreeperEntity creeper = (CreeperEntity) entity;
        float f = creeper.getClientFuseTime(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = (f * f) * (f * f);
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        matrixStackIn.scale(f2, f3, f2);
    }

    @Override
    protected float getOverlayProgress(LivingEntity livingEntityIn, float partialTicks) {
        CreeperEntity creeper = (CreeperEntity) livingEntityIn;
        float f = creeper.getClientFuseTime(partialTicks);
        return (int) (f * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(f, 0.5F, 1.0F);
    }

    @Override
    public RenderLayer getRenderType(Object animatable, float partialTicks, MatrixStack stack, @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation);
    }
}