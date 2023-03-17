package dev.krysztal.artisan

import dev.krysztal.artisan.listeners.EquipmentRepairListeners
import dev.krysztal.artisan.listeners.ExperienceAbsorptionListeners
import dev.krysztal.artisan.listeners.RefineListeners
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Artisan() : JavaPlugin() {

    override fun onEnable() {
        this.logger.info("Initializing the configuration file...")
        pluginInstance = this
        this.logger.info("Total number of registered conversion tables:\t\t${ArtisanConfig.EXPERIENCE_CONVERSIONS.count()}")
        this.logger.info("Total registered axes available for refining:\t\t${ArtisanConfig.REFINE_TOOL_AXES.count()}")
        this.logger.info("Total registered pickaxes available for refining:\t${ArtisanConfig.REFINE_TOOL_PICKAXES.count()}")
        this.logger.info("Configuration file initialized successfully!")

        this.logger.info("Listener registration in progress...")
        Bukkit.getPluginManager().registerEvents(ExperienceAbsorptionListeners(), this)
        Bukkit.getPluginManager().registerEvents(RefineListeners(), this)
        Bukkit.getPluginManager().registerEvents(EquipmentRepairListeners(), this)
        this.logger.info("Successfully registered listener!")
    }

    override fun onDisable() {
        
    }

    companion object {
        lateinit var pluginInstance: JavaPlugin
    }
}