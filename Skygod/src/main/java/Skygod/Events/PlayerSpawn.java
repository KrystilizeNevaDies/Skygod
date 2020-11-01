package Skygod.Events;

import net.minestom.server.entity.Entity;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.Position;

public class PlayerSpawn {
	public static void Event(PlayerSpawnEvent event) {
		Entity entity = event.getEntity();
		Instance entityInstance = entity.getInstance();
		int spawnY = 256;
		for (int i = 0; i < Chunk.CHUNK_SIZE_Y; i++) {
			if (entityInstance.getBlockStateId(0, i, 0) == Block.AIR.getBlockId()) {
				spawnY = i;
				break;
			}
		}
		
		entity.teleport(new Position(0, spawnY, 0));
	}
}
