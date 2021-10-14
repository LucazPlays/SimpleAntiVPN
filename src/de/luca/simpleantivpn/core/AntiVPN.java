package de.luca.simpleantivpn.core;

import java.io.File;

import de.luca.simpleantivpn.utils.IPChecker;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

public class AntiVPN extends Plugin {

	public static Plugin plugin;

	public static String antivpnbypasspermission;
	
	public static String antivpnkickmessage;
	
	public void onEnable() {
		createConfigs();
		plugin = this;
		ProxyServer.getInstance().getPluginManager().registerListener(plugin, new Listener() {
			@SuppressWarnings("deprecation")
			@EventHandler
			public void onJoin(PostLoginEvent event) {
				if (!event.getPlayer().hasPermission(antivpnbypasspermission)) {
					IPChecker.getInstance().start(() -> {
						if (!IPChecker.getInstance().isipresidental(event.getPlayer().getAddress().getAddress().getHostAddress())) {
							event.getPlayer().disconnect(TextComponent.fromLegacyText(antivpnkickmessage));
						}
					});
				}
			}
		});
	}
	

	public static Configuration config;
	public static File file;

	public static void createConfigs() {
		try {
			file = new File("SimpleAntiVPN/Config.yml");
			file.mkdirs();
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("Created Config File");
			}

			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

			antivpnbypasspermission = String.valueOf(addDefault(config, "settings.bypasspermission", "simpleantivpn.bypass"));

			antivpnkickmessage = String.valueOf(addDefault(config, "settings.antivpnkickmessage", "You got kicked due to the use of a VPN or Proxy"));


			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
			System.out.println("Loaded Config File");

		} catch (Throwable throwable) {
		}
	}

	public static Object addDefault(Configuration conf, String path, Object value) {
		if (!conf.contains(path)) {
			conf.set(path, value);
			return value;
		}
		return conf.get(path);
	}

	public static void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
