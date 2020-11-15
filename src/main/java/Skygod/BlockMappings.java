package Skygod;

import java.util.HashMap;
import java.util.Map;

import net.minestom.server.instance.block.Block;

public enum BlockMappings {
	INSTANCE;
	
	private Map<String, Block> blockStringMappings = new HashMap<String, Block>();
	
	public static BlockMappings get() 
    { 
        return INSTANCE; 
    }
	
	BlockMappings() {
		Block[] blocks = Block.values();
		for (int i = 0; i < blocks.length; i++) {
			blockStringMappings.putIfAbsent(blocks[i].toString(), blocks[i]);
		}
	}
	
	public Block getBlock(String ID) {
		if (blockStringMappings.containsKey(ID)) {
			return blockStringMappings.get(ID.toUpperCase());
		} else {
			System.out.println("Unknown Block Mapping: " + ID);
			return Block.AIR;
		}
	}
}
