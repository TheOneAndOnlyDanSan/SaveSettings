package net.theoneandonlydansan.savesettings;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class SaveSettings implements ModInitializer {
	static Path MinecraftDir = Path.of(System.getenv("APPDATA") + "/.minecraft");
	static File config = new File(MinecraftDir + "/config/Save-Settings.txt");
	static File options = new File(MinecraftDir + "/options.txt");

	@Override
	public void onInitialize() {
		try {
			if (!config.exists()) {
				config.createNewFile();
				Files.write(config.toPath(), Files.readAllLines(options.toPath()));
			}
			Files.write(options.toPath(), Files.readAllLines(config.toPath()));
			register();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void register() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
			ClientCommandManager.literal("savesettings").executes(context -> {
				try {
					Files.write(config.toPath(), Files.readAllLines(options.toPath()));
					MinecraftClient.getInstance().player.sendMessage(Text.literal("settings saved"), true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 1;
			})
		));
	}
}