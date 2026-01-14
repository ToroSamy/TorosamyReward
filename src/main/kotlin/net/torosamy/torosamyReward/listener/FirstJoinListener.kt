package net.torosamy.torosamyReward.listener



import net.torosamy.torosamyCore.api.TorosamyCoreAPI
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
        if(!ConfigUtil.mainConfig.firstJoin.enabled) {
            return
        }

        if (!event.player.hasPlayedBefore()) {
            return
        }
        
        TorosamyCoreAPI.runCommands(event.player, ConfigUtil.mainConfig.firstJoin.actions)

        if(!ConfigUtil.mainConfig.firstJoin.welcomeReward.enabled) return
        
        WelcomeRewardTask.start(event.player)
    }

    @EventHandler
    fun playerOnChat(event: AsyncPlayerChatEvent) {
        if(!ConfigUtil.mainConfig.firstJoin.enabled) {
            return
        }
        
        if(!ConfigUtil.mainConfig.firstJoin.welcomeReward.enabled) {
            return
        }

        if (!WelcomeRewardTask.isValid()) {
            return
        }
        
        if (!WelcomeRewardTask.canReward(event.player.name)) {
            return
        }

        for (key in ConfigUtil.mainConfig.firstJoin.welcomeReward.keys) {
            if(!event.message.contains(key)) {
                continue
            }
            
            WelcomeRewardTask.reward(event.player)
            
            break
        }
        
    }
}