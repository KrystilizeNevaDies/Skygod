package Skygod.Instances;

import Skygod.EntitySpawners.TutorialSpawners;
import Skygod.Generators.TutorialGenerator;
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
		
		// Create Generator
		TutorialGenerator generator = new TutorialGenerator(player, instance);
		
	    // Set the Generator
		instance.setChunkGenerator(generator.ChunkGenerator);
	    
		// load chunks in a circle with radius 20
		for (int chunkX = -20; chunkX < 20; chunkX++)
			for (int chunkZ = -20; chunkZ < 20; chunkZ++) {
				if (Math.sqrt(Math.pow(chunkX, 2) + Math.pow(chunkZ, 2)) < 20)
					instance.loadChunk(chunkX, chunkZ);
			}
		
		// Apply finishers
		generator.Finisher.applyFinishers();
		  
		// Apply Entity Spawner
		new TutorialSpawners(instance);
		
		// Disable new chunks
		instance.enableAutoChunkLoad(false);
		
		return instance;
	}
}
