package dev.krysztal.artisan.events

import dev.krysztal.artisan.foundation.extension.buildRunner
import dev.krysztal.artisan.foundation.extension.isRightHand
import net.kyori.adventure.text.Component
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class EquipmentRepairEvents : Listener {

    @EventHandler
    fun repairEquipment(event: PlayerInteractEvent) {
        if (!event.player.isSneaking) return
        if (!event.isRightHand()) return

        {
            var repairCount = 0
            event.player.equipment.armorContents.forEach {
                if (it.isRepairableBy(event.player.inventory.itemInMainHand)) repairCount++
            }

            val coefficient = ((1.0 / 3.0) / repairCount)
            var repairedPoint = 0.0

            event.player.equipment.armorContents
                .filter { it.durability != 0.toShort() }
                .forEach {
                    if (event.player.level < 1) return@forEach
                    if (it.isRepairableBy(event.player.inventory.itemInMainHand)) {
                        val repairPoint = (it.type.maxDurability * coefficient)

                        it.durability =
                            (it.durability - repairPoint)
                                .toInt()
                                .toShort()
                        event.player.level -= 2
                        event.player.inventory.itemInMainHand.amount -= 1
                        event.player.world.playSound(event.player, Sound.BLOCK_ANVIL_USE, 0.25f, 0.25f)
                        event.player.world.spawnParticle(Particle.LAVA, event.player.location.toCenterLocation(), 8)

                        repairedPoint += repairPoint.toInt()
                    }
                }

            event.player.sendActionBar(Component.text("Repaired $repairedPoint durability totally."))
        }.buildRunner().run()

    }
}