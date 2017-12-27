package it.raqb.bukkitpl.compassnavigator.core

import org.bukkit.Bukkit

object TrackingTaskManager {

    private val trackingRunnables: HashMap<String, Int> = HashMap()

    /**
     * Checks if player has task running (for tracking player), and cancles it
     */
    fun cancleTaskIfRunning(playerUUID: String) {
        if (trackingRunnables.containsKey(playerUUID)) {
            val taskId = trackingRunnables[playerUUID] ?: return

            Bukkit.getScheduler().cancelTask(taskId)
            trackingRunnables.remove(playerUUID)
        }
    }

    /**
     * Adds a task to the runnable hashmap
     */
    fun addTask(playerUUID: String, taskId: Int) {
        trackingRunnables.put(playerUUID, taskId)
    }

}