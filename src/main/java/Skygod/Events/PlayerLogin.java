package Skygod.Events;

import Skygod.PlayerData;
import Skygod.Stages.InstanceList;
import Skygod.Stages.Blank.BlankInstance;
import Skygod.Stages.Tutorial.TutorialInstance;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.Instance;

public class PlayerLogin {
	public static void Event(PlayerLoginEvent event) {
		
        Player player = (Player) event.getPlayer();
        
        Instance spawningInstance = null;
        
        // Find player's current world and load it.
        switch (PlayerData.get(player).getStage()) {
        case NONE: {
        	spawningInstance = BlankInstance.create(player);
        	InstanceList.get().registerPlayerInstance(player, spawningInstance);
        	break;
        }
		case TUTORIAL: {
			spawningInstance = TutorialInstance.create(player);
			InstanceList.get().registerPlayerInstance(player, spawningInstance);
			break;
		}
		default:
			break;
        }
        
		// Spawn instance
		event.setSpawningInstance(spawningInstance);
		
		// Set correct gamemode
		player.setGameMode(GameMode.ADVENTURE);
	}
}
