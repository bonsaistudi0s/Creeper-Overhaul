package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;
import tech.thatgravyboat.creeperoverhaul.common.utils.Events;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ReplacedCreeperRenderer extends GeoReplacedEntityRenderer<Creeper, ReplacedCreeper> {

    public ReplacedCreeperRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedCreeperModel(), new ReplacedCreeper());
        this.addRenderLayer(new ReplacedCreeperGlowLayer<>(this));
        this.addRenderLayer(new ReplacedCreeperPowerLayer<>(this));
    }

    @Override
    public void renderRecursively(PoseStack poseStack, ReplacedCreeper animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        boolean shouldRender;
        switch (bone.getName()) {
            case "Santa" -> shouldRender = Creepers.EVENT.equals(Events.CHRISTMAS);
            case "Witch" -> shouldRender = Creepers.EVENT.equals(Events.HALLOWEEN);
            case "Stpat" -> shouldRender = Creepers.EVENT.equals(Events.ST_PATRICKS_DAY);
            default -> shouldRender = true;
        }
        if (shouldRender) super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void preRender(PoseStack poseStack, ReplacedCreeper animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        float swellFactor = this.currentEntity.getSwelling(partialTick);
        float swellMod = 1 + Mth.sin(swellFactor * 100f) * swellFactor * 0.01f;
        swellFactor = Mth.clamp(swellFactor, 0.0F, 1.0F);
        swellFactor = (swellFactor * swellFactor) * (swellFactor * swellFactor);
        float horizontalSwell = (1 + swellFactor * 0.4f) * swellMod;
        float verticalSwell = (1 + swellFactor * 0.1f) / swellMod;

        poseStack.scale(horizontalSwell, verticalSwell, horizontalSwell);
    }

    @Override
    public int getPackedOverlay(ReplacedCreeper animatable, float u) {
        return super.getPackedOverlay(animatable, getSwellOverlay(this.currentEntity, u));
    }

    protected float getSwellOverlay(Creeper entity, float u) {
        float swell = entity.getSwelling(u);

        return (int) (swell * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(swell, 0.5F, 1.0F);
    }

    @Override
    public RenderType getRenderType(ReplacedCreeper animatable, ResourceLocation textureLocation, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(textureLocation);
    }
}
