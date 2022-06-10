package net.theoneandonlydansan.savesettings;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveSettings implements ModInitializer {
	static Path MinecraftDir = Path.of(System.getenv("APPDATA") + "/.minecraft");
	static File config = new File(MinecraftDir + "/config/Save-Settings.txt");
	static File options = new File(MinecraftDir + "/options.txt");

	@Override
	public void onInitialize() {
		writeToFile();
		register();
	}

	public static void writeToFile() {
		try {
			if (!config.exists()) {
				config.createNewFile();
				Files.write(config.toPath(), Files.readAllLines(options.toPath()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void register() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(
				ClientCommandManager.literal("savesettings").executes(context -> {
					writeToFile();
					return 1;
				})
			);
		});
	}
}