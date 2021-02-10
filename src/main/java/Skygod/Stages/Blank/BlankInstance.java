package skygod.stages.blank;

import java.util.UUID;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.ChunkGenerator;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.metadata.WrittenBookMeta;
import net.minestom.server.item.metadata.WrittenBookMeta.WrittenBookGeneration;
import net.minestom.server.lock.Acquirable;
import net.minestom.server.network.packet.server.play.OpenBookPacket;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.time.TimeUnit;
import net.minestom.server.world.DimensionType;
import skygod.Books;
import skygod.PlayerData;
import skygod.stages.BlankGenerator;
import skygod.stages.InstanceList;
import skygod.stages.SkygodInstance;
import skygod.stages.StageType;
import skygod.stages.tutorial.TutorialInstance;
import skygod.text.Gradient;
import skygod.text.Gradients;

public class BlankInstance extends InstanceContainer implements SkygodInstance {

	private Player player;
	
	public BlankInstance(Player player) {
		super(UUID.randomUUID(), DimensionType.OVERWORLD, null);
		
		this.player = player;
		
		System.out.println("Creating new blank instance for " + player.getUsername());
		
		// Register instance
		MinecraftServer.getInstanceManager().registerInstance(this);
		
	    // Set the ChunkGenerator
		ChunkGenerator generator = new BlankGenerator();
		
		this.setChunkGenerator(generator);
	    
		// load minimal chunks
		this.scheduleNextTick((instance) -> {
			instance.loadChunk(0, 0);
			instance.loadChunk(-1, 0);
			instance.loadChunk(0, -1);
			instance.loadChunk(-1, -1);
		});
	}

	@Override
	public void playerLogin(PlayerLoginEvent event) {}

	@Override
	public void playerSpawn(PlayerSpawnEvent event) {
		event.getAcquirablePlayer().acquire((player) -> {

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
					OpenBookPacket bookPacket = new OpenBookPacket();
					
					bookPacket.hand = Player.Hand.MAIN;
					
					player.getPlayerConnection().sendPacket(bookPacket);
				}
				
			}).delay((long) 1, TimeUnit.TICK).schedule();
		});
	}

	@Override
	public void playerChat(PlayerChatEvent event) {
		Acquirable<Player> acqPlayer = event.getAcquirablePlayer();
		acqPlayer.acquire((player) -> {

			switch (event.getMessage()) {
				case "home": {
					player.sendTitleMessage(Gradient.of(Gradients.MINION, "Generating your domain"));
					
					// Create Instance
					TutorialInstance instance = new TutorialInstance(player);
					
					// Offload to new instance's tick
					instance.scheduleNextTick((thisInstance) -> {
						
						PlayerData.get(player).setCurrentStage(StageType.TUTORIAL);
						
						InstanceList.removePlayerInstance(acqPlayer);
						
						InstanceList.registerPlayerInstance(player, (SkygodInstance) thisInstance);
						
						player.getInventory().clear();
						
						player.setInstance(thisInstance);
					});
					break;
				}
				
				case "settings": {
					playerSettings(player);
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
		});
	}

	@Override
	public void playerDisconnect(PlayerDisconnectEvent event) {}

	@Override
	public Player getPlayer() {
		return this.player;
	}
}
