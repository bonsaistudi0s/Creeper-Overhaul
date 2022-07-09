package tech.thatgravyboat.creeperoverhaul.fabric;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class Config {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final JsonObject DEFAULT_CONFIG = Util.make(new JsonObject(), json -> {
        json.addProperty("//replaceDefaultCreeper", "Change the Vanilla Creeper to a new and improved texture with better animations.");
        json.addProperty("replaceDefaultCreeper", true);
        json.addProperty("//destroyBlocks", "Change the Creeper Overhaul creepers to destroy blocks or not.");
        json.addProperty("destroyBlocks", true);
    });

    public static boolean replaceDefaultCreeper = true;
    public static boolean destroyBlocks = true;

    public static void loadConfig() throws IOException {
        Path cfgPath = FabricLoader.getInstance().getConfigDir();
        File cfgFile = new File(cfgPath.toFile(), "creeperoverhaul.json");
        if (!cfgFile.exists()) {
            cfgPath.toFile().mkdirs();
            FileUtils.write(cfgFile, GSON.toJson(DEFAULT_CONFIG), StandardCharsets.UTF_8);
        } else {
            JsonObject cfgJson = GSON.fromJson(FileUtils.readFileToString(cfgFile, StandardCharsets.UTF_8), JsonObject.class);
            if (cfgJson != null) {
                replaceDefaultCreeper = !cfgJson.has("replaceDefaultCreeper") || cfgJson.get("replaceDefaultCreeper").getAsBoolean();
                destroyBlocks = !cfgJson.has("destroyBlocks") || cfgJson.get("destroyBlocks").getAsBoolean();
            }
        }
    }
}
