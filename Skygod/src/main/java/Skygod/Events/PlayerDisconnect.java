package Skygod.Events;

import Skygod.Instances.InstanceList;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.instance.Instance;

public class PlayerDisconnect {
	public static void Event(PlayerDisconnectEvent event) {
        Player player = (Player) event.getPlayer();
        
        Instance playerInstance = InstanceList.get().getPlayerInstance(player);
        
        InstanceList.get().removePlayerInstance(player);
        
        playerInstance.scheduleNextTick(instance -> {
        	MinecraftServer.getInstanceManager().unregisterInstance(instance);
        });
	}
}
