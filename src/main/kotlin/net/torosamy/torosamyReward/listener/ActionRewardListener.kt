package net.torosamy.torosamyReward.listener

import net.torosamy.torosamyCore.api.TorosamyCoreAPI
import net.torosamy.torosamyReward.api.RewardAPI
import org.bukkit.block.data.Ageable
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDeathEvent


class ActionRewardListener : Listener {
    @EventHandler
    fun breakRewardListener(event: BlockBreakEvent) {
        if (!event.isDropItems) {
            return
        }

        val block = event.block
        
        if (block.blockData is Ageable) {
            val actionReward = RewardAPI.getCropActionReward(block.type.name) ?: return

            if (!actionReward.enableWorld.contains(event.player.world.name)) {
                return
            }
            
            val ageAble = block.blockData as Ageable
            
            if (ageAble.age != ageAble.maximumAge) {
                return
            }
            
            TorosamyCoreAPI.runCommands(event.player, actionReward.commands, actionReward.denyCommands)
        }
        
        if (block.type.name.contains("_ORE")) {
            val actionReward = RewardAPI.getOreActionReward(block.type.name) ?: return

            if (!actionReward.enableWorld.contains(event.player.world.name)) {
                return
            }
            
            val tool = event.player.equipment.itemInMainHand
            
            if (tool.containsEnchantment(Enchantment.SILK_TOUCH)) {
                return
            }
            
            TorosamyCoreAPI.runCommands(event.player, actionReward.commands, actionReward.denyCommands)
        }
    }
    
    @EventHandler
    fun killEntity(event: EntityDeathEvent) {
        val killer = event.entity.killer ?: return
        
        val actionReward = RewardAPI.getEntityActionReward(event.entity.type.name) ?: return
        
        if (!actionReward.enableWorld.contains(killer.world.name)) {
            return
        }
        
        TorosamyCoreAPI.runCommands(killer, actionReward.commands, actionReward.denyCommands)
    }
}