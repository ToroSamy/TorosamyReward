package net.torosamy.torosamyReward.scheduler


import net.torosamy.torosamyReward.utils.ConfigUtil
import org.bukkit.scheduler.BukkitRunnable

class WelcomeRewardTask : BukkitRunnable() {
    companion object{
        var duration: Int = ConfigUtil.mainConfig.firstJoin.welcomeReward.time
        var hasBeenRewardList = ArrayList<String>()
    }
    override fun run() {
        if(duration > 0) duration--
        else {
            this.cancel()
            duration = -1
            hasBeenRewardList.clear()
        }
    }
    init { duration = ConfigUtil.mainConfig.firstJoin.welcomeReward.time }
}