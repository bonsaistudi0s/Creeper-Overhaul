package tech.thatgravyboat.creeperoverhaul.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import com.teamresourceful.resourcefulconfig.web.annotations.WebInfo;

@Category(
    id = "client",
    translation = "Client Options"
)
@WebInfo(icon = "settings-2")
public final class ClientConfig {

    @ConfigEntry(
        type = EntryType.BOOLEAN,
        id = "replaceDefaultCreeper",
        translation = "Replace Default Creeper"
    )
    @Comment("Change the Vanilla Creeper to a new and improved texture with better animations.")
    public static boolean replaceDefaultCreeper = true;


}
