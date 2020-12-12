package skygod.stages.tutorial;

import java.io.File;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.utils.Position;
import skygod.sound.MidiSong;
import skygod.stages.SkygodInstance;

public class TutorialInstance implements SkygodInstance {
	
	public static TutorialInstance INSTANCE = new TutorialInstance();
	
	public Instance create(Player player) {
		
		System.out.println("Creating new tutorial instance for " + player.getUsername());
		
		// Create new Instance
		InstanceContainer instance = MinecraftServer.getInstanceManager().createInstanceContainer();
		
		// Create Generator
		TutorialGenerator generator = new TutorialGenerator(player, instance);
		
		// Set Chunk Generator
		instance.setChunkGenerator(generator.chunkGenerator);
		instance.enableAutoChunkLoad(false);
		
		// Load chunks
		for (int x = -10; x < 10; x++) {
			for (int y = -10; y < 10; y++) {
				instance.loadChunk(x, y);
			}	
		}
		
		
		
		// Do finishers
		generator.finisher.applyFinishers();
			
		// Set Entity Spawner
		new TutorialSpawners(instance);
		
		// Set Advancements
		new TutorialAdvancements(instance);
		
		return instance;
	}
	
	public String getName() {
		return "Tutorial";
	}

	public void playerSpawn(Instance instance, Player player) {
		player.teleport(new Position(0, 256, 0));
		player.setGameMode(GameMode.CREATIVE);
		
		MidiSong song = new MidiSong();
		
		song.addPlayer(player);
		
		song.setMidiFile(new File("Songs/ZZZ.mid"));
		
		song.play();
		
	}

	public void playerLeave(Instance instance, Player player) {
		// TODO Auto-generated method stub
		
	}

	public void playerChat(Instance instance, Player player, PlayerChatEvent event) {
		// TODO Auto-generated method stub
	}
}
