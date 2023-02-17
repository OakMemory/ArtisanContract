package dev.krysztal.artisan.foundation.extension

import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

fun PlayerInteractEvent.isRightHand(): Boolean = this.hand == EquipmentSlot.HAND


