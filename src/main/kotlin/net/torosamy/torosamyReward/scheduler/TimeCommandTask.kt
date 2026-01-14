package net.torosamy.torosamyReward.scheduler

import net.torosamy.torosamyCore.api.TorosamyCoreAPI
import net.torosamy.torosamyReward.pojo.TimeCommand
import net.torosamy.torosamyScript.utils.CommandUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import kotlin.properties.Delegates

class TimeCommandTask : BukkitRunnable {
    private var counts: Int = 0
    
    private val timeCommand: TimeCommand
    
    private val player: Player
    
    public constructor(player: Player, timeCommand: TimeCommand) {
        this.player = player
        this.timeCommand = timeCommand
    }
    
    override fun run() {
        if (counts != timeCommand.time) {
            counts ++
            return
        }
        
        counts = 0
        
        TorosamyCoreAPI.runCommands(player, timeCommand.commands, timeCommand.denyCommands)
    }
}