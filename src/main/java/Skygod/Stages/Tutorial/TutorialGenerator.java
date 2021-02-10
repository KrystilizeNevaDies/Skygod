package skygod.stages.tutorial;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.articdive.jnoise.JNoise;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.ChunkPopulator;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.world.biomes.Biome;

public class TutorialGenerator  {
	
	private int seed = -1;
	private Random random = new Random();
	
	
	public TutorialChunkGenerator chunkGenerator;
	public TutorialFinisher finisher;
	private InstanceContainer instance;
	
	public TutorialGenerator(Player player, InstanceContainer someInstance) {
		instance = someInstance;
		seed = player.getUuid().hashCode();
		random.setSeed(seed);
		chunkGenerator = new TutorialChunkGenerator();
		finisher = new TutorialFinisher(chunkGenerator);
	}
	
	private Boolean isValid(int PosX, int PosZ) {
		for (int i = 0; i < 255; i++) {
			Block block = Block.AIR;
			try {
				block = Block.fromStateId(instance.getBlockStateId(PosX, i, PosZ));
			} catch (Exception e) {
				
			}
			if (block != Block.AIR)
				return true;
		}
		return false;
	}
	
	private class TutorialChunkGenerator implements ChunkGenerator {
		
		private JNoise HeightNoiseOne;
		private JNoise HeightNoiseTwo;
		private JNoise HeightNoiseThree;
		private JNoise HeightNoiseFour;
		
		
		public TutorialChunkGenerator() {
			HeightNoiseOne = JNoise.newBuilder().openSimplex().setFrequency(0.01).setSeed(seed).build();
			HeightNoiseTwo = JNoise.newBuilder().openSimplex().setFrequency(0.02).setSeed(seed * 2).build();
			HeightNoiseThree = JNoise.newBuilder().openSimplex().setFrequency(0.04).setSeed(seed * 4).build();
			HeightNoiseFour = JNoise.newBuilder().openSimplex().setFrequency(0.08).setSeed(seed * 8).build();
		}
		
		public int getY(int X, int Z) {
			return (int) (64 + (HeightNoiseOne.getNoise(X, Z) * 32) + (HeightNoiseTwo.getNoise(X, Z) * 16) + (HeightNoiseThree.getNoise(X, Z) * 8) + (HeightNoiseFour.getNoise(X, Z) * 4));
		}
		
		
	    @Override
	    public void generateChunkData(ChunkBatch batch, int chunkX, int chunkZ) {
	        // Set chunk blocks
	    	for (byte x = 0; x < Chunk.CHUNK_SIZE_X; x++)
	            for (byte z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
	            	int posX = (chunkX * 16) + x;
	            	int posZ = (chunkZ * 16) + z;
	                double Y = getY(posX, posZ);
	                
	                double delta = Math.hypot(posX, posZ);
	                
	                for (int i = 0; i < Y; i++) {
	                	if (delta < 128 * Math.pow(random.nextDouble(), 16 / Y)) {
	                		if (i >= 80 + (10 * random.nextDouble())) {
	                			batch.setBlock(x, i, z, Block.SNOW_BLOCK);
	                			if (i == Y - 1 && random.nextBoolean()) {
	                				short id = Block.SNOW.getBlockId();
	                				int idx = random.nextInt(7);
	                				batch.setBlock(x, i + 1, z, Block.fromStateId((short) (id + idx)));
	                			}
	                		} else {
	                			batch.setBlock(x, i, z, Block.STONE);	                			
	                		}
	                	}
	                }
	                
	                if (Y < 40 && delta < 128) {
	                	for (int i = (int) Y; i < 40; i++) {
	                		batch.setBlock(x, i, z, Block.WATER);
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
			for (int x = -20; x < 20; x++)
				for (int z = -20; z < 20; z++) {
					if (isValid(x, z) && Math.hypot(x, z) < 6 * (random.nextDouble() + 0.5)) {
						instance.setBlock(posX + x, chunkGenerator.getY(posX + x, posZ + z), posZ + z, Block.GRASS_BLOCK);
					}
				}
		}
		
		private void generateSpawn(BlockPosition position) {
			TutorialSchematicGroup.debris().load(instance, position);
		}
	}
}