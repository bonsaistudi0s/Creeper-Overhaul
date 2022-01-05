package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;

public class CreeperModel<E extends BaseCreeper> extends AnimatedGeoModel<E> {

    private final CreeperType type;

    public CreeperModel(CreeperType type) {
        this.type = type;
    }

    @Override
    public Identifier getModelLocation(E entity) {
        return type.shearable() && entity.isSheared() && type.shearedModel() != null ? type.shearedModel() : type.model();
    }

    @Override
    public Identifier getTextureLocation(E entity) {
        return type.texture();
    }

    @Override
    public Identifier getAnimationFileLocation(E entity) {
        return type.animation();
    }
}
