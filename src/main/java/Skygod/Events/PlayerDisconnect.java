package Skygod.Events;

import Skygod.PlayerData;
import Skygod.Stages.InstanceList;
import Skygod.Stages.Blank.BlankInstance;
import Skygod.Stages.Tutorial.TutorialInstance;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.time.TimeUnit;

public class PlayerDisconnect {
	public static void Event(PlayerDisconnectEvent event) {
        Player player = (Player) event.getPlayer();
        
        Instance playerInstance = InstanceList.INSTANCE.getPlayerInstance(player);
        
        switch(PlayerData.get(player).getStage()) {
		case NONE:
			BlankInstance.playerLeave(playerInstance, player);
			break;
		case TUTORIAL:
			TutorialInstance.playerLeave(playerInstance, player);
			break;
		case WORLDONE:
			break;
		default:
			break;
        }
        
        InstanceList.INSTANCE.removePlayerInstance(player);
        
        MinecraftServer.getSchedulerManager().buildTask(new Runnable() {
                    @Override
                    public void run() {
                    	MinecraftServer.getInstanceManager().unregisterInstance(playerInstance);
                    }
        }).delay(1, TimeUnit.TICK).schedule();
	}
}
