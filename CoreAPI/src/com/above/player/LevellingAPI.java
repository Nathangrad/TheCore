package com.above.player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.above.data.DataTable;
import com.above.data.MySqlManager;
import com.above.exceptions.InvalidDataColumnException;
import com.above.interfaces.IDataProvider;

/**
 * Levelling API for getting, setting and updating the player's XP
 * 
 * @author NathanGrad
 *
 */
public class LevellingAPI implements IDataProvider<Player, Integer> {

	private Map<Integer, Integer> levels = new HashMap<Integer, Integer>();

	public LevellingAPI() {
		Integer[] list = new Integer[] { 0, 1300, 2700, 4800, 7600, 11200, 15700, 21100, 27600, 35200, 43900, 53700,
				64700, 77000, 90600, 105600, 122000, 139800, 159100, 179900, 202300, 226300, 251800, 279000, 307900,
				338400, 370700, 404600, 440900 };
		for (byte i = 1; true; i++) {
			try {
				levels.put((int) i, list[i - 1]);
			} catch (IndexOutOfBoundsException ioobe) {
				break;
			}
		}
	}

	/**
	 * Get the level that the player currently is
	 * 
	 * @param player
	 * @return
	 */
	public int getLevel(Player player) {
		int xp = get(player);
		byte level = 1;
		for (byte i = 1; true; i++) {
			try {
				if (xp < Integer.parseInt(levels.get(i).toString())) {
					break;
				} else {
					level = i;
				}
			} catch (NullPointerException npe) {
				break;
			}
		}
		return level;
	}

	@Override
	public Integer get(Player player) {
		MySqlManager sql = new MySqlManager();
		try {
			sql.open();
			DataTable dt = sql.executeQuery("SELECT * FROM Users WHERE UUID='" + player.getUniqueId() + "'");
			sql.close();
			return Integer.parseInt(dt.getRows()[0].getCell("XP").toString());
		} catch (SQLException | NumberFormatException | InvalidDataColumnException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void set(Player player, Integer xp) {
		MySqlManager sql = new MySqlManager();
		try {
			sql.open();
			sql.executeNonQuery("UPDATE Users SET XP=" + xp + " WHERE UUID='" + player.getUniqueId() + "'");
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Player player, Integer xp) {
		MySqlManager sql = new MySqlManager();
		try {
			sql.open();
			sql.executeNonQuery("UPDATE Users SET XP=(XP+(" + xp + ")) WHERE UUID='" + player.getUniqueId() + "'");
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setup(Player player, Integer xp) {
		MySqlManager sql = new MySqlManager();
		try {
			sql.open();
			sql.executeNonQuery("INSERT INTO Users VALUES ('" + player.getUniqueId() + "',0," + xp + ",NOW())");
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
