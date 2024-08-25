package net.torosamy.torosamyReward.utils


import net.torosamy.torosamyReward.TorosamyReward
import net.torosamy.torosamyReward.listener.FirstJoinListener
import net.torosamy.torosamyReward.listener.TimeCommandListener
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener


class ListenerUtil {
    companion object {

        private val firstJoinListener:Listener = FirstJoinListener()
        private val timeCommandListener:Listener = TimeCommandListener()
        fun registerListener() {
            registerFirstJoinListener()
            registerTimeCommandListener()
        }

        fun registerFirstJoinListener() {
            HandlerList.unregisterAll(firstJoinListener)
            if(!ConfigUtil.getMainConfig().firstJoin.enabled) return
            else TorosamyReward.plugin.server.pluginManager.registerEvents(firstJoinListener, TorosamyReward.plugin)
        }
        fun registerTimeCommandListener() {
            HandlerList.unregisterAll(timeCommandListener)
            if(!ConfigUtil.getMainConfig().timeCommand.enabled) return
            else TorosamyReward.plugin.server.pluginManager.registerEvents(timeCommandListener, TorosamyReward.plugin)
        }
    }
}