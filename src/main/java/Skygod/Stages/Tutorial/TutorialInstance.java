package skygod.stages.tutorial;

import java.util.Random;
import java.util.UUID;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.instance.InstanceTickEvent;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.particle.Particle;
import net.minestom.server.particle.ParticleCreator;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.Vector;
import net.minestom.server.world.DimensionType;
import skygod.entities.Fireball;
import skygod.sound.MidiSong;
import skygod.stages.SkygodInstance;

public class TutorialInstance extends InstanceContainer implements SkygodInstance {
	
	private Player player;
	
	public TutorialInstance(Player player) {
		super(UUID.randomUUID(), DimensionType.OVERWORLD, null);
		
		this.player = player;
		
		System.out.println("Creating new tutorial instance for " + player.getUsername());
		
		// Register instance
		MinecraftServer.getInstanceManager().registerInstance(this);
		
		// Create Generator
		TutorialGenerator generator = new TutorialGenerator(player, this);
		
		// Set Chunk Generator
		this.setChunkGenerator(generator.chunkGenerator);
		this.enableAutoChunkLoad(false);
		
		// Offload chunk loading to instance thread
		this.scheduleNextTick((thisInstance) -> {
			// Load chunks
			for (int x = -10; x < 10; x++) {
				for (int y = -10; y < 10; y++) {
					this.loadChunk(x, y);
				}	
			}
			
			// Do finishers
			generator.finisher.applyFinishers();
		});
		
		// Set Entity Spawner
		new TutorialSpawners(this);
		
		// Set Advancements
		new TutorialAdvancements(this);
		
		// Set tick event
		this.addEventCallback(InstanceTickEvent.class, this::tick);
	}
	
	private void tick(InstanceTickEvent event) {
		if (this.getWorldAge() % 50 == 0) {
			// new Aryin(this, player.getPosition(), 20);
		}
	}

	@Override
	public Player getPlayer() {
		return this.player;
	}

	public void playerLogin(PlayerLoginEvent event) {
		
	}

	public void playerSpawn(PlayerSpawnEvent event) {
		event.getAcquirablePlayer().acquire((player) -> {
			player.teleport(new Position(0, 256, 0));
			player.setGameMode(GameMode.CREATIVE);
			player.setAllowFlying(true);
			
			MidiSong song = new MidiSong();
			
			song.addPlayer(player);
			
			song.setEndScript((midiSong) -> {
				playRandom(song);
			});
			
			playRandom(song);
			
		});
	}
	
	public void playerBlockInteract(PlayerBlockInteractEvent event) {
		event.getAcquirablePlayer().acquire((player) -> {
			// player
		});
	}
	
	private Random songRandom = new Random();
	
	private void playRandom(MidiSong song) {
		switch (songRandom.nextInt(3)) {
		case 0:
			song.setMidiFile("Songs/ZZZ.mid");
			break;
		case 1:
			song.setMidiFile("Songs/AOT.mid");
			break;
		case 2:
			song.setMidiFile("Songs/ThisGame.mid");
			break;
		}
		song.play();
	}
	
	
	public void playerUseItem(PlayerUseItemEvent event) {
		event.getAcquirablePlayer().acquire((player) -> {
			Position position = player.getPosition();
			
			Vector velocity = position.getDirection().normalize().multiply(Math.random());
			
			this.scheduleNextTick((instance) -> {
				Fireball ball = new Fireball(
						Block.MAGMA_BLOCK,
						player.getPosition().clone().add(0, player.getEyeHeight(), 0),
						(fireball, time) -> {fireballUpdate(fireball, time, velocity);},
						this::fireballEnd);
				
				ball.setInstance(instance);
			});
		});
	}
	
	private void fireballUpdate(Fireball ball, Long time, Vector velocity) {
		Position position = ball.getPosition();
		ServerPacket packet = ParticleCreator.createParticlePacket(Particle.FLAME, position.getX(), position.getY(), position.getZ(), 0, 0, 0, 1);
		ball.sendPacketToViewers(packet);
    	
    	ball.getPosition().add(velocity.toPosition());
	}
	
	private void fireballEnd(Fireball ball) {
		Random random = new Random();
		
		
		
		for (int i = 0; i < 200; i++) {
			
			double xOffset = Math.pow(random.nextDouble() - 0.5, 3) * 30.0;
			double yOffset = Math.pow(random.nextDouble() - 0.5, 3) * 30.0;
			double zOffset = Math.pow(random.nextDouble() - 0.5, 3) * 30.0;
			
			Position position = ball.getPosition().clone().add(new Position(xOffset, yOffset, zOffset));
			ServerPacket packet = ParticleCreator
			.createParticlePacket(
				Particle.SOUL_FIRE_FLAME, 
				position.getX(), position.getY(), position.getZ(), 
				0, 0, 0, 5
			);
			ball.sendPacketToViewers(packet);
		}
	}
}
