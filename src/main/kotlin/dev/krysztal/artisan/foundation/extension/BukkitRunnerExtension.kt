package dev.krysztal.artisan.foundation.extension

import dev.krysztal.artisan.foundation.BukkitRunner
import org.bukkit.scheduler.BukkitRunnable

fun BukkitRunner.buildRunner(): BukkitRunnable {
    return object : BukkitRunnable() {
        override fun run() {
            this@buildRunner()
        }
    }
}