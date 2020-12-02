package skygod.stages.tutorial;

import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import skygod.Settings;
import skygod.entities.Minion;
import skygod.stages.InstanceList;

public class TutorialSpawners {
	
	private Instance instance;
	
	public TutorialSpawners(Instance newInstance) {
		instance = newInstance;
	}

	
	protected void tick() {
		Player player = InstanceList.INSTANCE.getInstancesPlayer(instance);
		
		if (instance.getCreatures().size() < Integer.valueOf((String) Settings.get().getSetting("EntityLimit")) && player instanceof Player) {
			new Minion(instance, player.getPosition(), 1);
		}
	}
}
