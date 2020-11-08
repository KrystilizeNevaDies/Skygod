/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Skygod.Stages.Tutorial;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Skygod.Schematic;
import de.articdive.jnoise.JNoise;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.world.biomes.Biome;

public class TutorialGenerator  {
	
	private int seed = -1;
	
	public TutorialChunkGenerator ChunkGenerator;
	public TutorialFinisher Finisher;
	
	public TutorialGenerator(Player player, Instance instance) {
		seed = player.getUuid().hashCode();
		ChunkGenerator = new TutorialChunkGenerator();
		Finisher = new TutorialFinisher(instance);
	}
	
	private class TutorialChunkGenerator implements ChunkGenerator {
		
		private JNoise HeightNoiseOne;
		private JNoise HeightNoiseTwo;
		
		private Random random = new Random();
		
		
		public TutorialChunkGenerator() {
			HeightNoiseOne = JNoise.newBuilder().openSimplex().setFrequency(0.01).setSeed(seed).build();
			HeightNoiseTwo = JNoise.newBuilder().openSimplex().setFrequency(0.08).setSeed(seed * 2).build();
		}
		
		
	    @Override
	    public void generateChunkData(ChunkBatch batch, int chunkX, int chunkZ) {
	    	// Set Seed
	    	random.setSeed(seed + chunkX + chunkZ);
	        // Set chunk blocks
	        for (byte x = 0; x < Chunk.CHUNK_SIZE_X; x++)
	            for (byte z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
	            	int posX = (chunkX * 16) + x;
	            	int posZ = (chunkZ * 16) + z;
	                double Y = 64 + (HeightNoiseOne.getNoise(posX, posZ) * 42) + (HeightNoiseTwo.getNoise(posX, posZ) * 5);
	                
	                double delta = Math.sqrt(Math.pow(Math.abs((double) posX), 3) + Math.pow(Math.abs((double) posZ), 3));
	                
	                for (int i = 0; i < Y; i++) {
	                	if (Math.abs(random.nextDouble()) < Math.pow((2048 / delta) / (0.1 * Y), 6)) {
	                		batch.setBlock(x, i, z, Block.STONE);
	                	}
	                }
	            }
	    }
	
	    @Override
	    public void fillBiomes(Biome[] biomes, int chunkX, int chunkZ) {
	    	Arrays.fill(biomes, Biome.PLAINS);
	    }
	
	    @Override
	    public List<ChunkPopulator> getPopulators() {
	        return null;
	    }
	}
	
	public class TutorialFinisher {
		
		private Instance instance;
		
		TutorialFinisher(Instance constuctorInstance) {
			instance = constuctorInstance;
		}
		
		public void applyFinishers() {
			
			// Generate Spawn
			// Get max Y
			int maxY = 256;
			for (int i = 0; i < Chunk.CHUNK_SIZE_Y; i++) {
				if (instance.getBlockStateId(0, i, 0) == Block.AIR.getBlockId()) {
					maxY = i;
					break;
				}
			}
			
			// Do the generation
			generateSpawn(new BlockPosition(0, maxY, 0));
			
			
		}
		
		public void generateSpawn(BlockPosition position) {
			Schematic spawnSchematic = new Schematic("Spawn.txt");
			spawnSchematic.load(instance, position);
		}
	}
}