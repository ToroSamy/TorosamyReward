package net.torosamy.torosamyScript.utils

import me.clip.placeholderapi.PlaceholderAPI


import net.torosamy.torosamyCore.TorosamyCore
import net.torosamy.torosamyCore.utils.MessageUtil
import net.torosamy.torosamyReward.TorosamyReward
import net.torosamy.torosamyReward.commands.AdminCommands

import org.bukkit.Bukkit
import org.bukkit.entity.Player

class CommandUtil {
    companion object {
        private var torosamyCorePlugin: TorosamyCore = TorosamyReward.plugin.server.pluginManager.getPlugin("TorosamyCore") as TorosamyCore
        fun registerCommand() {
            torosamyCorePlugin.getCommandManager().annotationParser.parse(AdminCommands())
        }

        fun getCommand(player: Player, command: String) :Boolean{
            if(command.startsWith("[permission] ")) {
                val message = command.replace("[permission] ", "")

                if (!player.hasPermission(message)) {
                    Bukkit.getConsoleSender().sendMessage(MessageUtil.text("&b[服务器娘]&clack permission: &e${command}"))
                    return false
                }
            }


            if(command.startsWith("[console] ")) {
                val message = command.replace("[console] ", "")
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), MessageUtil.text(PlaceholderAPI.setPlaceholders(player,message)))
                return true
            }
            else if(command.startsWith("[player] ")) {
                val message = command.replace("[player] ", "")
                Bukkit.dispatchCommand(player, MessageUtil.text(PlaceholderAPI.setPlaceholders(player, message)))
                return true
            }
            else if(command.startsWith("[message] ")) {
                val message = command.replace("[message] ", "")
                player.sendMessage(MessageUtil.text(PlaceholderAPI.setPlaceholders(player,message)))
                return true
            }
            else if(command.startsWith("[allMessage] ")) {
                val message = command.replace("[allMessage] ", "")
                Bukkit.getOnlinePlayers().forEach{ player: Player -> player.sendMessage(MessageUtil.text(PlaceholderAPI.setPlaceholders(player,message)))}
                return true
            }
            else if(command.startsWith("[op] ")) {
                val message = command.replace("[op] ", "")
                if(player.isOp) Bukkit.dispatchCommand(player, MessageUtil.text(PlaceholderAPI.setPlaceholders(player, message)))
                else {
                    player.isOp = true
                    Bukkit.dispatchCommand(player, MessageUtil.text(PlaceholderAPI.setPlaceholders(player, message)))
                    player.isOp = false
                }

                return true
            }
            else{ Bukkit.getConsoleSender().sendMessage(MessageUtil.text("&b[服务器娘]&cUnknown action: &e${command}")) }
            return false
        }


    }
}