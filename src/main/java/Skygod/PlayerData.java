package skygod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minestom.server.entity.Player;
import skygod.stages.StageType;

public class PlayerData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Map<Player, PlayerData> players = new HashMap<Player, PlayerData>();
	
	/**
	 * Gets a player's player data. Creates it if it doesnt exist.
	 * @param player
	 * @return PlayerData
	 */
	public static PlayerData get(Player player) {
		if (players.containsKey(player)) {
			return players.get(player);
		} else {
			PlayerData data = loadPlayerData(player); 
			if (data == null) {
				data = new PlayerData(player);
			}
			players.put(player, data);
			return players.get(player);
		}
	}
	
	public static void saveAll() {
		players.forEach((player, playerData) -> {
			savePlayerData(player);
		});
		
	}
	
	public static void savePlayerData(Player player) {
		UUID uuid = player.getUuid();
		
		String output = "PlayerData/" + uuid.toString() + ".playerdata";
		
		System.out.println("Saving " + player.getUsername() + "'s data to " + output);
		try {
			new File("PlayerData/").mkdirs();
			FileOutputStream fileOut = new FileOutputStream(output);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(PlayerData.get(player));
	        out.close();
	        fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static PlayerData loadPlayerData(Player player) {
		try {
			FileInputStream fileOut = new FileInputStream("PlayerData/" + player.getUuid().toString());
			ObjectInputStream in = new ObjectInputStream(fileOut);
	        PlayerData data = (PlayerData) in.readObject();
	        fileOut.close();
	        in.close();
	        return data;
		} catch (Exception e) {
			return null;
		}
	}
	
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
	
	public void setStage(StageType stage) {
		playerStage = stage;
	}
	
	public StageType getCurrentStage() {
		return playerCurrentStage;
	}
	
	public void setCurrentStage(StageType stage) {
		playerCurrentStage = stage;
	}
}
