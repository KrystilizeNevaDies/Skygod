package Skygod.Entities;

import java.util.Arrays;

import Skygod.Gradient;
import Skygod.Gradients;
import net.minestom.server.attribute.Attribute;
import net.minestom.server.attribute.AttributeModifier;
import net.minestom.server.attribute.AttributeOperation;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.Position;

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
	
	public Minion() {
		super(EntityType.ZOMBIE, new Position(0, 0, 0));
		this.setCustomName(Gradient.of(Gradients.MINION, "Minion lv. " + 0));
	}
	
	@Override
	public void spawn() {
		this.setGlowing(true);
	}
	
	@Override
	public void update(long time) {
		if (!instance.getPlayers().isEmpty()) {
			
			// Find nearest player
			Position playerPosition = new Position(0, 10000, 0);
			
			Player[] players = Arrays.copyOf(instance.getPlayers().toArray(), instance.getPlayers().size(), Player[].class);
			
			for (int i = 0; i < players.length; i++) {
				if (this.getPosition().getDistance(players[i].getPosition()) < this.getPosition().getDistance(playerPosition)) {
					playerPosition = players[i].getPosition();
				}
			}
			
			
			// Pathfind to player
			if (instance.getWorldAge() % 5 == 0)
				if (!(this.getPathPosition() == playerPosition))
					this.setPathTo(playerPosition);
			
			if (this.getPosition().getDistance(playerPosition) < 2)
				this.moveTowards(playerPosition, this.getAttributeValue(Attribute.fromKey("MOVEMENT_SPEED")));
		}
		
		// Do regular entitycreature updates
		super.update(time);
	}
}
