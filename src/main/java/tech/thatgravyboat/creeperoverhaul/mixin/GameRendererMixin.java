package tech.thatgravyboat.creeperoverhaul.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.thatgravyboat.creeperoverhaul.client.init.ClientInit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow @Final private Map<String, Shader> shaders;

    @Inject(method = "loadShaders", at = @At("TAIL"))
    private void onShadersLoaded(ResourceManager manager, CallbackInfo ci) {
        List<Pair<Shader, Consumer<Shader>>> shadersToLoad = new ArrayList<>();

        try {
            shadersToLoad.add(Pair.of(new Shader(manager, "creeperoverhaul_rendertype_energy_swirl", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL), (shader) -> {
                ClientInit.energySwirlShader = shader;
            }));
        }catch (Exception e) {
            shadersToLoad.forEach(pair -> pair.getFirst().close());
            throw new RuntimeException("Shaders could not be reloaded", e);
        }

        shadersToLoad.forEach(pair -> {
            Shader shader = pair.getFirst();
            this.shaders.put(shader.getName(), shader);
            pair.getSecond().accept(shader);
        });
    }
}
