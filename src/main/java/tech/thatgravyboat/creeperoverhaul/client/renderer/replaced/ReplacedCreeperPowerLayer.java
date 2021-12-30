package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Creeper;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.client.init.ClientInit;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ReplacedCreeperPowerLayer extends GeoLayerRenderer {

    private static final ResourceLocation PLAINS_CHARGED_TEXTURE = new ResourceLocation(Creepers.MODID, "textures/entity/armor/creeper_armor.png");

    public ReplacedCreeperPowerLayer(IGeoRenderer<ReplacedCreeper> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int packedLightIn, Entity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(Creepers.isUsingOptifine()) return;
        if (entity instanceof Creeper creeper && creeper.isPowered()) {

            float f = (float)creeper.tickCount + partialTicks;

            GeoModel chargedModel = this.getEntityModel().getModel(this.getEntityModel().getModelLocation(null));
            RenderType renderType = ClientInit.RenderTypes.getSwirl(PLAINS_CHARGED_TEXTURE, f * 0.005F % 1F,  f * 0.005F % 1F);
            VertexConsumer armorConsumer = buffer.getBuffer(renderType);

            getRenderer().render(chargedModel, creeper, partialTicks,
                    null, stack, null, armorConsumer,
                    packedLightIn, OverlayTexture.NO_OVERLAY,
                    1f, 1f, 1f, 1f);
        }
    }
}
