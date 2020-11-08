package Skygod.Events;

import Skygod.PlayerData;
import Skygod.Stages.InstanceList;
import Skygod.Stages.Hub.HubInstance;
import Skygod.Stages.Tutorial.TutorialInstance;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.Instance;

public class PlayerLogin {
	public static void Event(PlayerLoginEvent event) {
		
        Player player = (Player) event.getPlayer();
        
        // Get player's data
        PlayerData playerData = PlayerData.get(player);
        
        // Find player's current world and load it.
        switch (playerData.getStage()) {
        case NONE: player.setInstance(HubInstance.get());
		case TUTORIAL: InstanceList.get().registerPlayerInstance(player, TutorialInstance.create(player));
		case WORLDONE: InstanceList.get().registerPlayerInstance(player, TutorialInstance.create(player));
		default:
			break;
        }
        
        // Get instance
		Instance spawningInstance = InstanceList.get().getPlayerInstance(player);
        
		// Spawn instance
		event.setSpawningInstance(spawningInstance);
		
		player.setGameMode(GameMode.ADVENTURE);
	}
}
