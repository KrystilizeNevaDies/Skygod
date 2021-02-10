package skygod.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.lock.Acquirable;
import net.minestom.server.utils.time.TimeUnit;
import skygod.PlayerData;
import skygod.stages.InstanceList;
import skygod.stages.SkygodInstance;

public class PlayerDisconnect {
	public static void Event(PlayerDisconnectEvent event) {
		Acquirable<Player> acqPlayer = event.getAcquirablePlayer();
		acqPlayer.acquire((player) -> {

			SkygodInstance instance = InstanceList.getPlayerInstance(acqPlayer);
			
			instance.playerDisconnect(event);
	        
	        InstanceList.removePlayerInstance(acqPlayer);
	        
	        MinecraftServer.getSchedulerManager().buildTask(new Runnable() {
	                    @Override
	                    public void run() {
	                    	MinecraftServer.getInstanceManager().unregisterInstance((Instance) instance);
	                    }
	        }).delay(1, TimeUnit.TICK).schedule();
	        
	        PlayerData.savePlayerData(player);
		});
	}
}
