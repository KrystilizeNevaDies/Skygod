package skygod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import de.piegames.nbt.CompoundTag;
import de.piegames.nbt.stream.NBTInputStream;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.batch.BlockBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;

public class Schematic {
	
	BlockBatch batch;
	
	ArrayList<int[]> blockList = new ArrayList<int[]>(); 
			
	public Schematic(String fileName, SchematicType type, Boolean overwriteAir) {
		batch = new BlockBatch(null);
		create(fileName, type, overwriteAir);
	}
	
	public Schematic(String string, SchematicType type) {
		create(string, type, true);
	}

	private void create(String fileName, SchematicType type, Boolean overwriteAir) {
		switch (type) {
		case LEGACY:	readLegacySchematic(fileName, overwriteAir);	break;
		case SCHEM:		readSchemSchematic(fileName, overwriteAir);		break;
		case TEXT:		readText(fileName, overwriteAir);				break;
		default:														break;
		}
	}

	private void readSchemSchematic(String name, Boolean overwriteAir) {
		try {   
            InputStream fis = new FileInputStream(new File(name));
            
            NBTInputStream input = new NBTInputStream(fis);
            
            CompoundTag nbtdata = input.readTag().getAsCompoundTag().get();
 
            Map<Integer, String> palette = new HashMap<Integer, String>();
            
            short width = nbtdata.getAsShortTag("Width").get().getValue();
            short height = nbtdata.getAsShortTag("Height").get().getValue();
            short length = nbtdata.getAsShortTag("Length").get().getValue();;
            
 
        	// Get blocks
            byte[] blockData = nbtdata.getAsByteArrayTag("BlockData").get().getValue();

            CompoundTag blockIDs = nbtdata.getAsCompoundTag("Palette").get();
            
            blockIDs.getValue().forEach((string, tag) -> {
            	palette.put(tag.getAsIntTag().get().getValue(), string);
            });
            
            fis.close();
            input.close();
            
            // Loop through blocks and add them to block list
            for (int X = 0; X < width; X++)
            	for (int Y = 0; Y < height; Y++)
            		for (int Z = 0; Z < length; Z++) {
            			int i = (Y * length + Z) * width + X;
            			String namespace = palette.get((int) blockData[i]);
            			if (namespace != "minecraft:air" || overwriteAir) {
                			int[] newBlock = {0, 0, 0, -1};
            	        	newBlock[0] = X; 
            	        	newBlock[1] = Y;
            	        	newBlock[2] = Z;
            	        	
            	        	newBlock[3] = BlockMappings.INSTANCE.getNamespace(namespace);
                			blockList.add(newBlock);
            			}
            			
            		}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	private void readLegacySchematic(String name, Boolean overwriteAir) {
		try {
            InputStream fis = new FileInputStream(new File(name));
            
            NBTInputStream input = new NBTInputStream(fis);
            
            CompoundTag nbtdata = input.readTag().getAsCompoundTag().get();
            
            
            short width = nbtdata.getAsShortTag("Width").get().getValue();
            short height = nbtdata.getAsShortTag("Height").get().getValue();
            short length = nbtdata.getAsShortTag("Length").get().getValue();;
            
            
        	// Get blocks
            byte[] blockId = nbtdata.getAsByteArrayTag("Blocks").get().getValue();
            byte[] blockData = nbtdata.getAsByteArrayTag("Data").get().getValue();
            byte[] addId = new byte[0];
            short[] blocks = new short[blockId.length]; // Have to later combine IDs
            
            
            
            // Worldedit BlockIDs:
            
            // We support 4096 block IDs using the same method as vanilla Minecraft, where
            // the highest 4 bits are stored in a separate byte array.
            
            // Combine the AddBlocks data with the first 8-bit block ID
            for (int i = 0; i < blockId.length; i++) {
                if ((i >> 1) >= addId.length) { // No corresponding AddBlocks index
                    blocks[i] = (short) (blockId[i] & 0xFF);
                } else {
                    if ((i & 1) == 0) {
                        blocks[i] = (short) (((addId[i >> 1] & 0x0F) << 8) + (blockId[i] & 0xFF));
                    } else {
                        blocks[i] = (short) (((addId[i >> 1] & 0xF0) << 4) + (blockId[i] & 0xFF));
                    }
                }
            }
            
            // ListTag<?> entities = nbtdata.getAsListTag("Entities").get();
            // ListTag<?> tileentities = nbtdata.getAsListTag("TileEntities").get();
            
            fis.close();
            input.close();
            
            // Loop through blocks and add them to block list
            for (int X = 0; X < width; X++)
            	for (int Y = 0; Y < height; Y++)
            		for (int Z = 0; Z < length; Z++) {
            			int i = (Y * length + Z) * width + X;
            			if (blocks[i] != 0 || overwriteAir) {
                			int[] newBlock = {0, 0, 0, -1};
            	        	newBlock[0] = X; 
            	        	newBlock[1] = Y;
            	        	newBlock[2] = Z;
            	        	newBlock[3] = BlockMappings.INSTANCE.getLegacyBlock(blocks[i], blockData[i]);
                			blockList.add(newBlock);
            			}
            			
            		}
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	private void readText(String name, Boolean overwriteAir) {
		// Load file
		File spawnSchematic = new File(name);
		
		// Create scanner
		Scanner myReader = null;
		try {
			myReader = new Scanner(spawnSchematic);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Read each line and add blocks to block list
        while (myReader.hasNextLine()) {
	        // Get line
        	String line = myReader.nextLine();
        	// If line Is Valid
        	if (!line.startsWith("#") && !line.isBlank()) {
		        // Split Line
	        	String[] properties = line.split("[|]");
	        	
	        	// Create Block
	        	int[] newBlock = {0, 0, 0, -1};
	        	newBlock[0] = Integer.parseInt(properties[0]);
	        	newBlock[1] = Integer.parseInt(properties[1]);
	        	newBlock[2] = Integer.parseInt(properties[2]);
	        	newBlock[3] = Block.valueOf(properties[3]).getBlockId();
	        	
	        	// Add block to list
	        	blockList.add(newBlock);
        	}
        }
	}
	
	public void load(InstanceContainer instance, BlockPosition pos) {
		
		BlockBatch batch = new BlockBatch(instance);
		
		blockList.forEach(block -> {
			batch.setBlock(
					block[0] + pos.getX(),
					block[1] + pos.getY(),
					block[2] + pos.getZ(),
					Block.fromStateId((short) block[3])
				);
		});
		batch.flush(null);
	}
}
