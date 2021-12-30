package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.Events;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;

public class ReplacedCreeperModel<E extends ReplacedCreeper> extends AnimatedGeoModel<E> {

    private static final ResourceLocation MODEL = new ResourceLocation(Creepers.MODID, "geo/plains.geo.json");
    private static final ResourceLocation ANIMATION = new ResourceLocation(Creepers.MODID, "animations/creeper.animation.json");

    private static final ResourceLocation NORMAL = new ResourceLocation(Creepers.MODID, "textures/entity/plains/plains_creeper.png");
    private static final ResourceLocation CHRISTMAS = new ResourceLocation(Creepers.MODID, "textures/entity/plains/plains_creeper_christmas.png");
    private static final ResourceLocation HALLOWEEN = new ResourceLocation(Creepers.MODID, "textures/entity/plains/plains_creeper_halloween.png");
    private static final ResourceLocation ST_PATRICKS = new ResourceLocation(Creepers.MODID, "textures/entity/plains/plains_creeper_stpatricksday.png");

    @Override
    public ResourceLocation getModelLocation(ReplacedCreeper object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureLocation(ReplacedCreeper object) {
        return switch (Creepers.EVENT) {
            case CHRISTMAS -> CHRISTMAS;
            case HALLOWEEN -> HALLOWEEN;
            case ST_PATRICKS_DAY -> ST_PATRICKS;
            case NONE -> NORMAL;
        };
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ReplacedCreeper animatable) {
        return ANIMATION;
    }
}
