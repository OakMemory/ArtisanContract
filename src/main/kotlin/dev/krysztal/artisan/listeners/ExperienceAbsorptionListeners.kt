package dev.krysztal.artisan.listeners

import dev.krysztal.artisan.ArtisanConfig
import dev.krysztal.artisan.foundation.extension.isRightHand
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import java.util.*

class ExperienceAbsorptionListeners : Listener {

    @EventHandler
    fun useAbsorbItemOnBlock(event: PlayerInteractEvent) {
        if (!event.player.isSneaking) return
        if (event.player.inventory.itemInMainHand.type != ArtisanConfig.ABSORB_ITEM) return
        if (!event.isRightHand()) return
        if (event.player.hasCooldown(ArtisanConfig.ABSORB_ITEM)) return

        val conversion = ArtisanConfig.EXPERIENCE_CONVERSIONS
            .filter { it.material == event.clickedBlock?.type }
            .firstOrNull()
            ?: return

        val random = Random()
        event.player.giveExp(
            conversion.exp + random.nextInt(conversion.random),
            conversion.mending
        )
        event.player.world.playSound(
            event.clickedBlock?.location!!,
            Sound.BLOCK_AMETHYST_BLOCK_STEP,
            1.0f,
            random.nextFloat(1.0f, 3.0f)
        )
        event.player.world.setType(event.clickedBlock!!.location, Material.AIR)
        event.player.world.spawnParticle(Particle.CLOUD, event.clickedBlock!!.location.toCenterLocation(), 8)
        event.player.setCooldown(ArtisanConfig.ABSORB_ITEM, 5)
    }

    @EventHandler
    fun useItemOnAbsorbBlock(event: PlayerInteractEvent) {

        if (event.clickedBlock?.type != ArtisanConfig.ABSORB_BLOCK) return
        if (!event.isRightHand()) return

        val itemInMainHand = event.player.inventory.itemInMainHand.type
        val random = Random()

        val conversion = ArtisanConfig.EXPERIENCE_CONVERSIONS
            .filter { it.material == itemInMainHand }
            .firstOrNull()
            ?: return

        // Cancel the event to prevent the block from being placed
        event.isCancelled = true

        event.player.giveExp(conversion.exp + random.nextInt(conversion.random), conversion.mending)

        // Decrease item in main hand.
        event.player.inventory.itemInMainHand.amount -= 1
        event.player.world.playSound(
            event.clickedBlock?.location!!,
            Sound.BLOCK_AMETHYST_BLOCK_STEP,
            1.0f,
            1.0f
        )
        event.player.world.spawnParticle(Particle.END_ROD, event.clickedBlock!!.location.toCenterLocation(), 8)
    }

}