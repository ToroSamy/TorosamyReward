package net.torosamy.torosamyReward.utils

import net.torosamy.torosamyCore.config.Config
import net.torosamy.torosamyCore.config.ConfigFile
import net.torosamy.torosamyReward.TorosamyReward
import net.torosamy.torosamyReward.config.LangConfig
import net.torosamy.torosamyReward.config.MainConfig


object ConfigUtil {
    private val configs: ArrayList<Config> = ArrayList()

    public var mainConfig: MainConfig = MainConfig()
    public var langConfig: LangConfig = LangConfig()

    fun initConfig() {
        configs.clear()
        configs.add(Config(mainConfig, ConfigFile(TorosamyReward.plugin,"config.yml")))
        configs.add(Config(langConfig, ConfigFile(TorosamyReward.plugin,"lang.yml")))
    }

    fun reloadConfig() {
        for (config in configs) {
            config.load()
        }
    }

    fun saveConfig() {
        for (config in configs) {
            config.save()
        }
    }
}