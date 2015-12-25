package tk.justramon.bukkitpl.compassnavigator.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages
{
	// Making a variable for easier use of plugin instance

	/**
	 * Sends a message to the console with header
	 * @param msg The message to send
	 */
	private static String getHeader()
	{
		return ChatColor.GREEN + "" + ChatColor.BOLD +  "[" + ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Navigator" + ChatColor.GREEN + "" + ChatColor.BOLD + "]";
	}
	
	public static void sendConsoleMessage(String msg)
	{
		Bukkit.getConsoleSender().sendMessage(getHeader() + " " + msg);
	}

	/**
	 * Sends a message to a player with header
	 * @param p The Player to send the message to
	 * @param msg The message to send
	 */
	public static void sendPlayerMessage(Player p, String msg)
	{
		p.sendMessage(getHeader() + " " + msg);
	}
	/**
	 * Message that gets sent to tell the player the arguments are wrong.
	 * @param p The Player to send the message to
	 */
	public static void wrongArgs(Player p)
	{
		p.sendMessage(ChatColor.RED + "Sorry, but the argruments are wrong. Use Navigator like this:");
		p.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "/nav" + ChatColor.GREEN + " - Set your compass' pointer to your current location.");
		p.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "/nav x z" + ChatColor.GREEN + " - Set your compass' pointer to specified location.");
		p.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "/nav reset" + ChatColor.GREEN + " - Reset your compass' pointer.");
	}

	/**
	 * Message that gets sent to a player when it doesn't have the permission for the thing they're trying to do
	 * @param p The player to send the message to
	 */
	public static void noPermission(Player p)
	{
		sendPlayerMessage(p, ChatColor.RED + "Sorry, but you don't have the permission to do this.");
	}
}
