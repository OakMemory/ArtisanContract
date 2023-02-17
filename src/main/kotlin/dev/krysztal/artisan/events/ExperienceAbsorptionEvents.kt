package dev.krysztal.artisan.events

import dev.krysztal.artisan.foundation.extension.isRightHand
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import java.util.*

class ExperienceAbsorptionEvents : Listener {

    @EventHandler
    fun useBookOnAmethyst(event: PlayerInteractEvent) {
        if (!event.player.isSneaking) return
        if (event.player.inventory.itemInMainHand.type != Material.BOOK) return
        if (!event.isRightHand()) return

        val random = Random()

        when (event.clickedBlock?.type) {
            Material.AMETHYST_BLOCK -> event.player.giveExp(20 + random.nextInt(20), true)
            Material.AMETHYST_CLUSTER -> event.player.giveExp(5 + random.nextInt(5), true)
            else -> return
        }

        event.player.world.playSound(event.clickedBlock?.location!!, Sound.BLOCK_AMETHYST_BLOCK_STEP, 1.0f, 1.0f)
        event.player.world.setType(event.clickedBlock!!.location, Material.AIR)
        event.player.world.spawnParticle(Particle.CLOUD, event.clickedBlock!!.location.toCenterLocation(), 8)
    }

    @EventHandler
    fun useOnBookShelf(event: PlayerInteractEvent) {
        if (event.clickedBlock?.type != Material.BOOKSHELF) return
        if (!event.isRightHand()) return

        val itemInMainHand = event.player.inventory.itemInMainHand.type

        val random = Random()


        when (itemInMainHand) {
            Material.AMETHYST_SHARD -> event.player.giveExp(5 + random.nextInt(5), true)
            Material.AMETHYST_BLOCK -> event.player.giveExp(20 + random.nextInt(20), true)
            Material.ECHO_SHARD -> event.player.giveExp(200 + random.nextInt(100), true)
            else -> return
        }

        // Decrease item in main hand.
        event.player.inventory.itemInMainHand.amount -= 1
        event.player.world.playSound(event.clickedBlock?.location!!, Sound.BLOCK_AMETHYST_BLOCK_STEP, 1.0f, 1.0f)
        event.player.world.spawnParticle(Particle.END_ROD, event.clickedBlock!!.location.toCenterLocation(), 8)

    }
}