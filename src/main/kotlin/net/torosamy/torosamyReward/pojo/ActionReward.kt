package net.torosamy.torosamyReward.pojo

import org.bukkit.configuration.ConfigurationSection

class ActionReward {
    val commands: ArrayList<String> = arrayListOf()
    
    val denyCommands: HashMap<String, List<String>> = hashMapOf()
    
    val enableWorld: ArrayList<String> = arrayListOf()
    
    public constructor(config: ConfigurationSection?) {
        if (config == null) {
            return 
        }
        
        this.enableWorld.addAll(config.getStringList("enable-world"))
        
        this.commands.addAll(config.getStringList("commands"))
        
        val section = config.getConfigurationSection("denyCommands") ?: return 
        
        section.getKeys(false).forEach {
            denyCommands[it] = section.getStringList(it)
        }
    }
}