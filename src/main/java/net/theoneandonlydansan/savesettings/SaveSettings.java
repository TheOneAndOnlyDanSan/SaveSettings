package net.theoneandonlydansan.savesettings;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.nio.file.Files;

public class SaveSettings implements ModInitializer {
	static File MinecraftDir = new File(System.getenv("APPDATA") + "/.minecraft");
	static File configFolderDir = new File(MinecraftDir + "/config");
	static File saveFolderDir = new File(MinecraftDir + "/SaveSettings");
	static File optionsSave = new File(saveFolderDir + "options.txt");
	static File options = new File(MinecraftDir + "/options.txt");

	@Override
	public void onInitialize() {
		try {
			if(!configFolderDir.exists()) {
				configFolderDir.mkdir();
			}
			if (!optionsSave.exists()) {
				optionsSave.createNewFile();
				saveSettings();
			}
			register();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void register() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(
				ClientCommandManager.literal("SaveSettings").executes(context -> {
					saveSettings();
					MinecraftClient.getInstance().player.sendMessage(Text.literal("settings saved"), true);
					return 1;
				})
		));
	}

	public static void saveSettings() {
		try {
			Files.write(optionsSave.toPath(), Files.readAllLines(options.toPath()));
			FileUtils.copyDirectory(configFolderDir, saveFolderDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}