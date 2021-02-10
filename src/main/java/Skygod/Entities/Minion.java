package skygod.entities;

import java.util.Arrays;

import net.minestom.server.attribute.Attribute;
import net.minestom.server.attribute.AttributeModifier;
import net.minestom.server.attribute.AttributeOperation;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.Position;
import skygod.text.Gradient;
import skygod.text.Gradients;

public class Minion extends EntityCreature{
	
	public Minion(Instance instance, Position spawnPosition, Integer level) {
		super(EntityType.ZOMBIE, spawnPosition);
		this.setInstance(instance);
		// Set speed
		this.getAttribute(Attribute.fromKey("MOVEMENT_SPEED")).addModifier(new AttributeModifier("SpeedFromLevel", (float) (0.05 * (float) level), AttributeOperation.ADDITION));
		// Set attack
		this.getAttribute(Attribute.fromKey("ATTACK")).addModifier(new AttributeModifier("SpeedFromLevel", (float) (0.05 * (float) level), AttributeOperation.ADDITION));
		this.setCustomName(Gradient.of(Gradients.MINION, "Minion lv. " + level));
		this.setCustomNameVisible(true);
	}
	
	@Override
	public void spawn() {
		this.setGlowing(true);
	}
	
	@Override
	public void update(long time) {
		if (!instance.getPlayers().isEmpty()) {
			
			// Find nearest player
			Player player = null;
			
			Player[] players = Arrays.copyOf(instance.getPlayers().toArray(), instance.getPlayers().size(), Player[].class);
			
			for (int i = 0; i < players.length; i++) {
				if (player == null) {
					player = players[i];
					continue;
				}
				
				if (getPosition().getDistance(players[i].getPosition()) < getPosition().getDistance(player.getPosition())) {
					player = players[i];
					continue;
				}
			}
			
			
			// Pathfind to player
			if (instance.getWorldAge() % 5 == 0)
				this.setTarget(player);
		}
		
		// Do regular entitycreature updates
		super.update(time);
	}
}
