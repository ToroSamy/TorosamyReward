package net.torosamy.torosamyReward.manager

import net.torosamy.torosamyCore.utils.MessageUtil
import net.torosamy.torosamyReward.TorosamyReward
import net.torosamy.torosamyReward.pojo.TimeCommand
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class TimeCommandManager {
    companion object{
        val timeCommandDirectory = File(TorosamyReward.plugin.dataFolder, "time-command")
        val timeCommands = HashMap<String, TimeCommand>()


        fun loadTimeCommands() {
            timeCommands.clear()
            val timeCommandYmls = HashMap<String, ConfigurationSection>()
            loadTimeCommandConfigs(timeCommandDirectory, timeCommandYmls, "")
            loadTimeCommandData(timeCommandYmls)

            TorosamyReward.plugin.server.consoleSender.sendMessage(MessageUtil.text("&a[服务器娘]&a插件 &eTorosamyReward &a成功加载 &e${timeCommands.size} &a个定时指令组喵~"))
        }

        private fun loadTimeCommandConfigs(file: File, ymls: HashMap<String, ConfigurationSection>, path: String) {
            if (!file.exists()) file.mkdirs()

            for (listFile in file.listFiles()) {
                val filePath = if (path.isNotEmpty()) path + File.separator + listFile.name else listFile.name
                if (listFile.isDirectory) loadTimeCommandConfigs(listFile, ymls, filePath)
                else if (listFile.name.endsWith(".yml")) ymls[filePath] = YamlConfiguration.loadConfiguration(listFile)
            }
        }

        private fun loadTimeCommandData(ymls: HashMap<String, ConfigurationSection>) {
            for (yml in ymls.values) {
                for (timeCommandKey in yml.getKeys(false)) {
                    val sonYml = yml.getConfigurationSection(timeCommandKey)!!

                    val timeCommand = TimeCommand(sonYml.getStringList("commands"))

                    val permission = sonYml.getString("permission") ?: return
                    timeCommand.permission = permission
                    timeCommand.time = sonYml.getInt("time", 0)

                    timeCommands[timeCommandKey] = timeCommand
                }
            }
        }
    }
}