package tech.thatgravyboat.creeperoverhaul.fabric;

import net.fabricmc.api.ClientModInitializer;
import tech.thatgravyboat.creeperoverhaul.client.CreepersClient;
import tech.thatgravyboat.creeperoverhaul.client.fabric.ClientConfig;

import java.io.IOException;

public class CreepersClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        try {
            ClientConfig.loadConfig();
        } catch (Exception e) {
            System.out.println("[Creeper Overhaul] Failed to load Client Config.");
        }
        CreepersClient.registerRenderers();
    }
}
