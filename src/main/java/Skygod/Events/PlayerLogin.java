package Skygod.Events;

import Skygod.Stages.InstanceList;
import Skygod.Stages.Blank.BlankInstance;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.Instance;

public class PlayerLogin {
	public static void Event(PlayerLoginEvent event) {
		
        Player player = (Player) event.getPlayer();
        
        // Load hub instance for player
    	Instance spawningInstance = BlankInstance.create(player);
    	InstanceList.INSTANCE.registerPlayerInstance(player, spawningInstance);
        
		// Spawn instance
		event.setSpawningInstance(spawningInstance);
		
		// Set correct gamemode
		player.setGameMode(GameMode.ADVENTURE);
	}
}
