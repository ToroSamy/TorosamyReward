package net.torosamy.torosamyReward.scheduler

import net.torosamy.torosamyReward.pojo.TimeCommand
import net.torosamy.torosamyScript.utils.CommandUtil
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import kotlin.properties.Delegates

class TimeCommandTask(val timeCommand: TimeCommand, val player: Player) : BukkitRunnable() {
    var remainTime by Delegates.notNull<Int>()

    init {
        remainTime = timeCommand.time
    }

    override fun run() {
        if (remainTime > 0) remainTime--
        else {
            remainTime = timeCommand.time

            if (timeCommand.permission != "" && !player.hasPermission(timeCommand.permission)) return
            timeCommand.commands.forEach { CommandUtil.getCommand(player, it) }
        }
    }
}