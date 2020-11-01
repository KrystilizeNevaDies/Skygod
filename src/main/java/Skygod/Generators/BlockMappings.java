package Skygod.Generators;

import java.util.HashMap;
import java.util.Map;

import net.minestom.server.instance.block.Block;

public class BlockMappings {
	
	private static BlockMappings blockMappingsObject = null; 
	
	private Map<String, Block> blockMappings = new HashMap<String, Block>();
	
	public static BlockMappings get() 
    { 
        if (blockMappingsObject == null) 
        	blockMappingsObject = new BlockMappings(); 
  
        return blockMappingsObject; 
    }
	
	BlockMappings() {
		Block[] blocks = Block.values();
		for (int i = 0; i < blocks.length; i++) {
			blockMappings.putIfAbsent(blocks[i].toString(), blocks[i]);
		}
	}
	
	public Block getBlock(String ID) {
		if (blockMappings.containsKey(ID)) {
			return blockMappings.get(ID.toUpperCase());
		} else {
			System.out.println("Unknown Block Mapping: " + ID);
			return Block.AIR;
		}
	}
}