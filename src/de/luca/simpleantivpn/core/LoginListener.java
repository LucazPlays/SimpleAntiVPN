package de.luca.simpleantivpn.core;

import de.luca.simpleantivpn.utils.IPChecker;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PostLoginEvent event) {
		if (!event.getPlayer().hasPermission(AntiVPN.antivpnbypasspermission)) {
			IPChecker.getInstance().start(() -> {
				if (!IPChecker.getInstance().isipresidental(event.getPlayer().getAddress().getAddress().getHostAddress())) {
					event.getPlayer().disconnect(TextComponent.fromLegacyText(AntiVPN.antivpnkickmessage));
				}
			});
		}
	}
}
