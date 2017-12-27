package it.raqb.bukkitpl.compassnavigator.core

import it.raqb.bukkitpl.compassnavigator.util.Messages
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class CompassNavigator : JavaPlugin() {

    /**
     * Sets up the savedLocations folder
     */
    override fun reloadConfig() {
        super.reloadConfig()

        val savedLocFolder = File(dataFolder, "savedLocations")

        if (!savedLocFolder.exists())
            savedLocFolder.mkdirs()
    }

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<String>): Boolean {
        // Check to make sure commands are sent from players.

        if (sender !is Player) {
            // Sends messages to console to let know that only players can execute cmds
            Messages.sendConsoleMessage(ChatColor.RED.toString() + "A ${this.description.name} command was sent from an unsupported sender.")
            Messages.sendConsoleMessage(ChatColor.RED.toString() + "Please only execute CompassNavigator's commands in-game.")
            return true
        }

        // Command check
        if (cmd.name.equals("compassnavigator", ignoreCase = true)) {
            if (sender.hasPermission("compassnavigator.use")) {
                NavigatorCommand.exe(sender, args, this)
                return true
            } else
                Messages.noPermission(sender)
        }
        return false
    }
}