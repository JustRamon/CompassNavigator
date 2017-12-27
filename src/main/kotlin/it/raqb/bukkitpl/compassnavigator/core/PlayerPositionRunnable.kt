package it.raqb.bukkitpl.compassnavigator.core

import it.raqb.bukkitpl.compassnavigator.util.Messages
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class PlayerPositionRunnable(private val playerUUID: String, private val targetUUID: String) : BukkitRunnable() {

    var timerId = -1

    /**
     * Runs every update
     */
    override fun run() {
        val player = Bukkit.getPlayer(UUID.fromString(playerUUID))
        val target = Bukkit.getPlayer(UUID.fromString(targetUUID))

        if (player == null) {
            this.cancel()
            return
        }

        if (target == null) {
            Messages.sendPlayerMessage(player, "${ChatColor.RED}The target player left the server, and will no longer be tracked")
            this.cancel()
            return
        }

        setPlayerTarget(player, target)
    }

    /**
     * Sets the compass target of player to the target player's location
     */
    private fun setPlayerTarget(player: Player, target: Player) {
        val targetLocation: Location? = target.location

        if (targetLocation == null) {
            this.cancel()
            return
        }

        player.compassTarget = targetLocation
    }

    /**
     * Custom cancle to also cancle the wrapping timer task
     */
    override fun cancel() {
        super.cancel()
        Bukkit.getScheduler().cancelTask(timerId)
    }
}