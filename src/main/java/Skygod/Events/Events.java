package Skygod.Events;

import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;

public class Events {
	public static void registerEvents(Player player) {
		player.addEventCallback(PlayerLoginEvent.class, event -> {PlayerLogin.Event(event);});
        player.addEventCallback(PlayerSpawnEvent.class, event -> {PlayerSpawn.Event(event);});
        player.addEventCallback(PlayerDisconnectEvent.class, event -> {PlayerDisconnect.Event(event);});
        player.addEventCallback(PlayerChatEvent.class, event -> {PlayerChat.Event(event);});
	}
}
