package dev.krysztal.artisan

import dev.krysztal.artisan.foundation.extension.toMaterial
import org.bukkit.Material

object ArtisanConfig {
    var REPAIR_COST: Int = 0
    var REFINE_EXPERIENCE_COST: Int = 0

    var EXPERIENCE_CONVERSIONS: List<ExperienceConversion> = emptyList()

    var AVAILABLE_AXES: List<String>
    var AVAILABLE_PICKAXES: List<String>

    private var REFINE_PICKAXE_ITEM_MAPPING: List<RefineMapping> = emptyList()

    // Processed result
    var AVAILABLE_PICKAXES_MATERIALS: List<Material> = emptyList()
    var AVAILABLE_AXES_MATERIALS: List<Material> = emptyList()
    var PICKAXE_MATERIAL_REFINE_MAPPING: Map<Material, Material> = emptyMap()

    lateinit var ABSORB_ITEM: Material
    lateinit var ABSORB_BLOCK: Material

    init {
        Artisan.pluginInstance.saveDefaultConfig()

        // Process primitive type
        this.REFINE_EXPERIENCE_COST = Artisan.pluginInstance.config.getInt("refine.cost_experience")
        this.REPAIR_COST = Artisan.pluginInstance.config.getInt("repair.cost_level")

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        this.EXPERIENCE_CONVERSIONS = run {
            val processedList = mutableListOf<ExperienceConversion>()

            (Artisan.pluginInstance.config.getList(
                "experience_conversion",
                EXPERIENCE_CONVERSIONS
            ) as ArrayList).forEach {
                val configObject = it as LinkedHashMap<String, *>
                val material = (configObject["material"]!! as String).toMaterial()
                if (material == null) {
                    Artisan.pluginInstance.logger.info(
                        "An error occurred while converting " +
                                "${configObject["material"]!! as String} to Material in the conversion table, " +
                                "so the entry was skipped"
                    )
                    return@forEach
                }

                processedList.add(
                    ExperienceConversion(
                        material = material,
                        exp = configObject["exp"] as Int? ?: 0,
                        random = configObject["random"] as Int? ?: 0,
                        mending = configObject["mending"] as Boolean? ?: false,
                    )
                )
            }

            processedList
        }

        this.AVAILABLE_AXES = Artisan.pluginInstance.config.getStringList("refine.axes")
        this.AVAILABLE_PICKAXES = Artisan.pluginInstance.config.getStringList("refine.pickaxes")

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        this.REFINE_PICKAXE_ITEM_MAPPING =
            (Artisan.pluginInstance.config.getList(
                "refine.pickaxe_mapping",
                this.REFINE_PICKAXE_ITEM_MAPPING
            ) as ArrayList).map {
                val c = it as LinkedHashMap<String, String>
                RefineMapping(from = c["from"]!!, to = c["to"]!!)
            }

        this.AVAILABLE_PICKAXES_MATERIALS = buildMaterialList(AVAILABLE_PICKAXES)
        this.AVAILABLE_AXES_MATERIALS = buildMaterialList(AVAILABLE_AXES)

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        val map = mutableMapOf<Material, Material>()
        REFINE_PICKAXE_ITEM_MAPPING.forEach {
            val from = it.from.toMaterial()
            val to = it.to.toMaterial()
            if (from != null && to != null)
                map[from] = to
            else
                Artisan.pluginInstance.logger.info(
                    "Error during conversion: failed to convert ${it.from} or ${it.to} to Material enumeration"
                )
        }
        this.PICKAXE_MATERIAL_REFINE_MAPPING = map

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // I think I've done my best to make sure this plugin is working properly...
        ABSORB_BLOCK = (Artisan.pluginInstance.config.getString("absorb.block")
            ?: "bookshelf")
            .toMaterial()
            ?: Material.BOOKSHELF
        ABSORB_ITEM = (Artisan.pluginInstance.config.getString("absorb.item")
            ?: "book")
            .toMaterial()
            ?: Material.BOOK
    }
}

data class ExperienceConversion(
    val material: Material,
    val exp: Int,
    val random: Int,
    val mending: Boolean,
)

data class RefineMapping(
    val from: String,
    val to: String,
)

fun buildMaterialList(x: List<String>) = x.map { it.toMaterial() }.filterNotNull()

