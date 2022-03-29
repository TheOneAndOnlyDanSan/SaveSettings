package net.theoneandonlydansan.autotextureloader;

import net.fabricmc.api.ModInitializer;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SomehowEverything implements ModInitializer {
	@Override
	public void onInitialize() {
		try {
			Path MinecraftDir = Path.of(System.getenv("APPDATA") + "/.minecraft");
			File config = new File(MinecraftDir + "/config/auto-texture-loader.txt");
			if(!config.exists()) { config.createNewFile(); }
			List<String> lines = Files.readAllLines(Paths.get(config.toString()));
			Files.write(Path.of(MinecraftDir + "/options.txt"), lines);
		} catch (Exception e) {}
	}
}
