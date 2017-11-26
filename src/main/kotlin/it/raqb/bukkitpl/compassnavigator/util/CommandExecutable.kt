package it.raqb.bukkitpl.compassnavigator.util

import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

interface CommandExecutable {

    fun exe(p: Player, args: Array<String>, pl: Plugin)
}