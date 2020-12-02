package skygod.events;

import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.Instance;
import skygod.stages.InstanceList;
import skygod.stages.blank.BlankInstance;

public class PlayerLogin {
	public static void Event(PlayerLoginEvent event) {
		
        Player player = (Player) event.getPlayer();
        
        // Load hub instance for player
    	Instance spawningInstance = BlankInstance.INSTANCE.create(player);
    	InstanceList.INSTANCE.registerPlayerInstance(player, spawningInstance);
        
		// Spawn instance
		event.setSpawningInstance(spawningInstance);
		
		// Set correct gamemode
		player.setGameMode(GameMode.ADVENTURE);
	}
}
