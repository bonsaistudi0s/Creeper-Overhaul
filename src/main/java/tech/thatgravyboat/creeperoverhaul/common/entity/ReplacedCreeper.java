package tech.thatgravyboat.creeperoverhaul.common.entity;

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.core.manager.InstancedAnimationFactory;

import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.LOOP;

public class ReplacedCreeper implements IAnimatable {

    private final AnimationFactory factory = new InstancedAnimationFactory(this);

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(
                new AnimationController<>(this, "controller", 0, this::predicate)
        );
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        //event.isMoving() is acting as if it were name "isNotMoving()"
        if (!event.isMoving()) {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.creeper.walk", LOOP)
            );
        } else {
            event.getController().setAnimation(
                    new AnimationBuilder().addAnimation("animation.creeper.idle", LOOP)
            );
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
