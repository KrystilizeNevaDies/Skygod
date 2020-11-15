package Skygod.Events;

import Skygod.PlayerData;
import Skygod.Stages.InstanceList;
import Skygod.Stages.Blank.BlankInstance;
import Skygod.Stages.Tutorial.TutorialInstance;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.instance.Instance;

public class PlayerChat {
	public static void Event(PlayerChatEvent event) {
		Player player = (Player) event.getSender();
        
        Instance playerInstance = InstanceList.INSTANCE.getPlayerInstance(player);
        
        switch(PlayerData.get(player).getStage()) {
        
		case NONE:
			BlankInstance.playerChat(playerInstance, player, event);
			break;
		case TUTORIAL:
			TutorialInstance.playerChat(playerInstance, player, event);
			break;
		case WORLDONE:
			break;
		default:
			break;
        }
	}
}
