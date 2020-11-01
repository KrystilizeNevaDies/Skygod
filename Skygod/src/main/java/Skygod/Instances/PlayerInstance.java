package Skygod.Instances;

import Skygod.Generators.TutorialChunkGenerator;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceManager;

public class PlayerInstance {
	public static Instance createNew(Player player) {
		// Get instance manager
		InstanceManager instanceManager = MinecraftServer.getInstanceManager();
		
		// Create the instance
		Instance instance = instanceManager.createInstanceContainer();
		
	    // Set the ChunkGenerator
		instance.setChunkGenerator(new TutorialChunkGenerator());
	    
		// enable auto chunk load
		instance.enableAutoChunkLoad(true);
		
		return instance;
	}
}
