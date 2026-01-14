package net.torosamy.torosamyReward.pojo

import net.torosamy.torosamyCore.utils.MessageUtil
import net.torosamy.torosamyReward.api.RewardAPI
import net.torosamy.torosamyReward.utils.ConfigUtil
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class RewardBox {
    companion object {
        fun generateInstance(config: ConfigurationSection?): RewardBox? {
            if (config == null) {
                return null
            }

            val name = config.getString("name", null) ?: return null
            
            val counts = config.getInt("counts", 0)
            
            if (counts < 1 || counts > 54) return null
        
            return RewardBox(name, counts, config)
        }
    }

    val name: String

    val items = HashMap<Int, RewardBoxItem>()

    private val players: HashMap<String, Int> = HashMap()
    
    val counts: Int

    val inventory: Inventory
    
    public fun generateConfig(): YamlConfiguration {
        val config = YamlConfiguration()
        
        config.set("name", name)
        config.set("counts", counts)
        items.values.forEach{
            config.set("items." + it.getName(), it.getConfig())
        }
        val list = ArrayList<String>()
        
        players.forEach{(playerName, count)-> 
            list.add("$playerName:$count")
        }
        config.set("players", list)
        return config
    }
    
//    public fun getSlots(): List<Int> {
//        return items.keys.toList()
//    }
    
    public fun cloneInventory(): Inventory {
        val newInventory = Bukkit.createInventory(
            SelectInventoryHolder.SELECT_INVENTORY_HOLDER,
            54,
            MessageUtil.format(ConfigUtil.langConfig.viewTitle)
        )
        
        val contents: Array<ItemStack?> = inventory.contents

        newInventory.contents = contents
            .map { 
                it?.clone()
            }.toTypedArray()
        
        return newInventory
    }
    
    private constructor(name: String, counts: Int, config: ConfigurationSection) {
        this.name = name
        this.counts = counts
        
        val inventory: Inventory = RewardAPI.generateEmptyRewardBox()
        
        val itemsSection = config.getConfigurationSection("items")
        
        if (itemsSection != null) {
            for (it in itemsSection.getKeys(false)) {
                val item = RewardBoxItem.generateInstance(
                    it,
                    itemsSection.getConfigurationSection(it)
                ) ?: continue
                
                items[item.getSlot()] = item

                inventory.setItem(item.getSlot(), item.getItem())
            }
        }
        this.inventory = inventory
        
        for (it in config.getStringList("players")) {
            val split = it.split(":".toRegex()).dropLastWhile { 
                it.isEmpty() 
            }.toTypedArray()

            if (split.size < 2) {
                continue
            }

            val playerName = split[0]
            val count = split[1].toInt()
            
            players[playerName] = count
        }
    }

    fun reduceCounts(playerName: String, counts: Int) {
        players.put(
            playerName,
            players.getOrDefault(playerName, 0) - counts
        )
    }

    fun getCounts(playerName: String?): Int {
        return players.getOrDefault(playerName, 0)
    }

    fun addCounts(player: Player, counts: Int) {
        val playerName = player.name
        players.put(
            playerName,
            players.getOrDefault(playerName, 0) + counts
        )
    }
}