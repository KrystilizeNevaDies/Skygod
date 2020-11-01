package Skygod.Events;

import Skygod.Instances.InstanceList;
import Skygod.Instances.PlayerInstance;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.Instance;

public class PlayerLogin {
	public static void Event(PlayerLoginEvent event) {
        Player player = (Player) event.getPlayer();
        
        
        
        if (InstanceList.get().getPlayerInstance(player) == null) {
        	Instance playerInstance = PlayerInstance.createNew(player);
    		
    		InstanceList.get().registerPlayerInstance(player, playerInstance);
        };
        
		Instance spawningInstance = InstanceList.get().getPlayerInstance(player);
        
		event.setSpawningInstance(spawningInstance);
		
		player.sendMessage("You have been sent to " + InstanceList.get().getInstancesPlayer(spawningInstance).getUsername() + "'s world.");
		player.sendMessage("Player Worlds Loaded:");
		InstanceList.get().getPlayerInstances().forEach(instance -> {
			player.sendMessage(InstanceList.get().getInstancesPlayer(instance).getUsername() + " | " + instance.getDimensionType().getName().toString());
		});
	}
}
