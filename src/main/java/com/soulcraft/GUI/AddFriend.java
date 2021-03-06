package com.soulcraft.GUI;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.soulcraft.Data.SCSettingsManager;
import com.soulcraft.Player.PlayerData;

/**
 * Handles the menu for displaying players and allow you to add them as
 * a friend.
 *
 * @author Sommod
 * @version 1.0
 *
 */
public class AddFriend extends AbstractMenu {

	private Map<Player, Integer> playerPage;
	private List<OfflinePlayer> allPlayers;
	
	public AddFriend(SCSettingsManager manager) {
		super(manager, new File(manager.getPlugin().getDataFolder(), "Data/Gui Data/Add Friend.yml"));
		playerPage = new HashMap<Player, Integer>();
		allPlayers = new ArrayList<OfflinePlayer>();
		Arrays.asList(manager.getPlugin().getServer().getOfflinePlayers()).forEach(off -> { allPlayers.add(off); });
	}
	
	@Override
	public void open(Player player) {
		openNextPage(player, 0);
	}
	
	public void openNextPage(Player player, int page) {
		Inventory toChange = getBaseInventory();
		ItemStack head = new ItemStack(Material.PLAYER_HEAD);
		
		if(page * 28 > allPlayers.size() || page < 0)
			return;
		
		for(int i = 10, k = (page * 28); i < getBaseInventory().getSize() - 10 && k < allPlayers.size(); i++, k++) {
			if((i + 1) % 9 == 0) {
				i++;
				continue;
			}
			
			player.getOpenInventory().getTopInventory().setItem(i, null);
		}
		
		for(int i = 10, k = (page * 28); i < getBaseInventory().getSize() - 10 && k < allPlayers.size(); i++, k++) {
			if((i + 1) % 9 == 0)
				i += 2;
			
			SkullMeta meta = (SkullMeta) head.getItemMeta();
			meta.setDisplayName("§b§l" + allPlayers.get(k).getName());
			meta.setOwningPlayer(allPlayers.get(k));
			PlayerData pd = getManager().getPlayerManager().getPlayerData(player);
			
			if(pd.isFriend(allPlayers.get(k)))
				meta.setLore(Arrays.asList("§aAlready Friends"));
			else if(pd.isFriendRequest(allPlayers.get(k)))
				meta.setLore(Arrays.asList("§6Left-Click: §aAccept Friend Request", "§6Right-Click: §cDecline Friend Request"));
			else if(getManager().getPlayerManager().getPlayerData(allPlayers.get(k)).isFriendRequest(player))
				meta.setLore(Arrays.asList("§cCancel Request"));
			else
				meta.setLore(Arrays.asList("§aAdd Friend"));
			
			head.setItemMeta(meta);
			
			if(page == 1)
				toChange.setItem(i, head);
			else {
				player.getOpenInventory().getTopInventory().setItem(i, head);
			}
		}
		
		if(isUsingFillOption() && !isBorder()) {
			if(page != 1) {
				for(int i = 0; i < player.getOpenInventory().getTopInventory().getSize(); i++) {
					if(player.getOpenInventory().getTopInventory().getItem(i) == null)
						player.getOpenInventory().getTopInventory().setItem(i, getFillItem());
				}
			}
		}
		
		if(page == 0) {
			player.closeInventory();
			playerPage.put(player, 0);
			
			if(isUsingFillOption() && !isBorder())
				toChange = getFilledInventory(toChange);
			
		} else
			playerPage.put(player, page);
		
		player.openInventory(toChange);
	}
	
	/**
	 * Gets the entire list of Players that are in the friends menu
	 * @return Map
	 */
	public Map<Player, Integer> getPlayers() { return playerPage; }
	
	/**
	 * Gets the page that the player is currently on.
	 * @param player - Player to get page of
	 * @return Integer
	 */
	public int getPlayer(Player player) { return playerPage.get(player); }
	
	/**
	 * Sets the given page the player is to view next.
	 * @param player - Player to set
	 * @param number - page number
	 */
	public void setPage(Player player, int number) { playerPage.put(player, number); }

}
