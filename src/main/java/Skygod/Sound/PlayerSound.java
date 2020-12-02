package skygod.sound;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.sound.Sound;
import net.minestom.server.sound.SoundCategory;
import net.minestom.server.utils.time.TimeUnit;

public class PlayerSound {
	
	public static void playSound(Player player, Sound sound) {
		playSound(player, sound, 1, 1);
	};
	
	public static void playSound(Player player, Sound sound, float volume) {
		playSound(player, sound, volume, 1);
	};
	
	public static void playSound(Player player, Sound sound, float volume, float pitch) {
		player.playSound(sound, SoundCategory.MASTER, volume, pitch);
	};
	
	public static void playSong(Player player, Song song) {
		song.notes.forEach(note -> {
			MinecraftServer.getSchedulerManager().buildTask(new Runnable() {@Override public void run() {
				if (player.isOnline()) playSound(player, Sound.fromId((int) note[3]), note[2], note[1]);
				}}).delay((int) note[0], TimeUnit.TICK).schedule();
		});
	};
}
