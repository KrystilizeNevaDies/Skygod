package Skygod.Events;

import Skygod.ColorGradient;
import Skygod.Gradients;
import Skygod.Sound.PlayerSound;
import Skygod.Sound.Songs;
import Skygod.Stages.Tutorial.TutorialAdvancements;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.sound.Sound;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.time.TimeUnit;

public class PlayerSpawn {
	public static void Event(PlayerSpawnEvent event) {
		
		Player player = (Player) event.getEntity();
		Instance entityInstance = player.getInstance();
		int spawnY = 256;
		for (int i = 0; i < Chunk.CHUNK_SIZE_Y; i++) {
			if (entityInstance.getBlockStateId(0, i, 0) == Block.AIR.getBlockId()) {
				spawnY = i;
				break;
			}
		}
		
		player.teleport(new Position(0, spawnY, 0));
		
		PlayerSound.playSong(player, Songs.INTRO);
		
 		player.sendMessage(ColorGradient.of(Gradients.TUTORIAL, "Welcome to your very own generated world!"));
 		
 		MinecraftServer.getSchedulerManager().buildTask(
 				new Runnable() {
 					@Override public void run() {
 						player.sendMessage(ColorGradient.of(Gradients.TUTORIAL, "We would like to formally invite you to join us in the land of the gods."));
 						}
 					}
 				).delay(3, TimeUnit.SECOND).schedule();
 		
 		MinecraftServer.getSchedulerManager().buildTask(
 				new Runnable() {
 					@Override public void run() {
 						player.sendMessage(ColorGradient.of(Gradients.TUTORIAL, "However, you are insignificant. You will need to prove your worth to us gods."));
						TutorialAdvancements.get().addViewer(player);
						PlayerSound.playSound(player, Sound.UI_TOAST_CHALLENGE_COMPLETE);
 						}
 					}
 				).delay(6, TimeUnit.SECOND).schedule();
	}
}
