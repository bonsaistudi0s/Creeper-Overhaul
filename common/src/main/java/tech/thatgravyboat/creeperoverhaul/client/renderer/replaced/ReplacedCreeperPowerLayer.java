package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.client.RenderTypes;
import tech.thatgravyboat.creeperoverhaul.common.utils.PlatformUtils;

public class ReplacedCreeperPowerLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {

    private static final ResourceLocation PLAINS_CHARGED_TEXTURE = new ResourceLocation(Creepers.MODID, "textures/entity/armor/creeper_armor.png");

    public ReplacedCreeperPowerLayer(GeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack stack, T entity, BakedGeoModel bakedModel, RenderType type, MultiBufferSource buffers, VertexConsumer buffer, float partialTicks, int packedLight, int packedOverlay) {
        if(PlatformUtils.shouldHidePowerLayer()) return;
        if (entity instanceof Creeper creeper && creeper.isPowered()) {

            float f = (float)creeper.tickCount + partialTicks;

            BakedGeoModel chargedModel = this.getGeoModel().getBakedModel(this.getGeoModel().getModelResource(null));
            RenderType renderType = RenderTypes.getSwirl(PLAINS_CHARGED_TEXTURE, f * 0.005F % 1F,  f * 0.005F % 1F);
            VertexConsumer armorConsumer = buffers.getBuffer(renderType);

            getRenderer().reRender(chargedModel, stack, buffers, entity,
                    null, armorConsumer, partialTicks,
                    packedLight, OverlayTexture.NO_OVERLAY,
                    1f, 1f, 1f, f);
        }
    }
}
