package Skygod;

import java.util.HashMap;
import java.util.Map;

import net.minestom.server.entity.Player;

public class PlayerData {
	
	private static Map<Player, PlayerData> players = new HashMap<Player, PlayerData>();
	
	private PlayerData(Player player) {
		
	}
	
	public StageType getStage() {
		return StageType.NONE;
	}
	/**
	 * Gets a player's player data. Creates it if it doesnt exist.
	 * @param player
	 * @return PlayerData
	 */
	public static PlayerData get(Player player) {
		if (players.containsKey(player)) {
			return players.get(player);
		} else {
			players.put(player, new PlayerData(player));
			return players.get(player);
		}
	}
}
