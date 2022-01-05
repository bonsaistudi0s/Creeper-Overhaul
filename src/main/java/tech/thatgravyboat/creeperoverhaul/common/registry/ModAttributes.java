package tech.thatgravyboat.creeperoverhaul.common.registry;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import tech.thatgravyboat.creeperoverhaul.Creepers;

public class ModAttributes {

    public static final EntityAttribute REACH_DISTANCE = new ClampedEntityAttribute("generic.reach_distance", 5.0D, 0.0D, 1024.0D).setTracked(true);
    public static final EntityAttribute SWIM_SPEED = new ClampedEntityAttribute("generic.swim_speed", 1.0D, 0.0D, 1024.0D).setTracked(true);

    public static void register() {
        Registry.register(Registry.ATTRIBUTE, new Identifier(Creepers.MODID, "reach_distance"), REACH_DISTANCE);
        Registry.register(Registry.ATTRIBUTE, new Identifier(Creepers.MODID, "swim_speed"), SWIM_SPEED);
    }
}
