package dev.krysztal.artisan.foundation.extension

import org.bukkit.Material

fun String.toMaterial() = Material.getMaterial(this.uppercase())
