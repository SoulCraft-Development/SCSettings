package com.soulcraft.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import com.soulcraft.Data.SCSettingsManager;
import com.soulcraft.GUI.AbstractMenu;

/**
 * Super class used for all EventHandler classes.
 *
 * @author Sommod
 * @version 1.0
 *
 */
public abstract class AbstractHandler<T extends AbstractMenu> {

	private InventoryClickEvent event;
	private SCSettingsManager manager;
	private T menu;
	
	public AbstractHandler(InventoryClickEvent event, SCSettingsManager manager, T menu) {
		this.manager = manager;
		this.event = event;
		this.menu = menu;
	}
	
	/**
	 * Gets the manager class of the plugin.
	 * @return SCSettingsManager
	 */
	protected SCSettingsManager getManager() { return manager; }
	
	/**
	 * Gets the ClickEvent that contains all
	 * the stored data.
	 * @return InventoryClickEVent
	 */
	public InventoryClickEvent getEvent() { return event; }
	
	/**
	 * Gets the player who cliked in the inventory.
	 * @return Player
	 */
	public Player getPlayer() { return (Player) event.getWhoClicked(); }
	
	/**
	 * Gets the Top Inventory of the event.
	 * @return Top Inventory
	 */
	public Inventory getTopInventory() { return event.getInventory(); }
	
	/**
	 * Checks if the InvetnroryClickEvent was performed on the top
	 * inventory
	 * @return True - if clicked on the top inventory
	 */
	public boolean isTopInventory() { return event.getClickedInventory() == null ? false : event.getClickedInventory().equals(getTopInventory()); }
	
	/**
	 * Checks if the clicked section of the inventory was done on the outside.
	 * @return True - if clicked outside the inventories
	 */
	public boolean clickedOutside() { return event.getClickedInventory() == null; }
	
	/**
	 * Gets the menu that is associated with handling the
	 * action of the menu.
	 * @return Instance of AbstractMenu
	 */
	public T getMenu() { return menu; }
}
