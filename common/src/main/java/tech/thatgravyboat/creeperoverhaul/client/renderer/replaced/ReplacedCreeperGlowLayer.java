package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.client.RenderTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;

public class ReplacedCreeperGlowLayer extends GeoRenderLayer<ReplacedCreeper> {

    private static final ResourceLocation PLAINS_GLOW_TEXTURE = new ResourceLocation(Creepers.MODID, "textures/entity/plains/plains_creeper_glow.png");

    private final ReplacedCreeperRenderer renderer;

    public ReplacedCreeperGlowLayer(ReplacedCreeperRenderer renderer) {
        super(renderer);
        this.renderer = renderer;
    }

    @Override
    public void render(PoseStack poseStack, ReplacedCreeper animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        Creeper creeper = this.renderer.getCurrentEntity();
        float f = creeper.getSwelling(partialTick);

        if (f > 0f || creeper.isPowered()) {
            if (creeper.isPowered()) f = 1f;

            VertexConsumer glowConsumer = bufferSource.getBuffer(RenderTypes.getTransparentEyes(PLAINS_GLOW_TEXTURE));

            getRenderer().reRender(
                    getDefaultBakedModel(animatable), poseStack,
                    bufferSource, animatable,
                    RenderTypes.getTransparentEyes(PLAINS_GLOW_TEXTURE), glowConsumer,
                    partialTick,
                    packedLight, OverlayTexture.NO_OVERLAY,
                    1f, 1f, 1f, f
            );
        }
    }
}
