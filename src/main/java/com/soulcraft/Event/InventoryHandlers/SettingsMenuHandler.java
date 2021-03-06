package com.soulcraft.Event.InventoryHandlers;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.soulcraft.Data.Perms;
import com.soulcraft.Data.SCSettingsManager;
import com.soulcraft.Event.AbstractHandler;
import com.soulcraft.GUI.SettingsMenu;
import com.soulcraft.Player.PlayerData;

/**
 * Handles the Settings Menu actions.
 *
 * @author Sommod
 * @version 1.0
 *
 */
public class SettingsMenuHandler extends AbstractHandler<SettingsMenu> {

	public SettingsMenuHandler(InventoryClickEvent event, SCSettingsManager manager) {
		super(event, manager, manager.getGuiManager().getSettingsMenu());
	}
	
	@Override
	public void execute() {
		getEvent().setCancelled(true); // Defaulted to cancel every action
		
		if(isTopInventory()) {
			if(getMenu().isButton(getEvent().getSlot())) {
				if(openMenu())
					return;
				
				switch (getMenu().getButton(getEvent().getSlot())) {
				case ACCEPT_ALL:
				case CONFIRM:
				case NEXT_PAGE:
				case PREVIOUS_PAGE:
				case DECLINE_ALL:
					return;
					
				case ERROR:
				default:
					getPlayer().sendMessage("§Unknown Error, please contact the Administration. Error Code: SHM-21.");
					break;
				}
			} else {
				PlayerData data = getManager().getPlayerManager().getPlayerData(getPlayer());
				
				switch (getEvent().getSlot()) {
				case 10:
					if(getPlayer().hasPermission(Perms.DEATH_MESSAGES.toString()))
							data.getChatSettings().setDeathMessages(!data.getChatSettings().isSeeingDeathMessages());
					else
						getPlayer().sendMessage(getManager().getFileManager().getConfig("config").getString("friend.no permission").replace('&', '§'));
					break;
					
				case 11:
					if(getPlayer().hasPermission(Perms.FRIEND_JOIN.toString()))
						data.getChatSettings().setNotifyFriendJoin(!data.getChatSettings().isNotifiedFriendJoin());
					else
						getPlayer().sendMessage(getManager().getFileManager().getConfig("config").getString("friend.no permission").replace('&', '§'));
					break;

				case 12:
					if(getPlayer().hasPermission(Perms.ALLOW_REQUESTS.toString()))
						data.getChatSettings().setAllowFriendRequets(!data.getChatSettings().isAllowingFriendRequest());
					else
						getPlayer().sendMessage(getManager().getFileManager().getConfig("config").getString("friend.no permission").replace('&', '§'));
					break;
					
				case 13:
					if(getPlayer().hasPermission(Perms.IGNORE_CHAT.toString()))
						data.getChatSettings().setIgnoreGlobalChat(!data.getChatSettings().isIgnoringGlobalChat());
					else
						getPlayer().sendMessage(getManager().getFileManager().getConfig("config").getString("friend.no permission").replace('&', '§'));
					break;
					
				default:
					return;
				}
				
				ItemStack change = getEvent().getInventory().getItem(getEvent().getSlot() + 9);
				
				if(change.getType() == Material.GRAY_DYE)
					change.setType(Material.LIME_DYE);
				else
					change.setType(Material.GRAY_DYE);
				
				getEvent().getInventory().setItem(getEvent().getSlot() + 9, change); // Does not check for ArrayOutOfBounds Exception... Beware
			}
		}
	}

}
