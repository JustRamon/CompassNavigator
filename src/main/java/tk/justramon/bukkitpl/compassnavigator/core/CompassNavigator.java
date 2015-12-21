package tk.justramon.bukkitpl.compassnavigator.core;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import tk.justramon.bukkitpl.compassnavigator.util.Messages;

public class CompassNavigator extends JavaPlugin
{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		// Check to make sure commands are sent from players.
		Player p = null;

		if (sender instanceof Player)
			p = (Player) sender;
		else
		{
			// Sends messages to console to let know that only players can execute SH cmds
			Messages.sendConsoleMessage(ChatColor.RED + " A " + this.getDescription().getName() + " command was sent from an unsupported sender.");
			Messages.sendConsoleMessage(ChatColor.RED + " Please only execute CompassNavigator's commands in-game.");
			return true;
		}

		// Command check
		if (cmd.getName().equalsIgnoreCase("compassnavigator"))
		{
			if(p.hasPermission("compassnavigator.use"))
			{
			NavigatorCommand.exe(p, args);
			return true;
			}
			else
				Messages.noPermission(p);
		}
		return false;
	}
}
