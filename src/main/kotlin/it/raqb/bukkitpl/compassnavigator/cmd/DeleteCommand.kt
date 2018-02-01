package it.raqb.bukkitpl.compassnavigator.cmd

import it.raqb.bukkitpl.compassnavigator.util.CommandExecutable
import it.raqb.bukkitpl.compassnavigator.util.Messages
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.io.File

object DeleteCommand : CommandExecutable {
    override fun exe(p: Player, args: Array<String>, pl: Plugin) {
        if (args.size != 2) {
            Messages.wrongArgs(p)
            return
        }

        val name = args[1]

        val savedLocationsFolder = File(pl.dataFolder, "savedLocations/")

        if (!savedLocationsFolder.exists()) {
            Messages.sendPlayerMessage(p, "${ChatColor.RED}You do not have any locations stored")
            return
        }

        val playerFile = File(savedLocationsFolder, p.uniqueId.toString() + ".yml")

        if (!playerFile.exists()) {
            Messages.sendPlayerMessage(p, "${ChatColor.RED}You do not have any locations stored")
            return
        }

        val yamlConfig = YamlConfiguration.loadConfiguration(playerFile)

        val savedLocations: MutableList<String> = yamlConfig.getStringList("saved")

        savedLocations.forEachIndexed { index: Int, it: String ->
            if (it == name) {
                savedLocations.removeAt(index)
                yamlConfig.set("saved", savedLocations)
                yamlConfig.set(name, null)
                yamlConfig.save(playerFile)
                Messages.sendPlayerMessage(p, "${ChatColor.GREEN}The location ${ChatColor.YELLOW}$name ${ChatColor.GREEN}has been deleted.")
                return
            }
        }

        Messages.sendPlayerMessage(p, "${ChatColor.RED}Could not find a saved location with that name.")
    }
}