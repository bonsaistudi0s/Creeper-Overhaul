package tech.thatgravyboat.creeperoverhaul.common.registry.fabric;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import tech.thatgravyboat.creeperoverhaul.Creepers;

import java.util.function.Supplier;

public class ModSoundsImpl {
    public static Supplier<SoundEvent> registerSound(String id, Supplier<SoundEvent> sound) {
        var output = Registry.register(Registry.SOUND_EVENT, new ResourceLocation(Creepers.MODID, id), sound.get());
        return () -> output;
    }
}
