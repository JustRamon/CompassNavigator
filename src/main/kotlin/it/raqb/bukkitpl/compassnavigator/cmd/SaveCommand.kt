package it.raqb.bukkitpl.compassnavigator.cmd

import it.raqb.bukkitpl.compassnavigator.util.CommandExecutable
import it.raqb.bukkitpl.compassnavigator.util.Messages
import net.md_5.bungee.api.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.io.File

object SaveCommand : CommandExecutable {
    override fun exe(p: Player, args: Array<String>, pl: Plugin) {
        val currentLocation = p.compassTarget

        if (args.size != 2) {
            Messages.wrongArgs(p)
            return
        }

        val newName = args[1]

        val playerFile = File(pl.dataFolder, "savedLocations/" + p.uniqueId.toString() + ".yml")

        if (!playerFile.exists())
            playerFile.createNewFile()

        val yamlConfig = YamlConfiguration.loadConfiguration(playerFile)

        val savedLocations: MutableList<String> = yamlConfig.getStringList("saved")

        val foundLocation = savedLocations.any { it == newName }

        if (foundLocation) {
            Messages.sendPlayerMessage(p, "${ChatColor.RED}You have already saved a home with that name. Try a different one.")
            return
        }

        savedLocations.add(newName)

        yamlConfig.set("saved", savedLocations)
        yamlConfig.set("$newName.world", currentLocation.world.name)
        yamlConfig.set("$newName.pos", currentLocation.toVector())
        yamlConfig.save(playerFile)
        Messages.sendPlayerMessage(p, "${ChatColor.GREEN}Your current compass location has been saved as ${ChatColor.YELLOW}\"$newName\".")
    }
}