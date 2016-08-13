package com.above.player;

import java.sql.SQLException;

import org.bukkit.entity.Player;

import com.above.data.DataTable;
import com.above.data.MySqlManager;
import com.above.exceptions.InvalidDataColumnException;
import com.above.interfaces.IDataProvider;

/**
 * Economy API for getting, setting and updating the player's balance
 * 
 * @author NathanGrad
 *
 */
public class EconomyAPI implements IDataProvider<Player, Double> {

	/**
	 * Obtain the balance as a string with the 'Crowns' suffix
	 * 
	 * @param player
	 * @return
	 * @throws NumberFormatException
	 * @throws InvalidDataColumnException
	 */
	public String getBalanceString(Player player) throws NumberFormatException, InvalidDataColumnException {
		return get(player) + " Crowns";
	}

	/**
	 * Pay an amount from their own balance into the balance of another player
	 * 
	 * @param playerFrom
	 *            The player to be debited
	 * @param playerTo
	 *            The player to be credited
	 * @param amount
	 *            The amount to be transferred
	 * @return
	 * @throws NumberFormatException
	 * @throws InvalidDataColumnException
	 */
	public boolean payPlayer(Player playerFrom, Player playerTo, double amount)
			throws NumberFormatException, InvalidDataColumnException {
		if (get(playerFrom) < amount) {
			return false;
		}
		update(playerFrom, 0 - amount);
		update(playerTo, amount);
		MySqlManager sql = new MySqlManager();
		try {
			sql.open();
			sql.executeNonQuery("INSERT INTO PaymentHistory (Sender, " + "Receiver, Amount, Date) VALUES ('"
					+ playerFrom.getUniqueId() + "','" + playerTo.getUniqueId() + "'," + amount + ", NOW())");
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Double get(Player player) {
		MySqlManager sql = new MySqlManager();
		try {
			sql.open();
			DataTable dt = sql.executeQuery("SELECT * FROM Users WHERE UUID='" + player.getUniqueId() + "'");
			sql.close();
			return Double.parseDouble(dt.getRows()[0].getCell("balance").toString());
		} catch (SQLException | NumberFormatException | InvalidDataColumnException e) {
			e.printStackTrace();
		}
		return 0.0;
	}

	@Override
	public void set(Player player, Double amount) {
		MySqlManager sql = new MySqlManager();
		try {
			sql.open();
			sql.executeNonQuery("UPDATE Users SET Balance=" + amount + " WHERE UUID='" + player.getUniqueId() + "'");
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Player player, Double amount) {
		MySqlManager sql = new MySqlManager();
		try {
			sql.open();
			sql.executeNonQuery(
					"UPDATE Users SET Balance=(Balance+(" + amount + ")) WHERE UUID='" + player.getUniqueId() + "'");
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setup(Player player, Double amount) {
		MySqlManager sql = new MySqlManager();
		try {
			sql.open();
			sql.executeNonQuery("INSERT INTO Users VALUES ('" + player.getUniqueId() + "'," + amount + ",0,NOW())");
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
