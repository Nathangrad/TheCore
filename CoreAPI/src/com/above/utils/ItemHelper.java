package com.above.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Handles creation of items as well as their names and lores
 * 
 * @author NathanGrad
 *
 */
public class ItemHelper {

	/**
	 * Create a new ItemStack
	 * 
	 * @param material
	 *            The material of the item
	 * @param quantity
	 *            The quantity of the item
	 * @param type
	 *            The type data of the item
	 * @return
	 */
	public static ItemStack getItem(Material material, byte quantity, short type) {
		return new ItemStack(material, quantity, type);
	}

	/**
	 * Set the lore of the item
	 * 
	 * @param item
	 *            The item
	 * @param lines
	 *            The lore array
	 * @return
	 */
	public static ItemStack setLore(ItemStack item, String[] lines) {
		ItemMeta itemM = item.getItemMeta();
		List<String> result = new ArrayList<String>();
		int i = 0;
		while (true) {
			try {
				result.add(lines[i++]);
			} catch (IndexOutOfBoundsException ioobe) {
				break;
			}
		}
		itemM.setLore(result);
		item.setItemMeta(itemM);
		return item;
	}

	/**
	 * Set the name of the item
	 * 
	 * @param item
	 *            The item
	 * @param name
	 *            The name
	 * @return
	 */
	public static ItemStack setName(ItemStack item, String name) {
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(name);
		item.setItemMeta(itemM);
		return item;
	}

}
