package Skygod.Generators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import net.minestom.server.instance.Instance;
import net.minestom.server.utils.BlockPosition;

public class Schematic {
	
	ArrayList<Object[]> blockList = new ArrayList<Object[]>(); 
			
	public Schematic(String fileName) {
		// Load file
		File spawnSchematic = new File(fileName);
		
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
	        	Object[] newBlock = {0, 0, 0, -1};
	        	newBlock[0] = Integer.parseInt(properties[0]);
	        	newBlock[1] = Integer.parseInt(properties[1]);
	        	newBlock[2] = Integer.parseInt(properties[2]);
	        	newBlock[3] = properties[3];
	        	
	        	// Add block to list
	        	blockList.add(newBlock);
        	}
        }
	}
	
	public void load(Instance instance, BlockPosition pos) {
		blockList.forEach(block -> {
				instance.setBlock((int) block[0] + pos.getX(), (int) block[1] + pos.getY(), (int) block[2] + pos.getZ(), BlockMappings.get().getBlock((String) block[3]));
		});
	}
}
