package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.client.RenderTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ReplacedCreeperGlowLayer extends GeoLayerRenderer {

    private static final ResourceLocation PLAINS_GLOW_TEXTURE = new ResourceLocation(Creepers.MODID, "textures/entity/plains/plains_creeper_glow.png");

    public ReplacedCreeperGlowLayer(IGeoRenderer<ReplacedCreeper> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLightIn, Entity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof Creeper creeper) {
            float f = creeper.getSwelling(partialTicks);

            if (f > 0f || creeper.isPowered()) {
                if (creeper.isPowered()) f = 1f;

                GeoModel normalModel = this.getEntityModel().getModel(this.getEntityModel().getModelResource(null));
                VertexConsumer glowConsumer = buffer.getBuffer(RenderTypes.getTransparentEyes(PLAINS_GLOW_TEXTURE));

                getRenderer().render(normalModel, creeper, partialTicks,
                        null, stack, null, glowConsumer,
                        packedLightIn, OverlayTexture.NO_OVERLAY,
                        1f, 1f, 1f, f);
            }
        }
    }
}
