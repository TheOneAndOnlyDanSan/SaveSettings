package net.theoneandonlydansan.savesettings;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.text.LiteralText;


public class SomehowEverything implements ModInitializer {
	static Path MinecraftDir = Path.of(System.getenv("APPDATA") + "/.minecraft");
	static File config = new File(MinecraftDir + "/config/Save-Settings.txt");

	@Override
	public void onInitialize() {

		ClientCommandManager.DISPATCHER.register(
				ClientCommandManager.literal("savesettings").executes(context -> {
					try {Files.write(config.toPath(), Files.readAllLines(Path.of(MinecraftDir + "/options.txt")));}catch(Exception e){}
					MinecraftClient.getInstance().player.sendMessage(new LiteralText("settings saved"), true);
					return 1;
				})
		);
		try {
			if(!config.exists()) {
				config.createNewFile();
				Files.write(config.toPath(), Files.readAllLines(Path.of(MinecraftDir + "/options.txt")));
			}
			Files.write(Path.of(MinecraftDir + "/options.txt"), Files.readAllLines(Paths.get(config.toString())));
		} catch (Exception e) {}
	}
}
