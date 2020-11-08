package Skygod.Events;

import Skygod.PlayerData;
import Skygod.Stages.Blank.BlankInstance;
import Skygod.Stages.Tutorial.TutorialInstance;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerSpawnEvent;

public class PlayerSpawn {
	public static void Event(PlayerSpawnEvent event) {
		
		Player player = (Player) event.getEntity();
		switch(PlayerData.get(player).getStage()) {
		case NONE: {BlankInstance.playerSpawn(event.getSpawnInstance(), player); break;}
		case TUTORIAL: {TutorialInstance.playerSpawn(event.getSpawnInstance(), player); break;}
		case WORLDONE: break;
		default:
			break;
		}
	}
}
