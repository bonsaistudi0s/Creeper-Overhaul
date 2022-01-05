package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ReplacedCreeperGlowLayer extends GeoLayerRenderer {

    private static final Identifier PLAINS_GLOW_TEXTURE = new Identifier(Creepers.MODID, "textures/entity/plains/plains_creeper_glow.png");

    public ReplacedCreeperGlowLayer(IGeoRenderer<ReplacedCreeper> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack stack, VertexConsumerProvider buffer, int packedLightIn, Entity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof CreeperEntity creeper && creeper.shouldRenderOverlay()) {

            GeoModel normalModel = this.getEntityModel().getModel(this.getEntityModel().getModelLocation(null));
            VertexConsumer glowConsumer = buffer.getBuffer(RenderLayer.getEyes(PLAINS_GLOW_TEXTURE));

            getRenderer().render(normalModel, creeper, partialTicks,
                    null, stack, null, glowConsumer,
                    packedLightIn, OverlayTexture.DEFAULT_UV,
                    1f, 1f, 1f, 1f);
        }
    }
}
