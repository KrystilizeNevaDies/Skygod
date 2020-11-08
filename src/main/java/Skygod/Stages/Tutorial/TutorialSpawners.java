package Skygod.Stages.Tutorial;

import Skygod.Settings;
import Skygod.Entities.Minion;
import Skygod.Stages.InstanceList;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;

public class TutorialSpawners {
	
	private Instance instance;
	
	public TutorialSpawners(Instance newInstance) {
		instance = newInstance;
	}

	
	protected void tick() {
		Player player = InstanceList.get().getInstancesPlayer(instance);
		
		if (instance.getCreatures().size() < Integer.valueOf((String) Settings.get().getSetting("EntityLimit")) && player instanceof Player) {
			new Minion(instance, player.getPosition(), 1);
		}
	}
}
