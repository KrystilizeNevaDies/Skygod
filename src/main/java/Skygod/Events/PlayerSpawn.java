package skygod.events;

import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerSpawnEvent;
import skygod.PlayerData;
import skygod.stages.blank.BlankInstance;
import skygod.stages.tutorial.TutorialInstance;

public class PlayerSpawn {
	public static void Event(PlayerSpawnEvent event) {
		
		Player player = (Player) event.getEntity();
		switch(PlayerData.get(player).getCurrentStage()) {
			case BLANK: {BlankInstance.INSTANCE.playerSpawn(event.getSpawnInstance(), player); break;}
			case TUTORIAL: {TutorialInstance.INSTANCE.playerSpawn(event.getSpawnInstance(), player); break;}
			case WORLDONE: break;
			default:
				break;
		}
	}
}