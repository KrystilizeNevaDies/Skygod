/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package skygod.stages.tutorial;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
	private Random random = new Random();
	
	
	public TutorialChunkGenerator chunkGenerator;
	public TutorialFinisher finisher;
	private Instance instance;
	
	public TutorialGenerator(Player player, Instance someInstance) {
		instance = someInstance;
		seed = player.getUuid().hashCode();
		random.setSeed(seed);
		chunkGenerator = new TutorialChunkGenerator();
		finisher = new TutorialFinisher(chunkGenerator);
	}
	
	private Boolean isValid(int PosX, int PosZ) {
		for (int i = 0; i < 256; i++) {
			Block block = Block.fromStateId(instance.getBlockStateId(PosX, i, PosZ));
			if (block != Block.AIR)
				return true;
		}
		return false;
	}
	
	private class TutorialChunkGenerator implements ChunkGenerator {
		
		private JNoise HeightNoiseOne;
		private JNoise HeightNoiseTwo;
		
		
		public TutorialChunkGenerator() {
			HeightNoiseOne = JNoise.newBuilder().openSimplex().setFrequency(0.01).setSeed(seed).build();
			HeightNoiseTwo = JNoise.newBuilder().openSimplex().setFrequency(0.02).setSeed(seed * 2).build();
		}
		
		public int getY(int X, int Z) {
			return (int) (64 + (HeightNoiseOne.getNoise(X, Z) * 42) + (HeightNoiseTwo.getNoise(X, Z) * 5));
		}
		
		
	    @Override
	    public void generateChunkData(ChunkBatch batch, int chunkX, int chunkZ) {
	        // Set chunk blocks
	        for (byte x = 0; x < Chunk.CHUNK_SIZE_X; x++)
	            for (byte z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
	            	int posX = (chunkX * 16) + x;
	            	int posZ = (chunkZ * 16) + z;
	                double Y = getY(posX, posZ);
	                
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
		
		
		private TutorialChunkGenerator chunkGenerator;
		
		TutorialFinisher(TutorialChunkGenerator someChunkGenerator) {
			chunkGenerator = someChunkGenerator;
		}
		
		public void applyFinishers() {
			
			// Generate Spawn
			// Get max Y
			int maxY = chunkGenerator.getY(0, 0);
			
			// Do the generation
			generateSpawn(new BlockPosition(0, maxY, 0));
			
			for (int i = 0; i < 11; i++)
				createRandomDebris();
			
		}
		
		private void createRandomDebris() {
			// Choose a valid, random spot in the world
			int posX = (int) ((random.nextInt(320) - 160) * (Math.pow(random.nextDouble(), 3)));
			int posZ = (int) ((random.nextInt(320) - 160) * (Math.pow(random.nextDouble(), 3)));
			while (!isValid(posX, posZ) || Math.hypot(posX, posZ) < 8) {
				posX = (int) ((random.nextInt(320) - 160) * (Math.pow(random.nextDouble(), 3)));
				posZ = (int) ((random.nextInt(320) - 160) * (Math.pow(random.nextDouble(), 3)));
			}
			
			// Place a debris
			TutorialSchematicGroup.debris().load(instance, 
				new BlockPosition(posX, chunkGenerator.getY(posX, posZ), posZ)
			);
			
			// Finish with surrounding grass
			for (int x = -10; x < 11; x++)
				for (int z = -10; z < 11; z++) {
					if (isValid(x, z) && Math.hypot(x, z) * (random.nextDouble() + 0.5) < 5) {
						instance.setBlock(posX + x, chunkGenerator.getY(posX + x, posZ + z), posZ + z, Block.GRASS_BLOCK);
					}
				}
		}
		
		private void generateSpawn(BlockPosition position) {
			TutorialSchematicGroup.largeTent.load(instance, position);
		}
	}
}