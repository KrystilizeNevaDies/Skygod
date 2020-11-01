package Skygod.Generators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.BlockPosition;

public class Schematic {
	
	ArrayList<int[]> blockList = new ArrayList<int[]>(); 
			
	Schematic(String fileName) {
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
	        // Split Line
        	String[] properties = line.split("|");
        	// Create Block
        	int[] newBlock = {};
        	newBlock[1] = Integer.parseInt(properties[1]);
        	newBlock[2] = Integer.parseInt(properties[2]);
        	newBlock[3] = Integer.parseInt(properties[3]);
        	newBlock[4] = Integer.parseInt(properties[4]);
        	// Add block to list
        	blockList.add(newBlock);
        }
	}
	
	public void load(Instance instance, BlockPosition pos) {
		blockList.forEach(block -> {
			instance.setBlock(block[1] + pos.getX(), block[2] + pos.getY(), block[3] + pos.getZ(), Block.fromStateId((short) block[4]));
		});
	}
}
