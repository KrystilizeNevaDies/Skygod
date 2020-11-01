package Skygod.Instances;

import Skygod.Generators.AlphaChunkGenerator;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceManager;

public class HubInstance {
	
	private static Instance inst;
	
	public static void init() {
		// Get instance manager
		InstanceManager instanceManager = MinecraftServer.getInstanceManager();
		
		// Create the instance
		Instance instance = instanceManager.createInstanceContainer();
		
	    // Set the ChunkGenerator
		instance.setChunkGenerator(new AlphaChunkGenerator());
	    
		// load minimal chunks
		instance.loadChunk(0, 0);
		instance.loadChunk(-1, 0);
		instance.loadChunk(0, -1);
		instance.loadChunk(-1, -1);
		
		HubInstance.inst = instance;
	}
	
	public static Instance get() {
	    return inst;
	}
}
