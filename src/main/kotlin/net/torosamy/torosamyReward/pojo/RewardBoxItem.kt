package net.torosamy.torosamyReward.pojo

import net.torosamy.torosamyCore.api.TorosamyCoreAPI
import net.torosamy.torosamyReward.utils.ConfigUtil
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class RewardBoxItem {
    private val commands: ArrayList<String> = ArrayList()

    private val name: String

    private val item: ItemStack
    
    private val slot: Int

    fun runCommands(player: Player) {
        TorosamyCoreAPI.runCommands(player, commands)
    }
    
    fun getSlot(): Int {
        return slot
    }
    
    fun getDisplay(): String {
        return item.itemMeta.displayName
    }
    
    fun getName(): String {
        return name
    }
    fun getItem(): ItemStack {
        return item
    }

    fun getConfig(): ConfigurationSection {
        val config = TorosamyCoreAPI.getConfig(item)

        config["slot"] = slot
        config["commands"] = commands

        return config
    }

    private constructor(name: String, slot: Int, item: ItemStack, commands: List<String>) {
        this.name = name
        this.slot = slot
        this.item = item
        this.commands.addAll(commands)
    }
    
    companion object {
        fun generateInstance(name: String, config: ConfigurationSection?): RewardBoxItem? {
            if (config == null) {
                return null
            }

   
            if (name.isEmpty()) {
                return null
            }

            val slot = config.getInt("slot", -1)
            
            if (slot < 0 || slot > 53 || ConfigUtil.mainConfig.rewardBoxDefaultItem.slots.contains(slot)) {
                return null
            }

            return RewardBoxItem(
                name,
                slot,
                TorosamyCoreAPI.generateItem(config),
                config.getStringList("commands")
            )
        }
    }
}