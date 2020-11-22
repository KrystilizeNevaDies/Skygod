package Skygod;

import java.util.HashMap;
import java.util.Map;

import net.minestom.server.entity.Player;

public class PlayerData {
	
	private static Map<Player, PlayerData> players = new HashMap<Player, PlayerData>();
	
	// Saved playing stage
	private StageType playerStage;
	
	// Actual current stage
	private StageType playerCurrentStage;
	
	private PlayerData(Player player) {
		playerStage = StageType.TUTORIAL;
		playerCurrentStage = StageType.BLANK;
	}
	
	public StageType getStage() {
		return playerStage;
	}
	
	public StageType getCurrentStage() {
		return playerCurrentStage;
	}
	
	public void setCurrentStage(StageType stage) {
		playerCurrentStage = stage;
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
