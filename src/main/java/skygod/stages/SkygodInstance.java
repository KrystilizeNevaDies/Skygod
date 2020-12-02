package skygod.stages;

import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.instance.Instance;

public interface SkygodInstance {
	public Instance create(Player player);
	
	public String getName();
	
	public default void playerSpawn(Instance instance, Player player) {
		System.out.println(player.getUsername() + " has joined instance " + getName());
	}
	
	public default void playerSettings(Instance playerInstance, Player player) {
		
	}

	public default void playerLeave(Instance playerInstance, Player player) {
		System.out.println(player.getUsername() + " has left instance " + getName());
	}

	public default void playerChat(Instance playerInstance, Player player, PlayerChatEvent event) {
	}
}
