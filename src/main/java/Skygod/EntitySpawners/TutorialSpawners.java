package Skygod.EntitySpawners;

import Skygod.Settings;
import Skygod.Entities.Minion;
import Skygod.Instances.InstanceList;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.time.TimeUnit;

public class TutorialSpawners {
	
	private Instance instance;
	
	public TutorialSpawners(Instance constructorInstance) {
		instance = constructorInstance;
		instance.scheduleNextTick(inst -> {Tick();});
	}

	
	private void Tick() {
		Player player = InstanceList.get().getInstancesPlayer(instance);
		
		if (instance.getCreatures().size() < Integer.valueOf((String) Settings.get().getSetting("EntityLimit")) && player instanceof Player) {
			new Minion(instance, player.getPosition(), 1);
		}
		
		MinecraftServer.getSchedulerManager().buildTask(new Runnable() {@Override public void run() {Tick();}}).delay(1, TimeUnit.TICK).schedule();
	}
}
