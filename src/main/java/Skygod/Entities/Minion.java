package Skygod.Entities;

import Skygod.ColorGradient;
import Skygod.Gradients;
import Skygod.Instances.InstanceList;
import net.minestom.server.MinecraftServer;
import net.minestom.server.attribute.Attribute;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.time.TimeUnit;

public class Minion extends EntityCreature{
	
	public Minion(Instance instance, Position spawnPosition, Integer level) {
		super(EntityType.ZOMBIE, spawnPosition);
		this.setInstance(instance);
		this.setAttribute(Attribute.MOVEMENT_SPEED, (float) (this.getAttributeValue(Attribute.MOVEMENT_SPEED) + (0.05 * (double) level)));
		this.setCustomName(ColorGradient.of(Gradients.MINION, "Minion lv. " + level));
		this.setCustomNameVisible(true);
	}
	
	@Override
	public void spawn() {
		this.setGlowing(true);
		this.getInstance().scheduleNextTick(instance -> {
			Tick(instance);
		});
	}
	
	private void Tick(Instance instance) {
		if (InstanceList.get().getInstancesPlayer(instance) instanceof Player) {
			Position playerPosition = InstanceList.get().getInstancesPlayer(instance).getPosition();
			
			if (instance.getWorldAge() % 5 == 0) {
				if (!(this.getPathPosition() == playerPosition) && (this.getPosition().getDistance(playerPosition) < 32)) {
					this.setPathTo(playerPosition);
				}
			}
			
			if (this.getPosition().getDistance(playerPosition) < 2) {
				this.moveTowards(playerPosition, this.getAttributeValue(Attribute.MOVEMENT_SPEED));
			}
		}
		MinecraftServer.getSchedulerManager().buildTask(new Runnable() {@Override public void run() {Tick(instance);}}).delay(1, TimeUnit.TICK).schedule();
	}
}
