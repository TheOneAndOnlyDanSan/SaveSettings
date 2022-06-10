package net.theoneandonlydansan.savesettings;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveSettings implements ModInitializer {
	static Path MinecraftDir = Path.of(System.getenv("APPDATA") + "/.minecraft");
	static File config = new File(MinecraftDir + "/config/Save-Settings.txt");
	static File options = new File(MinecraftDir + "/options.txt");

	@Override
	public void onInitialize() {
		try {
			Files.write(options.toPath(), Files.readAllLines(config.toPath()));
			register();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveSettings()  {
		try {
			if (!config.exists()) {
				config.createNewFile();
			}
			Files.write(config.toPath(), Files.readAllLines(options.toPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void register() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
			ClientCommandManager.literal("savesettings").executes(context -> {
				saveSettings();
				MinecraftClient.getInstance().player.sendMessage(Text.literal("settings saved"), true);
				return 1;
			})
		));
	}
}