package dev.krysztal.artisan.listeners

import dev.krysztal.artisan.ArtisanConfig
import dev.krysztal.artisan.foundation.extension.isRightHand
import net.kyori.adventure.text.Component
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class EquipmentRepairListeners : Listener {

    @EventHandler
    fun repairEquipment(event: PlayerInteractEvent) {
        if (!event.player.isSneaking) return
        if (!event.isRightHand()) return

        var repairCount = 0
        event.player.equipment.armorContents
            .filterNotNull()
            .forEach {
                if (it.isRepairableBy(event.player.inventory.itemInMainHand)) repairCount++
            }

        val coefficient = ((1.0 / 3.0) / repairCount)
        var repairedDurability = 0.0

        event.player.equipment.armorContents
            .filterNotNull()
            .filter { it.durability != 0.toShort() }
            .forEach {
                if (event.player.level < ArtisanConfig.REPAIR_COST) {
                    event.player.sendActionBar(Component.text("You don't have enough level to fix your armors."))
                    return@forEach
                }
                if (it.isRepairableBy(event.player.inventory.itemInMainHand)) {
                    val repairPoint = (it.type.maxDurability * coefficient)

                    it.durability =
                        (it.durability - repairPoint)
                            .toInt()
                            .toShort()
                    event.player.level -= ArtisanConfig.REPAIR_COST
                    event.player.inventory.itemInMainHand.amount -= 1
                    event.player.world.playSound(event.player, Sound.BLOCK_ANVIL_USE, 0.25f, 0.25f)
                    event.player.world.spawnParticle(Particle.LAVA, event.player.location.toCenterLocation(), 8)

                    repairedDurability += repairPoint.toInt()
                }
            }
        if (repairedDurability != 0.0)
            event.player.sendActionBar(Component.text("Repaired $repairedDurability durability totally."))
    }
}