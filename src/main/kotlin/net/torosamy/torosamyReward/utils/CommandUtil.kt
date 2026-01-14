package net.torosamy.torosamyScript.utils


import net.torosamy.torosamyCore.commands.CommandManager
import net.torosamy.torosamyReward.TorosamyReward
import net.torosamy.torosamyReward.commands.AdminCommands
import net.torosamy.torosamyReward.commands.PlayerCommands

object CommandUtil {
    private val commanderManager: CommandManager = CommandManager(TorosamyReward.plugin)

    public val ADMIN_COMMANDS: AdminCommands = AdminCommands()
    public val PLAYER_COMMANDS: PlayerCommands = PlayerCommands()

    fun registerCommand() {
        commanderManager.annotationParser.parse(ADMIN_COMMANDS)
        commanderManager.annotationParser.parse(PLAYER_COMMANDS)
    }
}