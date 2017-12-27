package it.raqb.bukkitpl.compassnavigator.core

import it.raqb.bukkitpl.compassnavigator.cmd.ListCommand
import it.raqb.bukkitpl.compassnavigator.cmd.LoadCommand
import it.raqb.bukkitpl.compassnavigator.cmd.SaveCommand
import it.raqb.bukkitpl.compassnavigator.util.CommandExecutable
import it.raqb.bukkitpl.compassnavigator.util.Messages
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin


object NavigatorCommand : CommandExecutable {

    override fun exe(p: Player, args: Array<String>, pl: Plugin) {
        if (args.isEmpty()) {
            p.compassTarget = p.location
            Messages.sendPlayerMessage(p, "${ChatColor.GREEN}Your compass direction has been successfully set to your current location.")
            return
        }

        when (args[0]) {
            "reset" -> {
                TrackingTaskManager.cancleTaskIfRunning(p.uniqueId.toString())
                // Reset subcommand
                val loc = p.world.spawnLocation
                p.compassTarget = loc
                Messages.sendPlayerMessage(p, "${ChatColor.GOLD}Your compass has been reset to the spawnpoint of your world.")
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
                // Player location
                if (args.size == 1 && args[0].toDoubleOrNull() == null) {
                    val targetPlayer: Player? = Bukkit.getPlayer(args[0])

                    if (!p.hasPermission("compassnavigator.player")) {
                        Messages.noPermission(p)
                        return
                    }

                    if (targetPlayer == null) {
                        Messages.sendPlayerMessage(p, "${ChatColor.RED}The player ${args[0]} is currently not online!")
                        return
                    }

                    if (p.uniqueId == targetPlayer.uniqueId) {
                        Messages.sendPlayerMessage(p, "${ChatColor.RED}You cannot track yourself!")
                        return
                    }

                    TrackingTaskManager.cancleTaskIfRunning(p.uniqueId.toString())

                    val runnable = PlayerPositionRunnable(p.uniqueId.toString(), targetPlayer.uniqueId.toString())

                    val task = runnable.runTaskTimer(pl, 0, 20)

                    // So it is able to cancle the wrapping timer task
                    runnable.timerId = task.taskId

                    TrackingTaskManager.addTask(p.uniqueId.toString(), task.taskId)

                    Messages.sendPlayerMessage(p, "${ChatColor.GREEN}Your compass is now tracking ${targetPlayer.name}.")

                    return
                }

                // when just setting the location
                if (args.size != 2) {
                    Messages.wrongArgs(p)
                    return
                }

                try {
                    TrackingTaskManager.cancleTaskIfRunning(p.uniqueId.toString())
                    p.compassTarget = Location(p.world, args[0].toDouble(), 0.0, args[1].toDouble())
                    Messages.sendPlayerMessage(p, "${ChatColor.GREEN}Your compass direction has been successfully set to: ${ChatColor.GOLD}X: ${args[0]}, Z: ${args[1]}.")
                } catch (exception: NumberFormatException) {
                    Messages.wrongArgs(p)
                }
            }
        }

    }


}