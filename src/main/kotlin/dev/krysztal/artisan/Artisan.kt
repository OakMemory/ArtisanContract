package dev.krysztal.artisan

import dev.krysztal.artisan.listeners.EquipmentRepairListeners
import dev.krysztal.artisan.listeners.ExperienceAbsorptionListeners
import dev.krysztal.artisan.listeners.RefiningBlockListeners
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Artisan() : JavaPlugin() {

    override fun onEnable() {
        pluginInstance = this

        this.logger.info("Initializing the configuration file...")
        this.logger.info("Total number of registered conversion tables:     ${ArtisanConfig.EXPERIENCE_CONVERSIONS.count()}")
        this.logger.info("Total registered axes available for refining:     ${ArtisanConfig.AVAILABLE_AXES.count()}")
        this.logger.info("Total registered pickaxes available for refining: ${ArtisanConfig.AVAILABLE_PICKAXES.count()}")
        this.logger.info("Total registered pickaxes' mapping:               ${ArtisanConfig.PICKAXE_MATERIAL_REFINE_MAPPING.count()}")
        this.logger.info("Configuration file initialized successfully!")

        this.logger.info("Initializing the material list...")
        ArtisanConfig.AVAILABLE_AXES
        this.logger.info("Material list initialization is complete!")

        this.logger.info("Listener registration in progress...")
        Bukkit.getPluginManager().registerEvents(ExperienceAbsorptionListeners(), this)
        Bukkit.getPluginManager().registerEvents(RefiningBlockListeners(), this)
        Bukkit.getPluginManager().registerEvents(EquipmentRepairListeners(), this)
        this.logger.info("Successfully registered listener!")
    }

    override fun onDisable() {
    }

    companion object {
        lateinit var pluginInstance: JavaPlugin
    }
}
