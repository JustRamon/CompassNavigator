package it.raqb.bukkitpl.compassnavigator.cmd

import it.raqb.bukkitpl.compassnavigator.util.CommandExecutable
import it.raqb.bukkitpl.compassnavigator.util.Messages
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.util.Vector
import java.io.File

object LoadCommand : CommandExecutable {
    override fun exe(p: Player, args: Array<String>, pl: Plugin) {
        if (args.size != 2) {
            Messages.wrongArgs(p)
            return
        }

        val name = args[1]

        val playerFile = File(pl.dataFolder, "savedLocations/" + p.uniqueId + ".yml")

        if (!playerFile.exists()) {
            Messages.sendPlayerMessage(p, "${ChatColor.RED}You don't have any locations saved!.")
            return
        }

        val yamlConfig = YamlConfiguration.loadConfiguration(playerFile)

        val savedLocations: MutableList<String> = yamlConfig.getStringList("saved")

        val foundLocation = savedLocations.any { it == name }

        if (!foundLocation) {
            Messages.sendPlayerMessage(p, "${ChatColor.RED}Could not find a saved location named \"$name\", please save one first.")
            return
        }

        val pos = yamlConfig.getVector("$name.pos")

        if (pos !is Vector) {
            Messages.sendPlayerMessage(p, "${ChatColor.RED}Location corrupted. Please delete this location.")
            return
        }

        val world = Bukkit.getWorld(yamlConfig.getString("$name.world"))

        if (world !is World) {
            Messages.sendPlayerMessage(p, "${ChatColor.RED}Could not find world or location is corrupted. Please make sure this world is loaded.")
            return
        }

        val location = Location(world, pos.x, pos.y, pos.z)

        p.compassTarget = location

        Messages.sendPlayerMessage(p, "${ChatColor.GREEN}Your compass direction has been succesfully set to ${ChatColor.YELLOW}\"$name\"${ChatColor.GREEN}: ${ChatColor.GOLD}X: ${pos.x}, Z: ${pos.z}.")
    }
}