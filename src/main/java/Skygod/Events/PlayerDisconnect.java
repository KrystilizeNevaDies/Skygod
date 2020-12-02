package skygod.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.time.TimeUnit;
import skygod.PlayerData;
import skygod.stages.InstanceList;
import skygod.stages.blank.BlankInstance;
import skygod.stages.tutorial.TutorialInstance;

public class PlayerDisconnect {
	public static void Event(PlayerDisconnectEvent event) {
        Player player = (Player) event.getPlayer();
        
        Instance playerInstance = InstanceList.INSTANCE.getPlayerInstance(player);
        
        switch(PlayerData.get(player).getStage()) {
		case BLANK:
			BlankInstance.INSTANCE.playerLeave(playerInstance, player);
			break;
		case TUTORIAL:
			TutorialInstance.INSTANCE.playerLeave(playerInstance, player);
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
