package net.torosamy.torosamyReward.commands

import net.torosamy.torosamyCore.utils.MessageUtil
import net.torosamy.torosamyReward.api.RewardAPI
import net.torosamy.torosamyReward.utils.ConfigUtil
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.CommandDescription
import org.incendo.cloud.annotations.Permission
import org.incendo.cloud.annotations.suggestion.Suggestions
import org.incendo.cloud.context.CommandContext
import org.incendo.cloud.context.CommandInput


class AdminCommands {
    @Command(value = "tr reload")
    @Permission("torosamyreward.reload")
    @CommandDescription("重载TorosamyReward配置文件")
    fun reloadConfig(sender: CommandSender) {
        ConfigUtil.reloadConfig()

        RewardAPI.loadTimeCommands()
        RewardAPI.loadRewardBoxes()
        RewardAPI.loadActionReward()

        sender.sendMessage(MessageUtil.format(ConfigUtil.langConfig.reloadMessage))
    }


    @Command(value = "tr give <player> <box> <amount>")
    @Permission("torosamyreward.give")
    @CommandDescription("给予玩家指定自选箱次数")
    fun give(commandSender: CommandSender, @Argument("player") target: Player, @Argument(value = "box", suggestions = "box") boxName: String, @Argument("amount") amount: Int) {
        val box = RewardAPI.getRewardBox(boxName)

        if (box == null) {
            commandSender.sendMessage(MessageUtil.format(ConfigUtil.langConfig.boxNotFound))
            return
        }

        if (amount < 1) {
            commandSender.sendMessage(MessageUtil.format(ConfigUtil.langConfig.amountError))
            return
        }

        box.addCounts(target, amount)
    }

    @Suggestions("box")
    fun suggestBoxes(context: CommandContext<CommandSender>, input: CommandInput): List<String> {
        return RewardAPI.getBoxNames();
    }
}