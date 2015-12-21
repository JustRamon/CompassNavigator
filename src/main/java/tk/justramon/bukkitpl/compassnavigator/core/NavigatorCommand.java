package tk.justramon.bukkitpl.compassnavigator.core;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import tk.justramon.bukkitpl.compassnavigator.util.Messages;

public class NavigatorCommand
{
	public static void exe(Player p, String[] args)
	{
		if(args.length > 0)
		{
			if(args[0].equals("reset"))
			{
				Location loc = p.getWorld().getSpawnLocation();
				p.setCompassTarget(loc);
				Messages.sendPlayerMessage(p, ChatColor.GOLD + "Your compass has been reset to the spawnpoint of your world.");
			}
			else
			{
				if(args.length == 2 && args[0] != null && args[1] != null)
				{
					boolean success;
					try
					{
						p.setCompassTarget(new Location(p.getWorld(), Double.parseDouble(args[0]), 0.0, Double.parseDouble(args[1])));
						success = true;
					}
					catch(NumberFormatException exception)
					{
						Messages.wrongArgs(p);
						success = false;
					}
					if(success)
						Messages.sendPlayerMessage(p, ChatColor.GREEN + "Your compass direction has been succesfully set to: " + ChatColor.GOLD + args[0] + " " + args[1] + ".");
					else
						return;
				}
				else
					Messages.wrongArgs(p);
			}
		}
		// Cmd without args will open the help menu.
		else
			Messages.wrongArgs(p);
	}
}
