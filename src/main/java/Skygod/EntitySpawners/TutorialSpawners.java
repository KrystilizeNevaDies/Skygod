package Skygod.EntitySpawners;

import java.util.Random;

import Skygod.Instances.InstanceList;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.Position;

public class TutorialSpawners {
	
	private Instance instance;
	
	private Random random;
	
	public TutorialSpawners(Instance constructorInstance) {
		instance = constructorInstance;
		instance.scheduleNextTick(inst -> {Tick();});
		random = new Random();
	}
	
	private void Tick() {
		Player player = InstanceList.get().getInstancesPlayer(instance);
		if (player == null) 
			return;
		Position playerPosition = player.getPosition();
		if (player.getAliveTicks() % 600 == 300) {
			
			ZombieEntity entity = new ZombieEntity(playerPosition);
			
			entity.setInstance(instance);
			
			entity.teleport(playerPosition);
		}
		
		instance.scheduleNextTick(inst -> {Tick();});
	}
	
	private class ZombieEntity extends EntityCreature {

		public ZombieEntity(Position spawnPosition) {
			super(EntityType.ZOMBIE, spawnPosition);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void update(long time) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void spawn() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
