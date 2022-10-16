package tech.thatgravyboat.creeperoverhaul.integration.fabric;

import io.github.apace100.origins.component.PlayerOriginComponent;
import io.github.apace100.origins.origin.Origin;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public class OriginsImpl {

    public static boolean lookUpCreepersAvoid(Player player, Map<Player, Boolean> originsCache) {
        if (!FabricLoader.getInstance().isModLoaded("origins")) {
            originsCache.put(player, Boolean.FALSE);
            return false;
        }

        PlayerOriginComponent originComponent = new PlayerOriginComponent(player);
        if (originComponent == null) {
            return false;
        }

        String scare_creepers = "[origins:scare_creepers]";

        for(Origin origin : originComponent.getOrigins().values()) {
            String originAsString = origin.toString();
            if (originAsString.contains(scare_creepers)) {
                originsCache.put(player, Boolean.TRUE);
                return true;
            }
        }
        originsCache.put(player, Boolean.FALSE);
        return false;
    }
}
