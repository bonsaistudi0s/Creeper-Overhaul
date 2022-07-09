package tech.thatgravyboat.creeperoverhaul.common.registry.forge;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tech.thatgravyboat.creeperoverhaul.Creepers;

import java.util.function.Supplier;

public class ModSoundsImpl {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Creepers.MODID);

    public static Supplier<SoundEvent> registerSound(String id, Supplier<SoundEvent> sound) {
        return SOUNDS.register(id, sound);
    }
}
