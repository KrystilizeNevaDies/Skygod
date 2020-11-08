package Skygod.Stages.Blank;

import Skygod.Books;
import Skygod.Stages.BlankGenerator;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.metadata.WrittenBookMeta;
import net.minestom.server.item.metadata.WrittenBookMeta.WrittenBookGeneration;
import net.minestom.server.network.packet.server.play.OpenBookPacket;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.time.TimeUnit;

public class BlankInstance {
	public static Instance create(Player player) {

		System.out.println("Creating new blank instance for " + player.getUsername());
		
		// Create the instance
		Instance instance = MinecraftServer.getInstanceManager().createInstanceContainer();
		
	    // Set the ChunkGenerator
		instance.setChunkGenerator(new BlankGenerator());
	    
		// load minimal chunks
		instance.loadChunk(0, 0);
		instance.loadChunk(-1, 0);
		instance.loadChunk(0, -1);
		instance.loadChunk(-1, -1);
		
		return instance;
	}
	
	public static void playerSpawn(Instance instance, Player player) {
		player.teleport(new Position(0, 1, 0));
		
		player.getInventory().clear();
		
		ItemStack item = new ItemStack(Material.WRITTEN_BOOK, (byte) 1);
		
		WrittenBookMeta meta = (WrittenBookMeta) item.getItemMeta();
		
		
		
		Books.addPages(meta, Books.serverSelect);
		
		meta.setAuthor("Kry");
		
		meta.setGeneration(WrittenBookGeneration.ORIGINAL);
		
		meta.setResolved(false);
		
		meta.setTitle("A Book");
		
		player.getInventory().addItemStack(item);		
		
		MinecraftServer.getSchedulerManager().buildTask(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				OpenBookPacket bookPacket = new OpenBookPacket();
				
				bookPacket.hand = Player.Hand.MAIN;
				
				player.sendPacketToViewersAndSelf(bookPacket);
			}
			
		}).delay((long) 1, TimeUnit.TICK).schedule();
		
		
		
	}
}
