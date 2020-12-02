package skygod.events;

import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.instance.Instance;
import skygod.PlayerData;
import skygod.stages.InstanceList;
import skygod.stages.blank.BlankInstance;
import skygod.stages.tutorial.TutorialInstance;

public class PlayerChat {
	public static void Event(PlayerChatEvent event) {
		Player player = (Player) event.getSender();
        
        Instance playerInstance = InstanceList.INSTANCE.getPlayerInstance(player);
        
        switch(PlayerData.get(player).getCurrentStage()) {
        
		case BLANK:
			BlankInstance.INSTANCE.playerChat(playerInstance, player, event);
			break;
		case TUTORIAL:
			TutorialInstance.INSTANCE.playerChat(playerInstance, player, event);
			break;
		case WORLDONE:
			break;
		default:
			break;
        }
	}
}
