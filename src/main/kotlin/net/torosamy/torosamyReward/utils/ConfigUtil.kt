package net.torosamy.torosamyReward.utils

import net.torosamy.torosamyCore.manager.ConfigManager
import net.torosamy.torosamyReward.TorosamyReward
import net.torosamy.torosamyReward.config.LangConfig
import net.torosamy.torosamyReward.config.MainConfig


class ConfigUtil {
    companion object {
        var mainConfig: MainConfig = MainConfig()
        var langConfig: LangConfig = LangConfig()

        private var mainConfigManager: ConfigManager = ConfigManager(mainConfig,TorosamyReward.plugin,"", "config.yml")
        private var langConfigManager: ConfigManager = ConfigManager(langConfig,TorosamyReward.plugin,"", "lang.yml")

        fun reloadConfig() {
            mainConfigManager.load()
            langConfigManager.load()
        }

        fun saveConfig() {
            mainConfigManager.save()
            langConfigManager.save()
        }
    }
}