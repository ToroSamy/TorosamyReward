package net.torosamy.torosamyReward.listener;

import net.torosamy.torosamyCore.utils.MessageUtil
import net.torosamy.torosamyReward.api.RewardAPI
import net.torosamy.torosamyReward.pojo.SelectInventoryHolder
import net.torosamy.torosamyReward.utils.ConfigUtil
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class RewardBoxListener : Listener {
    @EventHandler
    public fun clickGUI(event: InventoryClickEvent) {
        val inventory = event.view.topInventory

        if (!SelectInventoryHolder.isHolder(inventory)) {
            return
        }
        event.isCancelled = true;
        val entity: HumanEntity = event.whoClicked
        
        if (entity !is Player) {
            return
        }
        
        val player: Player = entity

        val selectInventory = RewardAPI.getSelectInventory(player.name) ?: return
        
        val slot = event.rawSlot
        
        if (selectInventory.updateSelect(slot)) {
            selectInventory.updateSelectButton()
            return
        }
        if (slot != 53) {
            return
        }

        if (!selectInventory.canSelect) {
            player.sendMessage(MessageUtil.format(ConfigUtil.langConfig.boxCountInsufficient))
            player.closeInventory()
            return
        }

        val errorSize = selectInventory.selectItems.size
        val rightSize = selectInventory.rewardBox.counts

        if(errorSize != rightSize) {
            player.sendMessage(MessageUtil.format(ConfigUtil.langConfig.selectAmountError)
                .replace("%right_amount%", rightSize.toString())
                .replace("%error_amount%", errorSize.toString())
            )
            player.closeInventory()
            return
        }
        
        selectInventory.reward()
    }

    @EventHandler
    public fun closeGUI(event: InventoryCloseEvent) {
        val inventory = event.view.topInventory

        if (!SelectInventoryHolder.isHolder(inventory)) {
            return
        }

        val entity: HumanEntity = event.player
        
        if (entity !is Player) {
            return
        }

        val player: Player = entity

        RewardAPI.closeSelectInventory(player)

        
    }
}
