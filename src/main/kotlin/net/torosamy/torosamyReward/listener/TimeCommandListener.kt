package net.torosamy.torosamyReward.listener


import net.torosamy.torosamyReward.TorosamyReward
import net.torosamy.torosamyReward.manager.TimeCommandManager
import net.torosamy.torosamyReward.scheduler.TimeCommandTask
import net.torosamy.torosamyReward.utils.ConfigUtil
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.scheduler.BukkitTask

class TimeCommandListener : Listener {
    companion object {
        val playerList = HashMap<Player, HashSet<BukkitTask>>()
    }


    @EventHandler
    fun playerOnJoin(event: PlayerJoinEvent) {
        if(!ConfigUtil.mainConfig.timeCommand.enabled) return


        val hashSet = HashSet<BukkitTask>()
        for (timeCommand in TimeCommandManager.timeCommands.values) {
            if (timeCommand.permission != "" && !event.player.hasPermission(timeCommand.permission)) continue
            hashSet.add(TimeCommandTask(timeCommand, event.player).runTaskTimer(TorosamyReward.plugin,0L,20L))
        }
        playerList[event.player] = hashSet
    }

    @EventHandler
    fun playerOnQuit(event: PlayerQuitEvent) {
        if(!ConfigUtil.mainConfig.timeCommand.enabled) return

        val set = playerList[event.player]!!
        for (task in set) task.cancel()
        set.clear()
        playerList.remove(event.player)
    }
}