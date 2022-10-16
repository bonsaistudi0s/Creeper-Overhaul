package tech.thatgravyboat.creeperoverhaul.integration.forge;

import io.github.edwinmindcraft.origins.api.OriginsAPI;
import io.github.edwinmindcraft.origins.api.capabilities.IOriginContainer;
import io.github.edwinmindcraft.origins.api.origin.Origin;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;

import java.util.Map;


public class OriginsImpl {

    public static boolean lookUpCreepersAvoid(Player player, Map<Player, Boolean> originsCache) {
        if (!ModList.get().isLoaded("origins")) {
            originsCache.put(player, Boolean.FALSE);
            return false;
        }

        IOriginContainer originContainer = IOriginContainer.get(player).orElse(null);
        if (originContainer == null) {
            return false;
        }

        String scare_creepers = "[origins:scare_creepers]";

        Registry<Origin> originRegistry = OriginsAPI.getOriginsRegistry();
        for(ResourceKey<Origin> originKey : originContainer.getOrigins().values()) {
            Origin origin = originRegistry.get(originKey);
            if (origin == null) {
                continue;
            }
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



