package net.torosamy.torosamyReward.scheduler


import net.torosamy.torosamyCore.api.TorosamyCoreAPI
import net.torosamy.torosamyReward.TorosamyReward
import net.torosamy.torosamyReward.utils.ConfigUtil
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

class WelcomeRewardTask(private val player: Player) : BukkitRunnable() {
    companion object{
        private val refuseRewardList = ArrayList<String>()
        
        private var valid = false
        
        private var task: BukkitTask? = null

        public fun isValid(): Boolean {
            return valid
        }
        
        public fun canReward(playerName: String): Boolean {
            return refuseRewardList.contains(playerName)
        }
        
        public fun reward(player: Player) {
            TorosamyCoreAPI.runCommands(player, ConfigUtil.mainConfig.firstJoin.welcomeReward.actions)
            refuseRewardList.add(player.name)
        }
        
        public fun start(player: Player) {
            this.refuseRewardList.add(player.name)
            this.task = WelcomeRewardTask(player)
                .runTaskTimerAsynchronously(TorosamyReward.plugin,0,20L)
        }
    }

    private var remainTime: Int = ConfigUtil.mainConfig.firstJoin.welcomeReward.time
    
    public fun init() {
        valid = false
        this.cancel()
        refuseRewardList.clear()
    }
    
    override fun run() {
        if (!player.isOnline) {
            init()
            return
        }
        
        if(remainTime > 0) {
            remainTime--
            valid = true
            return
        }

        init()
    }
}