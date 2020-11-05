package Skygod.EntitySpawners;

import java.util.Random;

import Skygod.Settings;
import Skygod.Instances.InstanceList;
import net.minestom.server.MinecraftServer;
import net.minestom.server.chat.ColoredText;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.pathfinding.PFPathingEntity;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;
import net.minestom.server.utils.time.TimeUnit;

public class TutorialSpawners {
	
	private Instance instance;
	
	private Random random;
	
	public TutorialSpawners(Instance constructorInstance) {
		instance = constructorInstance;
		instance.scheduleNextTick(inst -> {Tick();});
		random = new Random();
	}

	
	private void Tick() {
		if (instance.getCreatures().size() < Integer.valueOf((String) Settings.get().getSetting("EntityLimit"))) {
			Position entityPosition = new Position();
			
			entityPosition.setX((random.nextFloat() * 64) - 32);
			
			entityPosition.setY(100);
			
			entityPosition.setZ((random.nextFloat() * 64) - 32);
			
			// Spawn entity
			
			// System.out.println("Spawning Zombie at " + entityPosition.getX() + "|" + entityPosition.getY() + "|" + entityPosition.getZ());
			
			ZombieEntity entity = new ZombieEntity(entityPosition);
			
			entity.setInstance(instance);
			
			entity.teleport(entityPosition);
			
			// Set downwards velocity
			Vector velocity = new Vector(0, -50, 0);
			
			entity.setVelocity(velocity);
		}
		
		// Set Pathfind for all zombies
		// Updates every second
		
		if (instance.getWorldAge() % 20 == 0) {
			Position playerPosition = InstanceList.get().getInstancesPlayer(instance).getPosition();
			instance.getEntities().forEach(entity -> {
				if (entity instanceof ZombieEntity) {
					if (!(((ZombieEntity) entity).getPathPosition() == playerPosition) && (entity.getPosition().getDistance(playerPosition) < 32)) {
						((ZombieEntity) entity).setPathTo(playerPosition);
					}
				}
			});
		}
		
		MinecraftServer.getSchedulerManager().buildTask(new Runnable() {@Override public void run() {Tick();}}).delay(1, TimeUnit.TICK).schedule();
	}
	
	private class ZombieEntity extends EntityCreature {

		public ZombieEntity(Position spawnPosition) {
			super(EntityType.ZOMBIE, spawnPosition);
		}
		
		@Override
		public void spawn() {
			this.setGlowing(true);
			InstanceList.get().getInstancesPlayer(instance).sendActionBarMessage(ColoredText.of("Entities: " + instance.getEntities().size()));
		}
	}
	
}
