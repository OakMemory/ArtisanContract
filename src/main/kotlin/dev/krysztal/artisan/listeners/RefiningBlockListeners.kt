package dev.krysztal.artisan.listeners

import dev.krysztal.artisan.ArtisanConfig
import dev.krysztal.artisan.ArtisanConfig.AVAILABLE_AXES_MATERIALS
import dev.krysztal.artisan.ArtisanConfig.AVAILABLE_PICKAXES_MATERIALS
import dev.krysztal.artisan.ArtisanConfig.PICKAXE_MATERIAL_REFINE_MAPPING
import dev.krysztal.artisan.foundation.extension.isRightHand
import dev.krysztal.artisan.foundation.extension.itemInMainHand
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.Tag
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

class RefiningBlockListeners : Listener {

    @EventHandler
    fun pickaxeRefineOres(event: PlayerInteractEvent) {
        val checkResult = check(event, AVAILABLE_PICKAXES_MATERIALS)
        val fortune = checkResult.second

        if (!checkResult.first) return
        val material = PICKAXE_MATERIAL_REFINE_MAPPING[event.clickedBlock?.type] ?: return

        val dropStack = ItemStack(
            material,
            1 + Random.nextInt(0, fortune),
        )

        breakAndDrop(event, dropStack)
    }

    @EventHandler
    fun axeRefine(event: PlayerInteractEvent) {
        if (!event.player.isSneaking) return

        val checkResult = check(event, AVAILABLE_AXES_MATERIALS)
        val fortune = checkResult.second

        if (!checkResult.first) return

        val material = event.clickedBlock?.type ?: return

        if (!Tag.LOGS.isTagged(material)) return

        val dropStack = ItemStack(
            Material.CHARCOAL, 1 + Random.nextInt(0, fortune)
        )

        breakAndDrop(event, dropStack)

    }

    private fun check(event: PlayerInteractEvent, itemList: List<Material>): Pair<Boolean, Int> {
        if (!event.action.isRightClick) return Pair(false, 0)
        else if (!event.isRightHand()) return Pair(false, 0)
        else if (!itemList.contains(event.player.inventory.itemInMainHand.type)) return Pair(false, 0)
        else if (event.player.exp < 20 && event.player.level < 1) return Pair(false, 0)

        val fortune = event.player.inventory.itemInMainHand.enchantments[Enchantment.LOOT_BONUS_BLOCKS] ?: 1
        return Pair(true, fortune)
    }

    private fun breakAndDrop(event: PlayerInteractEvent, dropStack: ItemStack) {
        if (event.player.hasCooldown(event.player.itemInMainHand().type)) return
        val clickedBlock = event.clickedBlock ?: return
        
        event.player.world.dropItemNaturally(clickedBlock.location, dropStack)

        event.player.world.setType(clickedBlock.location, Material.AIR)
        event.player.world.spawnParticle(
            Particle.FLAME, clickedBlock.location.toCenterLocation(), 64, 0.3, 0.3, 0.3
        )
        event.player.giveExp(-ArtisanConfig.REFINE_EXPERIENCE_COST)
        event.player.playSound(
            clickedBlock.location.toCenterLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1f, 1f
        )
        event.player.setCooldown(event.player.itemInMainHand().type, 5)
    }
}
