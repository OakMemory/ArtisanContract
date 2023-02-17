package dev.krysztal.artisan

import dev.krysztal.artisan.events.EquipmentRepairEvents
import dev.krysztal.artisan.events.ExperienceAbsorptionEvents
import dev.krysztal.artisan.events.ToolUseEvents
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Artisan() : JavaPlugin() {

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(ExperienceAbsorptionEvents(), this)
        Bukkit.getPluginManager().registerEvents(ToolUseEvents(), this)
        Bukkit.getPluginManager().registerEvents(EquipmentRepairEvents(), this)
    }

    override fun onDisable() {

    }
}