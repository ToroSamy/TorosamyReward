package net.torosamy.torosamyReward.listener


import net.torosamy.torosamyReward.api.RewardAPI
import net.torosamy.torosamyReward.utils.ConfigUtil
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class TimeCommandListener : Listener {
    @EventHandler
    fun playerOnJoin(event: PlayerJoinEvent) {
        if(!ConfigUtil.mainConfig.timeCommand.enabled) {
            RewardAPI.cancelTimeCommands()
            return
        }
        
        RewardAPI.startTimeCommands(event.player)
    }

    @EventHandler
    fun playerOnQuit(event: PlayerQuitEvent) {
        if(!ConfigUtil.mainConfig.timeCommand.enabled) {
            RewardAPI.cancelTimeCommands()
            return
        }
        
        RewardAPI.cancelTimeCommands(event.player)
    }
}