package it.raqb.bukkitpl.compassnavigator.core

import it.raqb.bukkitpl.compassnavigator.cmd.ListCommand
import it.raqb.bukkitpl.compassnavigator.cmd.LoadCommand
import it.raqb.bukkitpl.compassnavigator.cmd.SaveCommand
import it.raqb.bukkitpl.compassnavigator.util.CommandExecutable
import it.raqb.bukkitpl.compassnavigator.util.Messages
import net.md_5.bungee.api.ChatColor
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin


object NavigatorCommand : CommandExecutable {
    override fun exe(p: Player, args: Array<String>, pl: Plugin) {
        if (args.isEmpty()) {
            p.compassTarget = p.location
            Messages.sendPlayerMessage(p, ChatColor.GREEN.toString() + "Your compass direction has been succesfully set to your location.")
            return
        }

        when (args[0]) {
            "reset" -> {
                // Reset subcommand
                val loc = p.world.spawnLocation
                p.compassTarget = loc
                Messages.sendPlayerMessage(p, ChatColor.GOLD.toString() + "Your compass has been reset to the spawnpoint of your world.")
                return
            }
            "save" -> {
                // Save subcommand
                if (p.hasPermission("compassnavigator.save"))
                    SaveCommand.exe(p, args, pl)

            }
            "load" -> {
                // Load subcommand
                if (p.hasPermission("compassnavigator.save"))
                    LoadCommand.exe(p, args, pl)

            }
            "list" -> {
                // List subcommand
                if (p.hasPermission("compassnavigator.save"))
                    ListCommand.exe(p, args, pl)
            }
            else -> {
                // when just setting the location
                if (args.size != 2) {
                    Messages.wrongArgs(p)
                    return
                }
                try {
                    p.compassTarget = Location(p.world, args[0].toDouble(), 0.0, args[1].toDouble())
                    Messages.sendPlayerMessage(p, "${ChatColor.GREEN}Your compass direction has been succesfully set to: ${ChatColor.GOLD}X: ${args[0]}, Z: ${args[1]}.")
                } catch (exception: NumberFormatException) {
                    Messages.wrongArgs(p)
                }
            }
        }

    }
}