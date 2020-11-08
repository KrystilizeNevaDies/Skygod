package Skygod.Stages.Tutorial;

import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.world.DimensionType;

public abstract class TutorialInstance extends Instance {
	
	private TutorialSpawners spawners;
	private TutorialAdvancements advancements;
	
	
	@Override
	public void tick(long time) {
		// Do ticks
		this.getSpawners().tick();
		super.tick(time);
	}
	
	public TutorialInstance(Player player) {
		super(null, DimensionType.OVERWORLD);
		
		// Create Generator
		TutorialGenerator generator = new TutorialGenerator(player, this);
		
		// Set Chunk Generator
		setChunkGenerator(generator.ChunkGenerator);
		
		// Load 20 x 20 Chunks
		for (int x = -20; x < 20; x++) {
			for (int y = -20; y < 20; y++) {
				loadChunk(x, y);
			}	
		}
		
		// Do finishers
		generator.Finisher.applyFinishers();
		
		// Set Entity Spawner
		setSpawners(new TutorialSpawners(this));
		
		// Set Advancements
		setAdvancements(new TutorialAdvancements());
	}

	public TutorialAdvancements getAdvancements() {
		return advancements;
	}

	public void setAdvancements(TutorialAdvancements advancements) {
		this.advancements = advancements;
	}

	public TutorialSpawners getSpawners() {
		return spawners;
	}

	public void setSpawners(TutorialSpawners spawners) {
		this.spawners = spawners;
	}
	
}
