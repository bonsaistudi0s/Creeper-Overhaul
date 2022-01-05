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
import tech.thatgravyboat.creeperoverhaul.client.init.ClientInit;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ReplacedCreeperPowerLayer extends GeoLayerRenderer {

    private static final Identifier PLAINS_CHARGED_TEXTURE = new Identifier(Creepers.MODID, "textures/entity/armor/creeper_armor.png");

    public ReplacedCreeperPowerLayer(IGeoRenderer<ReplacedCreeper> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack stack, VertexConsumerProvider buffer, int packedLightIn, Entity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof CreeperEntity creeper && creeper.shouldRenderOverlay()) {

            float f = (float)creeper.age + partialTicks;

            GeoModel chargedModel = this.getEntityModel().getModel(this.getEntityModel().getModelLocation(null));
            RenderLayer renderType = ClientInit.RenderTypes.getSwirl(PLAINS_CHARGED_TEXTURE, f * 0.005F % 1F,  f * 0.005F % 1F);
            VertexConsumer armorConsumer = buffer.getBuffer(renderType);

            getRenderer().render(chargedModel, creeper, partialTicks,
                    null, stack, null, armorConsumer,
                    packedLightIn, OverlayTexture.DEFAULT_UV,
                    1f, 1f, 1f, 1f);
        }
    }
}
