package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;

public class ReplacedCreeperModel<E extends ReplacedCreeper> extends AnimatedGeoModel<E> {

    private static final Identifier MODEL = new Identifier(Creepers.MODID, "geo/plains.geo.json");
    private static final Identifier ANIMATION = new Identifier(Creepers.MODID, "animations/creeper.animation.json");

    private static final Identifier NORMAL = new Identifier(Creepers.MODID, "textures/entity/plains/plains_creeper.png");
    private static final Identifier CHRISTMAS = new Identifier(Creepers.MODID, "textures/entity/plains/plains_creeper_christmas.png");
    private static final Identifier HALLOWEEN = new Identifier(Creepers.MODID, "textures/entity/plains/plains_creeper_halloween.png");
    private static final Identifier ST_PATRICKS = new Identifier(Creepers.MODID, "textures/entity/plains/plains_creeper_stpatricksday.png");

    @Override
    public Identifier getModelLocation(ReplacedCreeper object) {
        return MODEL;
    }

    @Override
    public Identifier getTextureLocation(ReplacedCreeper object) {
        return switch (Creepers.EVENT) {
            case CHRISTMAS -> CHRISTMAS;
            case HALLOWEEN -> HALLOWEEN;
            case ST_PATRICKS_DAY -> ST_PATRICKS;
            case NONE -> NORMAL;
        };
    }

    @Override
    public Identifier getAnimationFileLocation(ReplacedCreeper animatable) {
        return ANIMATION;
    }
}
