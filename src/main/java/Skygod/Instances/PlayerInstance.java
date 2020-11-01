package Skygod.Instances;

import Skygod.EntitySpawners.TutorialSpawners;
import Skygod.Generators.TutorialGenerator;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;

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
	    
		// load 40 x 40 chunks
		for (int chunkX = -20; chunkX < 20; chunkX++)
			for (int chunkZ = -20; chunkZ < 20; chunkZ++) {
				instance.loadChunk(chunkX, chunkZ);
			}
		
		// Generate Spawn
		// Get max Y
		int maxY = 256;
		for (int i = 0; i < Chunk.CHUNK_SIZE_Y; i++) {
			if (instance.getBlockStateId(0, i, 0) == Block.AIR.getBlockId()) {
				maxY = i;
				break;
			}
		}
		
		// Apply generator
		generator.Finisher.generateSpawn(new BlockPosition(0, maxY, 0));
		
		// Apply Entity Spawner
		new TutorialSpawners(instance);
		
		// Disable new chunks
		instance.enableAutoChunkLoad(false);
		
		return instance;
	}
}
