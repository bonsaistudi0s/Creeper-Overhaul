package tech.thatgravyboat.creeperoverhaul.integration;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;


public class Origins {
    private static Map<Player, Boolean> scaresCreepersCache = new HashMap<>();

    // Ideally, this and clearCache() would be hooked up to commands
    public static void cacheInvalidate(Player player) {
        scaresCreepersCache.remove(player);
    }

    public static void clearCache() {
        scaresCreepersCache.clear();
    }

    public static boolean creepersAvoid(Player player) {
        if (scaresCreepersCache.containsKey(player)) {
            return scaresCreepersCache.get(player);
        }

        return lookUpCreepersAvoid(player, scaresCreepersCache);
    }

    @ExpectPlatform
    public static boolean lookUpCreepersAvoid(Player player, Map<Player, Boolean> originsCache) {
        throw new AssertionError();
    }
}


