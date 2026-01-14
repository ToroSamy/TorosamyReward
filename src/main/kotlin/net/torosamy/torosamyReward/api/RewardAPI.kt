package net.torosamy.torosamyReward.api

import net.torosamy.torosamyCore.api.TorosamyCoreAPI
import net.torosamy.torosamyCore.config.ConfigFile
import net.torosamy.torosamyCore.utils.MessageUtil
import net.torosamy.torosamyReward.TorosamyReward
import net.torosamy.torosamyReward.pojo.*
import net.torosamy.torosamyReward.utils.ConfigUtil
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.io.File

object RewardAPI {
    private val selectInventories: HashMap<String, SelectInventory> = HashMap()

    private val timeCommands = HashMap<String, TimeCommand>()

    private val rewardBoxes = HashMap<String, RewardBox>()
    
    private val oreActionRewards = HashMap<String, ActionReward>()
    
    private val cropsActionRewards = HashMap<String, ActionReward>()
    
    private val entityActionRewards = HashMap<String, ActionReward>()

    public var REWARD_BOX_DEFAULT_ITEM: ItemStack = ItemStack(Material.GRAY_STAINED_GLASS_PANE)

    public var REWARD_BOX_SELECT_ITEMS: ItemStack = ItemStack(Material.LIME_WOOL)

    fun openSelectInventory(player: Player, box: RewardBox) {
        val selectInventory = SelectInventory(player, box)

        selectInventories[player.name] = selectInventory

        selectInventory.open()
    }

    fun loadActionReward() {
        loadCropsActionReward()
        loadOreActionReward()
        loadEntityActionReward()
    }
    
    fun getOreActionReward(name: String): ActionReward? {
        return oreActionRewards[name]
    }
    
    fun getCropActionReward(name: String): ActionReward? {
        return cropsActionRewards[name]
    }

    fun getEntityActionReward(name: String): ActionReward? {
        return entityActionRewards[name]
    }
    
    fun loadEntityActionReward() {
        entityActionRewards.clear()

        val configFile = ConfigFile(TorosamyReward.plugin, "action-reward.yml")

        val config = configFile.config ?: return

        val section = config.getConfigurationSection("entity") ?: return

        section.getKeys(false).forEach {
            entityActionRewards[it] = ActionReward(section.getConfigurationSection(it))
        }
    }
    
    fun loadCropsActionReward() {
        cropsActionRewards.clear()

        val configFile = ConfigFile(TorosamyReward.plugin, "action-reward.yml")

        val config = configFile.config ?: return

        val section = config.getConfigurationSection("crops") ?: return

        section.getKeys(false).forEach {
            cropsActionRewards[it] = ActionReward(section.getConfigurationSection(it))
        }
    }
    
    fun loadOreActionReward() {
        oreActionRewards.clear()

        val configFile = ConfigFile(TorosamyReward.plugin, "action-reward.yml")

        val config = configFile.config ?: return

        val section = config.getConfigurationSection("ore") ?: return
        
        section.getKeys(false).forEach {
            oreActionRewards[it] = ActionReward(section.getConfigurationSection(it))
        }
    }

    fun getBoxNames(): List<String> {
        return rewardBoxes.keys.toList()
    }

    fun saveRewardBoxes() {
        val folder = File(TorosamyReward.plugin.dataFolder, "reward-box")

        if (!folder.exists()) {
            folder.mkdirs()
        }

        rewardBoxes.forEach{(name, box)->
            val config = box.generateConfig()
            val file = File(folder, name + ".yml")
            config.save(file)
        }
    }

    fun getSelectInventory(playerName: String): SelectInventory? {
        return selectInventories[playerName]
    }

    fun closeSelectInventory(player: Player) {
        selectInventories.remove(player.name)
    }

    fun loadRewardBoxes() {
        selectInventories.clear()

        val config = ConfigFile(TorosamyReward.plugin, "config.yml").config

        val defaultItemSection = config.getConfigurationSection("reward-box-default-item")
        REWARD_BOX_DEFAULT_ITEM = TorosamyCoreAPI.generateItem(defaultItemSection)

        val selectItemSection = config.getConfigurationSection("reward-box-select-items")
        REWARD_BOX_SELECT_ITEMS = TorosamyCoreAPI.generateItem(selectItemSection)

        rewardBoxes.clear()

        TorosamyCoreAPI.getConfigs(TorosamyReward.plugin, listOf("reward-box")).values.forEach{
            val rewardBox = RewardBox.generateInstance(it)

            if (rewardBox != null) {
                rewardBoxes[rewardBox.name] = rewardBox
            }
        }

        Bukkit.getConsoleSender().sendMessage(MessageUtil.format(ConfigUtil.langConfig.loadBoxMessage.replace("%amount%", rewardBoxes.size.toString())))
    }

    fun getRewardBox(boxName: String): RewardBox? {
        return rewardBoxes[boxName]
    }

    fun generateEmptyRewardBox(): Inventory {
        val displayInv = Bukkit.createInventory(
            SelectInventoryHolder.SELECT_INVENTORY_HOLDER,
            54,
            MessageUtil.format(ConfigUtil.langConfig.viewTitle)
        )

        ConfigUtil.mainConfig.rewardBoxDefaultItem.slots.forEach {
            displayInv.setItem(it, REWARD_BOX_DEFAULT_ITEM)
        }

        displayInv.setItem(53, REWARD_BOX_SELECT_ITEMS)

        return displayInv
    }

    fun loadTimeCommands() {
        timeCommands.values.forEach{
            it.cancel()
        }
        
        timeCommands.clear()
        
        TorosamyCoreAPI.getConfigs(TorosamyReward.plugin, listOf("time-command")).values.forEach{config->
            config.getKeys(false).forEach {
                val section = config.getConfigurationSection(it)
                val timeCommand = TimeCommand.generateInstance(it, section)
                if (timeCommand != null) {
                    timeCommands[it] = timeCommand

                    for (player in Bukkit.getOnlinePlayers()) {
                        timeCommand.start(player)
                    }
                }
            }
        }
        Bukkit.getConsoleSender().sendMessage(MessageUtil.format(ConfigUtil.langConfig.loadTimeCommandMessage.replace("%amount%", timeCommands.size.toString())))
    }

    fun cancelTimeCommands() {
        timeCommands.values.forEach {
            it.cancel()
        }
    }

    fun startTimeCommands(player: Player) {
        timeCommands.values.forEach{
            it.start(player)
        }
    }

    fun cancelTimeCommands(player: Player) {
        timeCommands.values.forEach{
            it.cancel(player.name)
        }
    }
}