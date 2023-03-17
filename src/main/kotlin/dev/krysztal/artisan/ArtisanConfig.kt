package dev.krysztal.artisan

object ArtisanConfig {
    var REPAIR_EXPERIENCE_COST: Int = 0
    var REFINE_EXPERIENCE_COST: Int = 0
    var EXPERIENCE_CONVERSIONS: List<ExperienceConversion> = emptyList()

    var REFINE_TOOL_AXES: List<String>
    var REFINE_TOOL_PICKAXES: List<String>

    init {
        Artisan.pluginInstance.saveDefaultConfig()

        // Process primitive type
        this.REFINE_EXPERIENCE_COST = Artisan.pluginInstance.config.getInt("refine.cost")
        this.REPAIR_EXPERIENCE_COST = Artisan.pluginInstance.config.getInt("repair.cost")
        this.EXPERIENCE_CONVERSIONS =
            Artisan.pluginInstance.config.getList("experience", EXPERIENCE_CONVERSIONS) as List<ExperienceConversion>
        this.REFINE_TOOL_AXES = Artisan.pluginInstance.config.getStringList("refine.axes")
        this.REFINE_TOOL_PICKAXES = Artisan.pluginInstance.config.getStringList("refine.pickaxes")
    }
}


data class ExperienceConversion(
    val name: String,
    val exp: Int,
    val max: Int = 0,
    val min: Int = 0,
)

