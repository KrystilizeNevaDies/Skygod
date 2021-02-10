package skygod.stages;

import net.minestom.server.entity.Player;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.entity.EntityDamageEvent;
import net.minestom.server.event.entity.EntityDeathEvent;
import net.minestom.server.event.entity.EntityFireEvent;
import net.minestom.server.event.entity.EntityItemMergeEvent;
import net.minestom.server.event.entity.EntitySpawnEvent;
import net.minestom.server.event.entity.EntityTickEvent;
import net.minestom.server.event.entity.EntityVelocityEvent;
import net.minestom.server.event.instance.AddEntityToInstanceEvent;
import net.minestom.server.event.instance.InstanceChunkLoadEvent;
import net.minestom.server.event.instance.InstanceChunkUnloadEvent;
import net.minestom.server.event.instance.InstanceTickEvent;
import net.minestom.server.event.instance.RemoveEntityFromInstanceEvent;
import net.minestom.server.event.inventory.InventoryClickEvent;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryOpenEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.event.item.ArmorEquipEvent;
import net.minestom.server.event.item.PickupExperienceEvent;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.event.player.AdvancementTabEvent;
import net.minestom.server.event.player.PlayerAddItemStackEvent;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;
import net.minestom.server.event.player.PlayerChangeHeldSlotEvent;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.player.PlayerChunkLoadEvent;
import net.minestom.server.event.player.PlayerChunkUnloadEvent;
import net.minestom.server.event.player.PlayerCommandEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerEatEvent;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.event.player.PlayerHandAnimationEvent;
import net.minestom.server.event.player.PlayerItemAnimationEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerMoveEvent;
import net.minestom.server.event.player.PlayerPluginMessageEvent;
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
import net.minestom.server.event.player.PlayerTickEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;
import net.minestom.server.event.player.UpdateTagListEvent;

public interface SkygodInstance {
	
	public Player getPlayer();
	
	public default void playerSettings(Player player) {
		// TODO: Player settings gui
	}
	
	// Player Events
	public default void advancementTab(AdvancementTabEvent event) {}
	public default void playerAddItemStack(PlayerAddItemStackEvent event) {}
	public default void playerBlockBreak(PlayerBlockBreakEvent event) {}
	public default void playerBlockInteract(PlayerBlockInteractEvent event) {}
	public default void playerBlockPlace(PlayerBlockPlaceEvent event) {}
	public default void playerChangeHeldSlot(PlayerChangeHeldSlotEvent event) {}
	public default void playerChat(PlayerChatEvent event) {}
	public default void playerChunkLoad(PlayerChunkLoadEvent event) {}
	public default void playerChunkUnload(PlayerChunkUnloadEvent event) {}
	public default void playerCommand(PlayerCommandEvent event) {}
	public default void playerDisconnect(PlayerDisconnectEvent event) {}
	public default void playerEat(PlayerEatEvent event) {}
	public default void playerEntityInteract(PlayerEntityInteractEvent event) {}
	public default void playerHandAnimation(PlayerHandAnimationEvent event) {}
	public default void playerItemAnimation(PlayerItemAnimationEvent event) {}
	public default void playerLogin(PlayerLoginEvent event) {}
	public default void playerMove(PlayerMoveEvent event) {}
	public default void playerPluginMessage(PlayerPluginMessageEvent event) {}
	public default void playerPreEat(PlayerPreEatEvent event) {}
	public default void playerResourcePackStatus(PlayerResourcePackStatusEvent event) {}
	public default void playerRespawn(PlayerRespawnEvent event) {}
	public default void playerSetItemStack(PlayerSetItemStackEvent event) {}
	public default void playerSettingsChange(PlayerSettingsChangeEvent event) {}
	public default void playerSkinInit(PlayerSkinInitEvent event) {}
	public default void playerSpawn(PlayerSpawnEvent event) {}
	public default void playerStartDigging(PlayerStartDiggingEvent event) {}
	public default void playerStartFlying(PlayerStartFlyingEvent event) {}
	public default void playerStopFlying(PlayerStopFlyingEvent event) {}
	public default void playerSwapItem(PlayerSwapItemEvent event) {}
	public default void playerTick(PlayerTickEvent event) {}
	public default void playerUseItem(PlayerUseItemEvent event) {}
	public default void playerUseItemOnBlock(PlayerUseItemOnBlockEvent event) {}
	
	
	// Item events
	public default void armorEquipEvent(ArmorEquipEvent event) {}
	public default void pickupExperienceEvent(PickupExperienceEvent event) {}
	public default void pickupItemEvent(PickupItemEvent event) {}
	
	// Inventory events
	public default void inventoryClickEvent(InventoryClickEvent event) {}
	public default void inventoryCloseEvent(InventoryCloseEvent event) {}
	public default void inventoryOpenEvent(InventoryOpenEvent event) {}
	public default void inventoryPreClickEvent(InventoryPreClickEvent event) {}
	
	// Instance events
	public default void addEntityToInstanceEvent(AddEntityToInstanceEvent event) {}
	public default void instanceChunkLoadEvent(InstanceChunkLoadEvent event) {}
	public default void instanceChunkUnloadEvent(InstanceChunkUnloadEvent event) {}
	public default void instanceTickEvent(InstanceTickEvent event) {}
	public default void removeEntityFromInstanceEvent(RemoveEntityFromInstanceEvent event) {}
	
	// Entity events
	public default void entityAttackEvent(EntityAttackEvent event) {}
	public default void entityDamageEvent(EntityDamageEvent event) {}
	public default void entityDeathEvent(EntityDeathEvent event) {}
	public default void entityFireEvent(EntityFireEvent event) {}
	public default void entityItemMergeEvent(EntityItemMergeEvent event) {}
	public default void entitySpawnEvent(EntitySpawnEvent event) {}
	public default void entityTickEvent(EntityTickEvent event) {}
	public default void entityVelocityEvent(EntityVelocityEvent event) {}
	
	// Misc or unknown
	public default void updateTagList(UpdateTagListEvent event) {}
}
