package Skygod.Stages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;

public enum InstanceList {
	
	INSTANCE;
	
	// Map of all player instances
	Map<UUID, Instance> instanceMap;
	
	private InstanceList() {
		instanceMap = new HashMap<UUID, Instance>();
	}
	
	/**
	 * Gets an ArrayList of every instance that has an associated pair
	 * @return ArrayList<Instance>
	 */
	public ArrayList<Instance> getPlayerInstances() {
		
		ArrayList<Instance> returnlist = new ArrayList<Instance>();
		
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
    public Instance getPlayerInstance(Player player) {
    	return instanceMap.get(player.getUuid());
    }
    
    /**
     * Registers the instance of a specific player
     * @param player
     * @param instance
     * @return
     */
    public void registerPlayerInstance(Player player, Instance instance) {
    	instanceMap.put(player.getUuid(), instance);
    }
    
    /**
     * Removes a specific player's instance from the list
     * @param player
     */
    public void removePlayerInstance(Player player) {
    	instanceMap.remove(player.getUuid());
    }
    
    /**
     * Gets the player of a specific instance
     * @param instance
     * @return Player
     */
    public Player getInstancesPlayer(Instance instance) {
    	
    	Object[] keySet = instanceMap.keySet().toArray();
    	
    	for (int i = 0; i < keySet.length; i++) {
    		if (instanceMap.get(keySet[i]) == instance) {
    			return MinecraftServer.getConnectionManager().getPlayer((UUID) keySet[i]);
    		}
    	}
    	
    	return null;
    }
}
