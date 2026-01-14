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

class PlayerCommands {
    @Command(value = "tr open <box>")
    @Permission("torosamyreward.open")
    @CommandDescription("打开指定自选箱")
    fun open(player: Player, @Argument(value = "box", suggestions = "box") boxName: String)  {
        val box = RewardAPI.getRewardBox(boxName)

        if (box == null) {
            player.sendMessage(MessageUtil.format(ConfigUtil.langConfig.boxNotFound))
            return
        }

        RewardAPI.openSelectInventory(player, box)
    }

    @Suggestions("box")
    fun suggestBoxes(context: CommandContext<CommandSender>, input: CommandInput): List<String> {
        return RewardAPI.getBoxNames();
    }
}