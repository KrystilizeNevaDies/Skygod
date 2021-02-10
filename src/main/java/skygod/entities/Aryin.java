package skygod.entities;

import java.util.Arrays;

import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.particle.Particle;
import net.minestom.server.particle.ParticleCreator;
import net.minestom.server.utils.Position;
import skygod.text.Gradient;
import skygod.text.Gradients;

public class Aryin extends EntityCreature {
	
	private int level;
	
	public Aryin(Instance instance, Position spawnPosition, Integer level) {
		super(EntityType.SKELETON, spawnPosition);
		this.level = level;
		this.setInstance(instance);
		this.setCustomName(Gradient.of(Gradients.MINION, "Minion lv. " + this.level));
		this.setCustomNameVisible(true);
	}
	
	private int fireballCooldown = 0;
	
	@Override
	public void spawn() {
		this.setGlowing(true);
	}
	
	@Override
	public void update(long time) {
		fireballCooldown -= time;
		if (!instance.getPlayers().isEmpty()) {
			
			// Find nearest player
			Player player = null;
			
			Player[] players = Arrays.copyOf(instance.getPlayers().toArray(), instance.getPlayers().size(), Player[].class);
			
			Position position = this.getPosition();
			
			for (int i = 0; i < players.length; i++) {
				if (player == null) {
					player = players[i];
					continue;
				}
				
				if (position.getDistance(players[i].getPosition()) < position.getDistance(player.getPosition())) {
					player = players[i];
					continue;
				}
			}
			
			double distance = position.getDistance(player.getPosition());
			
			// Pathfind to player
			if (instance.getWorldAge() % 5 == 0)
				this.setTarget(player);
			
			// Check for fireball
			if (distance < 10 && fireballCooldown < 1) {
				fireballCooldown = 0;
				fireball(player.getPosition());
			}
			
		}
		
		// Do regular entitycreature updates
		super.update(time);
	}

	private void fireball(Position playerPosition) {
		Position position = this.getPosition();
		
		// atan2f( delta.y, delta.x )
		float pitch = (float) Math.atan2((playerPosition.getY() - position.getY()), playerPosition.getX() - position.getX());
		
		// -asin( delta.normalized().z )
		float yaw = (float) -Math.asin(playerPosition.getZ() - position.getZ());
		
		position.setYaw(yaw);
		
		position.setPitch(pitch);
		
		// Vector velocity = playerPosition.clone().subtract(position.getX(), position.getY(), position.getZ()).toVector().normalize();
		
		Fireball ball = new Fireball(Block.MAGMA_BLOCK, position, this::fireballParticles, this::fireballEnd);
		
		ball.setInstance(this.getInstance());
		
	}
	
	private void fireballParticles(Fireball ball, Long time) {
		if (time > 30 ) {
			Position position = ball.getPosition();
			ServerPacket packet = ParticleCreator.createParticlePacket(Particle.FLAME, position.getX(), position.getY(), position.getZ(), 0, 0, 0, 1);
			ball.sendPacketToViewers(packet);
		}
	}
	
	private void fireballEnd(Fireball ball) {
		ball.remove();
	}
}
