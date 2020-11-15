package Skygod.Stages.Tutorial;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.Position;

public class TutorialInstance {
	public static Instance create(Player player) {
		
		System.out.println("Creating new tutorial instance for " + player.getUsername());
		
		// Create new Instance
		Instance instance = MinecraftServer.getInstanceManager().createInstanceContainer();
		
		// Create Generator
		TutorialGenerator generator = new TutorialGenerator(player, instance);
		
		// Set Chunk Generator
		instance.setChunkGenerator(generator.ChunkGenerator);
		
		// Load 20 x 20 Chunks
		for (int x = -20; x < 20; x++) {
			for (int y = -20; y < 20; y++) {
				instance.loadChunk(x, y);
			}	
		}
		
		// Do finishers
		generator.Finisher.applyFinishers();
		
		// Set Entity Spawner
		new TutorialSpawners(instance);
		
		// Set Advancements
		new TutorialAdvancements(instance);
		
		return instance;
	}

	public static void playerSpawn(Instance instance, Player player) {
		player.teleport(new Position(0, 256, 0));
		System.out.println(player.getUsername());
	}

	public static void playerLeave(Instance instance, Player player) {
		// TODO Auto-generated method stub
		
	}

	public static void playerChat(Instance instance, Player player, PlayerChatEvent event) {
		// TODO Auto-generated method stub
	}
}
