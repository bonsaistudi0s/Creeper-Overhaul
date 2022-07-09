package tech.thatgravyboat.creeperoverhaul.fabric;

import net.fabricmc.api.ClientModInitializer;
import tech.thatgravyboat.creeperoverhaul.client.CreepersClient;

public class CreepersClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CreepersClient.registerRenderers();
    }
}
