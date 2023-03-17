package dev.krysztal.artisan

import org.bukkit.Material

object ArtisanConfig {
    var REPAIR_COST: Int = 0
    var REFINE_EXPERIENCE_COST: Int = 0

    var EXPERIENCE_CONVERSIONS: List<ExperienceConversion> = emptyList()
    var ABSORPTION_APPLY_MENDING: Boolean = true;

    var REFINE_AVAILABLE_AXES: List<String>
    var REFINE_AVAILABLE_PICKAXES: List<String>

    var REFINE_PICKAXE_ITEM_MAPPING: List<RefineMapping> = emptyList()

    // Processed result
    var AVAILABLE_PICKAXES: List<Material> = emptyList()
    var AVAILABLE_AXES: List<Material> = emptyList()
    var PICKAXE_ITEM_REFINE: Map<Material, Material> = emptyMap()

    init {
        Artisan.pluginInstance.saveDefaultConfig()

        // Process primitive type
        this.REFINE_EXPERIENCE_COST = Artisan.pluginInstance.config.getInt("refine.cost_experience")
        this.REPAIR_COST = Artisan.pluginInstance.config.getInt("repair.cost_level")
        this.ABSORPTION_APPLY_MENDING = Artisan.pluginInstance.config.getBoolean("absorption_apply_mending")

        this.EXPERIENCE_CONVERSIONS =
            (Artisan.pluginInstance.config.getList(
                "experience_conversion",
                EXPERIENCE_CONVERSIONS
            ) as ArrayList).map {
                val c = it as LinkedHashMap<String, *>
                ExperienceConversion(
                    name = c["name"]!! as String,
                    exp = c["exp"]!! as Int,
                    max = c["max"]!! as Int,
                    min = c["min"]!! as Int
                )
            }

        this.REFINE_AVAILABLE_AXES = Artisan.pluginInstance.config.getStringList("refine.axes")
        this.REFINE_AVAILABLE_PICKAXES = Artisan.pluginInstance.config.getStringList("refine.pickaxes")

        this.REFINE_PICKAXE_ITEM_MAPPING =
            (Artisan.pluginInstance.config.getList(
                "refine.pickaxe_mapping",
                this.REFINE_PICKAXE_ITEM_MAPPING
            ) as ArrayList).map {
                val c = it as LinkedHashMap<String, String>
                RefineMapping(from = c["from"]!!, to = c["to"]!!)
            }

        this.AVAILABLE_PICKAXES = buildMaterialList(REFINE_AVAILABLE_PICKAXES)
        this.AVAILABLE_AXES = buildMaterialList(REFINE_AVAILABLE_AXES)

        val map = mutableMapOf<Material, Material>()
        Artisan.pluginInstance.logger.info(REFINE_PICKAXE_ITEM_MAPPING.toString())

        for (it in REFINE_PICKAXE_ITEM_MAPPING) {
            val from = Material.getMaterial(it.from.uppercase())
            val to = Material.getMaterial(it.to.uppercase())
            if (from != null && to != null)
                map[from] = to
            else
                Artisan.pluginInstance.logger.info(
                    "Error during conversion: failed to convert ${it.from} or ${it.to} to Material enumeration"
                )
        }

        this.PICKAXE_ITEM_REFINE = map

    }
}

data class ExperienceConversion(
    val name: String,
    val exp: Int,
    val max: Int = 0,
    val min: Int = 0,
)

data class RefineMapping(
    val from: String,
    val to: String,
)

fun buildMaterialList(x: List<String>): List<Material> {
    val list = mutableListOf<Material>()
    x.forEach {
        if (Material.getMaterial(it.uppercase()) != null)
            list.add(Material.getMaterial(it.uppercase())!!)
    }
    return list
}