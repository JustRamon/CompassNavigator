package it.raqb.bukkitpl.compassnavigator.cmd

import it.raqb.bukkitpl.compassnavigator.util.CommandExecutable
import it.raqb.bukkitpl.compassnavigator.util.Messages
import net.md_5.bungee.api.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.io.File

object ListCommand : CommandExecutable {
    override fun exe(p: Player, args: Array<String>, pl: Plugin) {
        val playerFile = File(pl.dataFolder, "savedLocations/" + p.uniqueId + ".yml")

        if (!playerFile.exists()) {
            Messages.sendPlayerMessage(p, "${ChatColor.RED}You don't have any locations saved!")
            return
        }

        val yamlConfig = YamlConfiguration.loadConfiguration(playerFile)

        val savedLocations: MutableList<String> = yamlConfig.getStringList("saved")

        if(savedLocations.isEmpty()){
            Messages.sendPlayerMessage(p, "${ChatColor.RED}You don't have any locations saved!")
            return
        }

        Messages.sendPlayerMessage(p, "${ChatColor.GREEN}Locations found:")
        Messages.sendListMessage(p, savedLocations.toTypedArray())
    }
}