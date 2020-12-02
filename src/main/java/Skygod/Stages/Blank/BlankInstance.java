package skygod.stages.blank;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.metadata.WrittenBookMeta;
import net.minestom.server.item.metadata.WrittenBookMeta.WrittenBookGeneration;
import net.minestom.server.network.packet.server.play.OpenBookPacket;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.time.TimeUnit;
import skygod.Books;
import skygod.Gradient;
import skygod.Gradients;
import skygod.PlayerData;
import skygod.StageType;
import skygod.stages.BlankGenerator;
import skygod.stages.InstanceList;
import skygod.stages.SkygodInstance;
import skygod.stages.tutorial.TutorialInstance;

public class BlankInstance implements SkygodInstance {

	public static BlankInstance INSTANCE = new BlankInstance();
	
	@Override
	public Instance create(Player player) {
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
	
	@Override
	public String getName() {
		return "Blank";
	}
	
	public void playerSpawn(Instance instance, Player player) {
		
		player.teleport(new Position(0, 1, 0));
		
		player.getInventory().clear();
		
		ItemStack item = new ItemStack(Material.WRITTEN_BOOK, (byte) 1);
		
		WrittenBookMeta meta = (WrittenBookMeta) item.getItemMeta();
		
		Books.addPages(meta, Books.serverSelect);
		
		meta.setAuthor("Krystilize");
		
		meta.setGeneration(WrittenBookGeneration.ORIGINAL);
		
		meta.setResolved(false);
		
		meta.setTitle("A book");
		
		player.getInventory().addItemStack(item);		
		
		
		MinecraftServer.getSchedulerManager().buildTask(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				OpenBookPacket bookPacket = new OpenBookPacket();
				
				bookPacket.hand = Player.Hand.MAIN;
				
				player.sendPacketToSelf(bookPacket);
			}
			
		}).delay((long) 1, TimeUnit.TICK).schedule();
	}

	public void playerLeave(Instance playerInstance, Player player) {
		
	}

	public void playerChat(Instance playerInstance, Player player, PlayerChatEvent event) {
		switch (event.getMessage()) {
			case "home": {
				PlayerData.get(player).setCurrentStage(StageType.TUTORIAL);
				
				InstanceList.INSTANCE.removePlayerInstance(player);
				Instance instance = TutorialInstance.INSTANCE.create(player);
				InstanceList.INSTANCE.registerPlayerInstance(player, instance);
				
				player.getInventory().clear();
				
				player.setInstance(instance);
				break;
			}
			
			case "settings": {
				playerSettings(playerInstance, player);
				break;
			}
			
			case "exit": {
				player.kick(Gradient.of(Gradients.MINION, "Goodbye, thanks for playing!"));
				break;
			}
			
			default:
				break;
		}
		event.setCancelled(true);
	}


}
