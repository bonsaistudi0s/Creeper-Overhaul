package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;

import java.util.List;

public class CreeperModel<E extends BaseCreeper> extends AnimatedGeoModel<E> {

    private final CreeperType type;

    public CreeperModel(CreeperType type) {
        this.type = type;
    }

    @Override
    public ResourceLocation getModelResource(E entity) {
        return type.isShearable() && entity.isSheared() && type.shearedModel() != null ? type.shearedModel().apply(entity) : type.model().apply(entity);
    }

    @Override
    public ResourceLocation getTextureResource(E entity) {
        return type.texture().apply(entity);
    }

    @Override
    public ResourceLocation getAnimationResource(E entity) {
        return type.animation().apply(entity);
    }

    @Override
    public void setCustomAnimations(E animatable, int instanceId, AnimationEvent event) {
        super.setCustomAnimations(animatable, instanceId, event);

        List<EntityModelData> extraDataOfType = event.getExtraDataOfType(EntityModelData.class);
        var head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            head.setRotationY(extraDataOfType.get(0).netHeadYaw * ((float)Math.PI / 180F));
        }
    }
}
