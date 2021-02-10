package skygod.stages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.lock.Acquirable;

public class InstanceList {
	
	// Map of all player instances
	private static Map<UUID, SkygodInstance> instanceMap = new HashMap<UUID, SkygodInstance>();
	
	/**
	 * Gets an ArrayList of every instance that has an associated pair
	 * @return ArrayList<Instance>
	 */
	public static ArrayList<SkygodInstance> getPlayerInstances() {
		
		ArrayList<SkygodInstance> returnlist = new ArrayList<SkygodInstance>();
		
		instanceMap.forEach((uuid, instance) -> {
			returnlist.add(instance);
		});
		
		return returnlist;
    }
    /**
     * Gets the instance of a specific player
     * @param Player
     * @return Instance
     */
    public static SkygodInstance getPlayerInstance(Acquirable<Player> player) {
    	return instanceMap.get(player.unwrap().getUuid());
    }
    
    /**
     * Registers the instance of a specific player
     * @param player
     * @param instance
     * @return
     */
    public static void registerPlayerInstance(Player player, SkygodInstance instance) {
    	instanceMap.put(player.getUuid(), instance);
    }
    
    /**
     * Removes a specific player's instance from the list
     * @param acqPlayer
     */
    public static void removePlayerInstance(Acquirable<Player> acqPlayer) {
    	instanceMap.remove(acqPlayer.unwrap().getUuid());
    }
    
    /**
     * Gets the player of a specific instance
     * @param instance
     * @return Player
     */
    public static Acquirable<Player> getInstancesPlayer(SkygodInstance instance) {
    	Object[] keySet = instanceMap.keySet().toArray();
    	
    	for (int i = 0; i < keySet.length; i++) {
    		if (instanceMap.get(keySet[i]) == instance) {
    			return MinecraftServer.getConnectionManager().getPlayer((UUID) keySet[i]);
    		}
    	}
    	
    	return null;
    }
}
