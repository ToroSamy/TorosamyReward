package net.torosamy.torosamyReward

import net.torosamy.torosamyCore.utils.MessageUtil
import net.torosamy.torosamyReward.manager.TimeCommandManager
import net.torosamy.torosamyReward.utils.ConfigUtil
import net.torosamy.torosamyReward.utils.ListenerUtil
import net.torosamy.torosamyScript.utils.CommandUtil
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class TorosamyReward : JavaPlugin() {
    companion object{lateinit var plugin: TorosamyReward }
    override fun onEnable() {
        plugin = this

        ConfigUtil.reloadConfig()
        CommandUtil.registerCommand()
        ListenerUtil.registerListener()
        TimeCommandManager.loadTimeCommands()


        Bukkit.getConsoleSender().sendMessage(MessageUtil.text("&a[服务器娘]&a插件 &eTorosamyReward &a成功开启喵~"))
        Bukkit.getConsoleSender().sendMessage(MessageUtil.text("&a[服务器娘]&a作者 &eTorosamy|yweiyang"))
    }

    override fun onDisable() {
        ConfigUtil.saveConfig()

        Bukkit.getConsoleSender().sendMessage(MessageUtil.text("&a[服务器娘]&c插件 &eTorosamyReward &c成功关闭喵~"))
        Bukkit.getConsoleSender().sendMessage(MessageUtil.text("&a[服务器娘]&c作者 &eTorosamy|yweiyang"))
    }
}
