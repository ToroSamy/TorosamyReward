package net.torosamy.torosamyReward.utils


import net.torosamy.torosamyReward.TorosamyReward
import net.torosamy.torosamyReward.listener.ActionRewardListener
import net.torosamy.torosamyReward.listener.FirstJoinListener
import net.torosamy.torosamyReward.listener.RewardBoxListener
import net.torosamy.torosamyReward.listener.TimeCommandListener
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener


object ListenerUtil {
    public val firstJoinListener = FirstJoinListener()
    public val timeCommandListener = TimeCommandListener()
    public val rewardBoxListener = RewardBoxListener()
    public val actionRewardListener = ActionRewardListener()

    fun registerListener() {
        TorosamyReward.plugin.server.pluginManager.registerEvents(actionRewardListener, TorosamyReward.plugin)
        TorosamyReward.plugin.server.pluginManager.registerEvents(firstJoinListener, TorosamyReward.plugin)
        TorosamyReward.plugin.server.pluginManager.registerEvents(timeCommandListener, TorosamyReward.plugin)
        TorosamyReward.plugin.server.pluginManager.registerEvents(rewardBoxListener, TorosamyReward.plugin)
    }


}