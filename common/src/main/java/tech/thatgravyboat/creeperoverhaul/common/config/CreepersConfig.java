package tech.thatgravyboat.creeperoverhaul.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.InlineCategory;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import com.teamresourceful.resourcefulconfig.web.annotations.Link;
import com.teamresourceful.resourcefulconfig.web.annotations.WebInfo;

@Config("creeperoverhaul")
@WebInfo(
        icon = "creeper",
        title = "Creeper Overhaul",
        description = "Biome specific creepers",
        color = "#7BB252",
        links = {
                @Link(
                        value = "https://modrinth.com/mod/creeper-overhaul",
                        icon = "modrinth",
                        title = "Modrinth"
                ),
                @Link(
                        value = "https://curseforge.com/minecraft/mc-mods/creeper-overhaul",
                        icon = "curseforge",
                        title = "Curseforge"
                ),
                @Link(
                        value = "https://github.com/bonsaistudi0s/Creeper-Overhaul",
                        icon = "github",
                        title = "Github"
                )
        }
)
public final class CreepersConfig {

    @ConfigEntry(
            type = EntryType.BOOLEAN,
            id = "destroyBlocks",
            translation = "Destroy Blocks")
    @Comment("Changes the Creeper Overhaul creepers to destroy blocks or not.")
    public static boolean destroyBlocks = true;

    @InlineCategory
    public static ClientConfig client;

    @InlineCategory
    public static SpawningConfig spawning;
}
