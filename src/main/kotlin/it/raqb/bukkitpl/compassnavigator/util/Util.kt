package it.raqb.bukkitpl.compassnavigator.util

import org.bukkit.entity.Player

object Util {
    fun hasPermission(player: Player, permission: String): Boolean {
        if(player.hasPermission(permission)){
            return true
        } else {
            Messages.noPermission(player)
            return false
        }
    }
}