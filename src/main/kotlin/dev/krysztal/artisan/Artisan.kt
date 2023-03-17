package dev.krysztal.artisan

import dev.krysztal.artisan.listeners.EquipmentRepairListeners
import dev.krysztal.artisan.listeners.ExperienceAbsorptionListeners
import dev.krysztal.artisan.listeners.RefineListeners
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Artisan() : JavaPlugin() {

    override fun onEnable() {
        pluginInstance = this

        ArtisanConfig.REFINE_TOOL_AXES

        Bukkit.getPluginManager().registerEvents(ExperienceAbsorptionListeners(), this)
        Bukkit.getPluginManager().registerEvents(RefineListeners(), this)
        Bukkit.getPluginManager().registerEvents(EquipmentRepairListeners(), this)
    }

    override fun onDisable() {

    }

    companion object {
        lateinit var pluginInstance: JavaPlugin
    }
}