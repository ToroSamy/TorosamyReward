package net.torosamy.torosamyReward.pojo

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.torosamy.torosamyCore.api.TorosamyCoreAPI
import net.torosamy.torosamyCore.utils.MessageUtil
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class SelectInventory {
    val player: Player

    val inventory: Inventory
    
    val rewardBox: RewardBox
    
    val canSelect: Boolean

    val selectItems: HashMap<Int, RewardBoxItem> = HashMap()
    
    public constructor(player: Player, rewardBox: RewardBox) {
        this.player = player
        
        this.inventory = rewardBox.cloneInventory()
        
        this.rewardBox = rewardBox

        val count = rewardBox.getCounts(player.name)

        if (count < 1) {
            this.canSelect = false
            return
        }

        this.canSelect = true
    }
    
    public fun checkSelectAmount(): Boolean {
        return selectItems.size == rewardBox.counts
    }
    
    public fun reward() {
        for (selectItem in selectItems.values) {
            selectItem.runCommands(player)
        }
        rewardBox.reduceCounts(player.name, 1)
        player.closeInventory()
    }
    
    public fun updateSelectButton() {
        val item = inventory.getItem(53) ?: return
        
        val meta = item.itemMeta ?: return
        
        val lore = ArrayList<String>()
        for (boxItem in selectItems.values) {
            lore.add(
                MessageUtil.format("&f"+TorosamyCoreAPI.getDisplayName(boxItem.getItem()))
            )
        }
        meta.lore = lore
        item.itemMeta = meta
    }
    
    public fun open() {
        player.openInventory(inventory)
    }
    
    public fun updateSelect(slot: Int): Boolean {
        val item = rewardBox.items[slot] ?: return false

        if (selectItems.contains(item.getSlot())) {
            selectItems.remove(item.getSlot())
            return true
        }
        
        selectItems[item.getSlot()] = item
        return true
    }
    
}