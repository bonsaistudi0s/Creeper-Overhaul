package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.Events;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ReplacedCreeperRenderer extends GeoReplacedEntityRenderer<ReplacedCreeper> {

    public ReplacedCreeperRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedCreeperModel(), new ReplacedCreeper());
        addLayer(new ReplacedCreeperGlowLayer(this));
        addLayer(new ReplacedCreeperPowerLayer(this));
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
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
    protected void preRenderCallback(LivingEntity entity, PoseStack matrixStackIn, float partialTickTime) {
        Creeper creeper = (Creeper) entity;
        float f = creeper.getSwelling(partialTickTime);
        float f1 = 1.0F + Mth.sin(f * 100.0F) * f * 0.01F;
        f = Mth.clamp(f, 0.0F, 1.0F);
        f = (f * f) * (f * f);
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        matrixStackIn.scale(f2, f3, f2);
    }

    @Override
    protected float getOverlayProgress(LivingEntity livingEntityIn, float partialTicks) {
        Creeper creeper = (Creeper) livingEntityIn;
        float f = creeper.getSwelling(partialTicks);
        return (int) (f * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(f, 0.5F, 1.0F);
    }

    @Override
    public RenderType getRenderType(Object animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(textureLocation);
    }
}