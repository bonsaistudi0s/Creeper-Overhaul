package tech.thatgravyboat.creeperoverhaul.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import tech.thatgravyboat.creeperoverhaul.api.CreeperPlugin;
import tech.thatgravyboat.creeperoverhaul.api.PluginRegistry;

import java.util.List;

public final class PluginLoader {

    public static void load() {
        List<CreeperPlugin> plugins = FabricLoader.getInstance().getEntrypointContainers("creeperoverhaul", CreeperPlugin.class)
                .stream()
                .map(EntrypointContainer::getEntrypoint)
                .toList();
        PluginRegistry.getInstance().registerPlugins(plugins);
    }
}
