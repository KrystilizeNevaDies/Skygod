package skygod.events;

import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AdvancementTabEvent;
import net.minestom.server.event.player.PlayerAddItemStackEvent;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.player.PlayerChangeHeldSlotEvent;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.player.PlayerCommandEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerEatEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.event.player.PlayerHandAnimationEvent;
import net.minestom.server.event.player.PlayerItemAnimationEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerMoveEvent;
import net.minestom.server.event.player.PlayerPreEatEvent;
import net.minestom.server.event.player.PlayerResourcePackStatusEvent;
import net.minestom.server.event.player.PlayerRespawnEvent;
import net.minestom.server.event.player.PlayerSetItemStackEvent;
import net.minestom.server.event.player.PlayerSettingsChangeEvent;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.event.player.PlayerStartDiggingEvent;
import net.minestom.server.event.player.PlayerStartFlyingEvent;
import net.minestom.server.event.player.PlayerStopFlyingEvent;
import net.minestom.server.event.player.PlayerSwapItemEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;
import net.minestom.server.lock.Acquirable;
import skygod.stages.InstanceList;
import skygod.stages.SkygodInstance;

/**
 * 
 * Generalised events
 * 
 * If you need to add extra logic to an event, create a new file and remove the callback here as seen in PlayerLoginEvent.
 * 
 * These generalised events are designed to work with different instances.
 * 
 * @author Krystilize
 *
 */

public class Events {
	public static void registerPlayerEvents(Player player) {
		
		
		
		// Static events
		player.addEventCallback(PlayerLoginEvent.class, event -> {PlayerLogin.Event(event);});
        player.addEventCallback(PlayerDisconnectEvent.class, event -> {PlayerDisconnect.Event(event);});
        
        // Instance events
    	player.addEventCallback(AdvancementTabEvent.class, Events::advancementTabEvent);
    	player.addEventCallback(PlayerAddItemStackEvent.class, Events::playerAddItemStackEvent);
    	player.addEventCallback(PlayerBlockBreakEvent.class, Events::playerBlockBreakEvent);
    	player.addEventCallback(PlayerBlockInteractEvent.class, Events::playerBlockInteractEvent);
    	player.addEventCallback(PlayerBlockPlaceEvent.class, Events::playerBlockPlaceEvent);
    	player.addEventCallback(PlayerChangeHeldSlotEvent.class, Events::playerChangeHeldSlotEvent);
    	player.addEventCallback(PlayerChatEvent.class, Events::playerChatEvent);
    	player.addEventCallback(PlayerCommandEvent.class, Events::playerCommandEvent);
    	player.addEventCallback(PlayerEatEvent.class, Events::playerEatEvent);
    	player.addEventCallback(PlayerEntityInteractEvent.class, Events::playerEntityInteractEvent);
    	player.addEventCallback(PlayerHandAnimationEvent.class, Events::playerHandAnimationEvent);
    	player.addEventCallback(PlayerItemAnimationEvent.class, Events::playerItemAnimationEvent);
    	player.addEventCallback(PlayerMoveEvent.class, Events::playerMoveEvent);
    	player.addEventCallback(PlayerPreEatEvent.class, Events::playerPreEatEvent);
    	player.addEventCallback(PlayerResourcePackStatusEvent.class, Events::playerResourcePackStatusEvent);
    	player.addEventCallback(PlayerRespawnEvent.class, Events::playerRespawnEvent);
    	player.addEventCallback(PlayerSetItemStackEvent.class, Events::playerSetItemStackEvent);
    	player.addEventCallback(PlayerSettingsChangeEvent.class, Events::playerSettingsChangeEvent);
    	player.addEventCallback(PlayerSkinInitEvent.class, Events::playerSkinInitEvent);
    	player.addEventCallback(PlayerSpawnEvent.class, Events::playerSpawnEvent);
    	player.addEventCallback(PlayerStartDiggingEvent.class, Events::playerStartDiggingEvent);
    	player.addEventCallback(PlayerStartFlyingEvent.class, Events::playerStartFlyingEvent);
    	player.addEventCallback(PlayerStopFlyingEvent.class, Events::playerStopFlyingEvent);
    	player.addEventCallback(PlayerSwapItemEvent.class, Events::playerSwapItemEvent);
    	player.addEventCallback(PlayerUseItemEvent.class, Events::playerUseItemEvent);
    	player.addEventCallback(PlayerUseItemOnBlockEvent.class, Events::playerUseItemOnBlockEvent);
	}
	
	private static void playerUseItemOnBlockEvent(PlayerUseItemOnBlockEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerUseItemOnBlock(event);
	}

	private static void playerUseItemEvent(PlayerUseItemEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerUseItem(event);
	}

	private static void playerSwapItemEvent(PlayerSwapItemEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerSwapItem(event);
	}

	private static void playerStopFlyingEvent(PlayerStopFlyingEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerStopFlying(event);
	}

	private static void playerStartFlyingEvent(PlayerStartFlyingEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerStartFlying(event);
	}

	private static void playerStartDiggingEvent(PlayerStartDiggingEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerStartDigging(event);
	}

	private static void playerSpawnEvent(PlayerSpawnEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerSpawn(event);
	}

	private static void playerSkinInitEvent(PlayerSkinInitEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerSkinInit(event);
	}

	private static void playerSettingsChangeEvent(PlayerSettingsChangeEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerSettingsChange(event);
	}

	private static void playerSetItemStackEvent(PlayerSetItemStackEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerSetItemStack(event);
	}

	private static void playerRespawnEvent(PlayerRespawnEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerRespawn(event);
	}

	private static void playerResourcePackStatusEvent(PlayerResourcePackStatusEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerResourcePackStatus(event);
	}

	private static void playerPreEatEvent(PlayerPreEatEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerPreEat(event);
	}

	private static void playerMoveEvent(PlayerMoveEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerMove(event);
	}

	private static void playerItemAnimationEvent(PlayerItemAnimationEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerItemAnimation(event);
	}

	private static void playerHandAnimationEvent(PlayerHandAnimationEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerHandAnimation(event);
	}

	private static void playerEntityInteractEvent(PlayerEntityInteractEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerEntityInteract(event);
	}

	private static void playerEatEvent(PlayerEatEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerEat(event);
	}

	private static void playerCommandEvent(PlayerCommandEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerCommand(event);
	}

	private static void playerChangeHeldSlotEvent(PlayerChangeHeldSlotEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerChangeHeldSlot(event);
	}

	private static void playerBlockPlaceEvent(PlayerBlockPlaceEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerBlockPlace(event);
	}

	private static void playerBlockInteractEvent(PlayerBlockInteractEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerBlockInteract(event);
	}

	private static void playerBlockBreakEvent(PlayerBlockBreakEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerBlockBreak(event);
	}

	private static void playerAddItemStackEvent(PlayerAddItemStackEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerAddItemStack(event);
	}

	private static void advancementTabEvent(AdvancementTabEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.advancementTab(event);
	}

	private static void playerChatEvent(PlayerChatEvent event) {
		Acquirable<Player> player = event.getAcquirablePlayer();
		
		SkygodInstance instance = InstanceList.getPlayerInstance(player);
		
		if (instance != null) instance.playerChat(event);
	}
}
