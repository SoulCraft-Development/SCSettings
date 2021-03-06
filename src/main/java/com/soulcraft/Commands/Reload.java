package com.soulcraft.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.soulcraft.Data.Perms;
import com.soulcraft.Data.SCSettingsManager;

/**
 * Handles the action of reload the entire plugin.
 *
 * @author Sommod
 * @version 1.0
 *
 */
public class Reload extends CommandManger {

	public Reload(CommandSender sender, Command command, String[] args, SCSettingsManager manager) {
		super(sender, command, args, manager);
	}
	
	@Override
	public void execute() {
		if(isPlayer() && (!getPlayer().hasPermission(Perms.RELOAD.toString()) || !getPlayer().isOp())) {
			noPermisison();
			return;
		}
		
		getManager().reload();
		getCommandSender().sendMessage("§aPlugin Reload.");
	}

}
