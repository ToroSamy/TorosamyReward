package net.torosamy.torosamyReward.commands

import net.torosamy.torosamyCore.utils.MessageUtil
import net.torosamy.torosamyReward.manager.TimeCommandManager
import net.torosamy.torosamyReward.utils.ConfigUtil
import net.torosamy.torosamyReward.utils.ListenerUtil
import org.bukkit.command.CommandSender
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.CommandDescription
import org.incendo.cloud.annotations.Permission

class AdminCommands {
    @Command(value = "tr reload")
    @Permission("torosamyReward.reload")
    @CommandDescription("重载TorosamyReward配置文件")
    fun reloadConfig(sender: CommandSender) {
        ConfigUtil.reloadConfig()
        ListenerUtil.registerListener()
        TimeCommandManager.loadTimeCommands()


        sender.sendMessage(MessageUtil.text(ConfigUtil.langConfig.reloadMessage))
    }
}