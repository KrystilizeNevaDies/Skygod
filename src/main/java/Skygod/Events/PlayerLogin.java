package skygod.events;

import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.PlayerLoginEvent;
import skygod.stages.InstanceList;
import skygod.stages.blank.BlankInstance;

public class PlayerLogin {
	public static void Event(PlayerLoginEvent event) {
		event.getAcquirablePlayer().acquire((player) -> {
			// Load hub instance for player
	        BlankInstance spawningInstance = new BlankInstance(player);
	    	InstanceList.registerPlayerInstance(player, spawningInstance);
	    	
			// Spawn instance
			event.setSpawningInstance(spawningInstance);
			
			// Set correct gamemode
			player.setGameMode(GameMode.ADVENTURE);
        });
        
	}
}
