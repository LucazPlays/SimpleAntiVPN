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

	public Plugin plugin;

	public static String antivpnbypasspermission;
	
	public static String antivpnkickmessage;

	public void onEnable() {
		createConfigs();
		plugin = this;
		ProxyServer.getInstance().getPluginManager().registerListener(plugin, new LoginListener());
	}
	

	public Configuration config;
	public File file;

	public void createConfigs() {
		try {
			file = new File(ProxyServer.getInstance().getPluginsFolder().getPath(), "SimpleAntiVPN");
			file.mkdirs();
			file = new File(ProxyServer.getInstance().getPluginsFolder().getPath(), "SimpleAntiVPN/config.yml");
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("Created Config File");
			}

			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

			antivpnbypasspermission = String.valueOf(addDefault(config, "settings.bypasspermission", "simpleantivpn.bypass"));

			antivpnkickmessage = String.valueOf(addDefault(config, "settings.antivpnkickmessage", "&cYou got kicked due to the use of a VPN or Proxy")).replaceAll("&", "§");


			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
			System.out.println("Loaded Config File");

		} catch (Throwable throwable) {
		}
	}

	public Object addDefault(Configuration conf, String path, Object value) {
		if (!conf.contains(path)) {
			conf.set(path, value);
			return value;
		}
		return conf.get(path);
	}

	public void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
