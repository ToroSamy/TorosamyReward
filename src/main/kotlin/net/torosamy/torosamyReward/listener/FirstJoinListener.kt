package net.torosamy.torosamyReward.listener



import net.torosamy.torosamyReward.TorosamyReward
import net.torosamy.torosamyReward.scheduler.WelcomeRewardTask
import net.torosamy.torosamyReward.utils.ConfigUtil
import net.torosamy.torosamyScript.utils.CommandUtil
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent

class FirstJoinListener : Listener {
    @EventHandler
    fun playerOnJoin(event: PlayerJoinEvent) {
        if(!ConfigUtil.getMainConfig().firstJoin.enabled) return
        //如果玩家游玩时长超过了500毫秒 则不认为是第一次进入服务器
        if(System.currentTimeMillis() - event.player.firstPlayed > 500) return

        for (action in ConfigUtil.getMainConfig().firstJoin.actions) {
            if(!CommandUtil.getCommand(event.player,action)) return
        }

        if(!ConfigUtil.getMainConfig().firstJoin.welcomeReward.enabled) return
        WelcomeRewardTask().runTaskTimerAsynchronously(TorosamyReward.plugin,0,20L)
    }

    @EventHandler
    fun playerOnChat(event: AsyncPlayerChatEvent) {
        if(!ConfigUtil.getMainConfig().firstJoin.enabled) return
        if(!ConfigUtil.getMainConfig().firstJoin.welcomeReward.enabled) return
        //已经超时
        if(WelcomeRewardTask.duration <= 0) return
        ConfigUtil.getMainConfig().firstJoin.welcomeReward.keys.forEach {key: String ->
            //如果发送的信息不包含key
            if(!event.message.contains(key)) return
            //已经领取过一次奖励
            if(WelcomeRewardTask.hasBeenRewardList.contains(event.player.name)) return

            for (action in ConfigUtil.getMainConfig().firstJoin.welcomeReward.actions) {
                if(!CommandUtil.getCommand(event.player,action)) return
            }
        }
    }
}