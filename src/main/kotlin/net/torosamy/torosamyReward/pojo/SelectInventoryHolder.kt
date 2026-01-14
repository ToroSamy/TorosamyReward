package net.torosamy.torosamyReward.pojo

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class SelectInventoryHolder: InventoryHolder {
    companion object {
        public val SELECT_INVENTORY_HOLDER = SelectInventoryHolder()
        
        public fun isHolder(inventory: Inventory): Boolean {
            return inventory.holder is SelectInventoryHolder
        }
    }
    
    override fun getInventory(): Inventory {
        throw UnsupportedOperationException("This InventoryHolder is only used as a marker.");
    }
    
    
}