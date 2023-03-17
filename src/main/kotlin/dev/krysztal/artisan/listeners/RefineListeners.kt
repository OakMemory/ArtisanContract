package dev.krysztal.artisan.listeners

import dev.krysztal.artisan.ArtisanConfig.AVAILABLE_AXES
import dev.krysztal.artisan.ArtisanConfig.AVAILABLE_PICKAXES
import dev.krysztal.artisan.ArtisanConfig.PICKAXE_ITEM_REFINE
import dev.krysztal.artisan.foundation.extension.isRightHand
import net.kyori.adventure.text.Component
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

class RefineListeners : Listener {


    @EventHandler
    fun pickaxeRefineOres(event: PlayerInteractEvent) {
        val checkResult = check(event, AVAILABLE_PICKAXES)
        val fortune = checkResult.second

        if (!checkResult.first) return

        if (!PICKAXE_ITEM_REFINE.contains(event.clickedBlock?.type)) return

        val dropStack = ItemStack(
            PICKAXE_ITEM_REFINE[event.clickedBlock!!.type]!!, 1 + Random.nextInt(0, fortune)
        )

        breakAndDrop(event, dropStack)
    }

    @EventHandler
    fun axeRefine(event: PlayerInteractEvent) {

        val checkResult = check(event, AVAILABLE_AXES)
        val fortune = checkResult.second

        if (!checkResult.first) return

        if (!Tag.LOGS.isTagged(event.clickedBlock?.type!!)) return

        val dropStack = ItemStack(
            Material.CHARCOAL, 1 + Random.nextInt(0, fortune)
        )

        breakAndDrop(event, dropStack)

    }

    private fun check(event: PlayerInteractEvent, itemList: List<Material>): Pair<Boolean, Int> {
        if (!event.action.isRightClick) return Pair(false, 0)
        if (!event.isRightHand()) return Pair(false, 0)
        if (!itemList.contains(event.player.inventory.itemInMainHand.type)) return Pair(false, 0)
        if (event.player.exp < 20 && event.player.level < 1) {
            event.player.sendActionBar(Component.text("You don't have enough experience to refining block."))
            return Pair(false, 0)
        }

        val fortune = event.player.inventory.itemInMainHand.enchantments[Enchantment.LOOT_BONUS_BLOCKS] ?: 1
        return Pair(true, fortune)
    }

    private fun breakAndDrop(event: PlayerInteractEvent, dropStack: ItemStack) {
        event.player.world.dropItemNaturally(event.clickedBlock!!.location, dropStack)

        event.player.world.setType(event.clickedBlock!!.location, Material.AIR)
        event.player.world.spawnParticle(Particle.ASH, event.clickedBlock!!.location.toCenterLocation(), 8)
        event.player.giveExp(-20)

        event.player.playSound(
            event.clickedBlock!!.location.toCenterLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1f, 1f
        )
    }
}
