package com.above.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Allow the creation of a simple bordered inventory
 * 
 * @author NathanGrad
 *
 */
public class InventoryHelper {

	private static ItemStack blackPane;

	/**
	 * Create an inventory with 54 slots and a border
	 * 
	 * @param name
	 *            The name of the inventory
	 * @return
	 */
	public static Inventory createInventory(String name) {
		blackPane = ItemHelper.setName(ItemHelper.getItem(Material.STAINED_GLASS_PANE, (byte) 1, (short) 15), "");
		Inventory inv = Bukkit.getServer().createInventory(null, 54, name);
		for (byte i = 0; i < 54; i++) {
			inv.setItem(i, blackPane);
		}
		Integer[] list = new Integer[] { 17, 18, 26, 27, 35, 36 };
		for (byte i = 10; i < 44; i++) {
			boolean bad = false;
			for (int x : list) {
				if (i == x) {
					bad = true;
				}
			}
			if (!bad) {
				inv.setItem(i, null);
			}
		}
		return inv;
	}

}
