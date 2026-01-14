package net.torosamy.torosamyReward.pojo

import net.torosamy.torosamyReward.TorosamyReward
import net.torosamy.torosamyReward.scheduler.TimeCommandTask
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player

class TimeCommand {
    val name: String
    val denyCommands: HashMap<String, List<String>>
    val time: Int
    val commands: List<String>
    val tasks: HashMap<String, TimeCommandTask> = HashMap()
    
    private constructor(name: String, time: Int, denyCommands: HashMap<String, List<String>>, commands: List<String>) {
        this.name = name
        this.time = time;
        this.denyCommands = denyCommands
        this.commands = commands
    }
    
    public fun start(player: Player) {
        if (!player.isOnline) {
            return
        }
        
        cancel(player.name)
        
        val task = TimeCommandTask(player, this)
        task.runTaskTimer(TorosamyReward.plugin,0L,20L)
        tasks[player.name] = task
    }
    
    public fun cancel() {
        tasks.values.forEach{
            it.cancel()
        }
        tasks.clear()
    }
    
    public fun cancel(playerName: String) {
        val task = tasks[playerName] ?: return
        task.cancel()
        tasks.remove(playerName)
    }
    
    public fun generateConfig(): ConfigurationSection {
        val config = YamlConfiguration()
        
        this.denyCommands.forEach {
            config.set("denyCommands." + it.key, it.value)
        }
        
        config.set("time", time)
        config.set("name", name)
        config.set("commands", commands)
        return config
    }
    
    companion object {
        public fun generateInstance(name: String, config: ConfigurationSection?): TimeCommand? {
            if (config == null) {
                return null
            }
            
            val time = config.getInt("time", 0)
            
            if (time < 1) {
                return null
            }

            val commands = config.getStringList("commands")

            val denyCommands = HashMap<String, List<String>>()

            val section = config.getConfigurationSection("denyCommands")

            section?.getKeys(false)?.forEach {
                denyCommands[it] = section.getStringList(it)
            }
            
            return TimeCommand(name, time, denyCommands, commands)
        } 
    }
}