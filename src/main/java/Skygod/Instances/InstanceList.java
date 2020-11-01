package Skygod.Instances;

import java.util.ArrayList;
import java.util.UUID;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceManager;

public class InstanceList {
	
	private static InstanceList instanceList = null; 
	
	// List of all player instances
	private ArrayList<UUID> playerInstances;
	
	// List of all player uuids
	private ArrayList<UUID> UUIDS = new ArrayList<UUID>();
	
	private InstanceList() {
		playerInstances = new ArrayList<UUID>();
		UUIDS = new ArrayList<UUID>();
	}
	
	/**
	 * Gets the single InstanceList
	 * @return InstanceList
	 */
	public static InstanceList get() 
    { 
        if (instanceList == null) 
        	instanceList = new InstanceList(); 
  
        return instanceList; 
    } 
	
	/**
	 * Gets an ArrayList of every instance that has an associated pair
	 * @return ArrayList<Instance>
	 */
	public ArrayList<Instance> getPlayerInstances() {
		
		ArrayList<Instance> returnlist = new ArrayList<Instance>();
		
		InstanceManager instanceManager = MinecraftServer.getInstanceManager();
		
		Object[] list = instanceManager.getInstances().toArray();
		for (int i = 0; i < list.length; i++) {	
			if (playerInstances.contains(((Instance) list[i]).getUniqueId())) {
				returnlist.add((Instance) list[i]);
			}
		}
		return returnlist;
    }
    /**
     * Gets the instance of a specific player
     * @param Player
     * @return Instance
     */
    public Instance getPlayerInstance(Player player) {
    	// Get Instance ID
		int ID = getPlayerInstanceID(player);
		
		// Check if instance doesn't exist
		if (ID == -1) {return null;};
		
		UUID instanceUuid = playerInstances.get(ID);
		
		InstanceManager instanceManager = MinecraftServer.getInstanceManager();
		
		Object[] list = instanceManager.getInstances().toArray();
		for (int i = 0; i < list.length; i++) {
			if (((Instance) list[i]).getUniqueId() == instanceUuid) {
				return (Instance) list[i];
			}
		}
		return null;
    }
    
    /**
     * Registers the instance of a specific player
     * @param player
     * @param instance
     * @return
     */
    public int registerPlayerInstance(Player player, Instance instance) {
    	UUID uuid = player.getUuid();
    	UUID instanceUuid = instance.getUniqueId();
    	playerInstances.add(instanceUuid);
    	UUIDS.add(uuid);
    	return getPlayerInstanceID(player);
    }
    
    /**
     * Removes a specific player's instance from the list
     * @param player
     */
    public void removePlayerInstance(Player player) {
    	int ID = getPlayerInstanceID(player);
    	if (ID != -1) {
	    	playerInstances.remove(ID);
	    	UUIDS.remove(ID);
    	}
    }
    
    /**
     * Gets the player of a specific instance
     * @param instance
     * @return Player
     */
    public Player getInstancesPlayer(Instance instance) {
    	int ID = getInstanceID(instance);
    	if (ID == -1) {
    		return null;
    	}
    	return MinecraftServer.getConnectionManager().getPlayer(UUIDS.get(ID));
    }
    
    private int getPlayerInstanceID(Player player) {
    	UUID uuid = player.getUuid();
		for (int i = 0; i < UUIDS.size(); i++) {
			if (UUIDS.get(i) == uuid) {
				return i;
			}
		}
    	return -1;
    }
    
    private int getInstanceID(Instance instance) {
    	UUID uuid = instance.getUniqueId();
		for (int i = 0; i < playerInstances.size(); i++) {
			if (playerInstances.get(i) == uuid) {
				return i;
			}
		}
    	return -1;
    }
}
