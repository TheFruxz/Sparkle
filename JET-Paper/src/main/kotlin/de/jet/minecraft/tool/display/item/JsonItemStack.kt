@file:Suppress("DEPRECATION", "SENSELESS_COMPARISON", "DuplicatedCode", "ReplaceNegatedIsEmptyWithIsNotEmpty")
package de.jet.minecraft.tool.display.item

import com.google.gson.*
import org.bukkit.Color
import org.bukkit.DyeColor
import org.bukkit.FireworkEffect
import org.bukkit.FireworkEffect.Type
import org.bukkit.Material
import org.bukkit.block.banner.Pattern
import org.bukkit.block.banner.PatternType
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.*
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*
import java.util.function.Consumer

/**
 * Parse [ItemStack] to JSON
 *
 * @author DevSrSouza
 * @version 1.0
 *
 * https://github.com/DevSrSouza/
 * You can find updates here https://gist.github.com/DevSrSouza
 */
object JsonItemStack {
    private val BYPASS_CLASS = arrayOf(
        "CraftMetaBlockState", "CraftMetaItem" /*Glowstone Support*/, "GlowMetaItem"
    )

    /**
     * Parse the [ItemStack] to JSON
     *
     * @param itemStack The [ItemStack] instance
     * @return The JSON string
     */
    fun toJson(itemStack: ItemStack): String {
        val gson = Gson()
        val itemJson = JsonObject()
        itemJson.addProperty("type", itemStack.type.name)
        if (itemStack.durability > 0) itemJson.addProperty("data", itemStack.durability)
        if (itemStack.amount != 1) itemJson.addProperty("amount", itemStack.amount)
        if (itemStack.hasItemMeta()) {
            val metaJson = JsonObject()
            val meta = itemStack.itemMeta
            if (meta.hasDisplayName()) {
                metaJson.addProperty("displayname", meta.displayName)
            }
            if (meta.hasLore()) {
                val lore = JsonArray()
                meta.lore!!.forEach(Consumer { str: String? ->
                    lore.add(
                        JsonPrimitive(str)
                    )
                })
                metaJson.add("lore", lore)
            }
            if (meta.hasEnchants()) {
                val enchants = JsonArray()
                meta.enchants.forEach { (enchantment: Enchantment, integer: Int) ->
                    enchants.add(
                        JsonPrimitive(enchantment.name + ":" + integer)
                    )
                }
                metaJson.add("enchants", enchants)
            }
            if (!meta.itemFlags.isEmpty()) {
                val flags = JsonArray()
                meta.itemFlags.stream().map { obj: ItemFlag -> obj.name }.forEach { str: String? ->
                    flags.add(
                        JsonPrimitive(
                            str
                        )
                    )
                }
                metaJson.add("flags", flags)
            }
            for (clazz in BYPASS_CLASS) {
                if (meta.javaClass.simpleName == clazz) {
                    itemJson.add("item-meta", metaJson)
                    return gson.toJson(itemJson)
                }
            }
            if (meta is SkullMeta) {
                val skullMeta = meta
                if (skullMeta.hasOwner()) {
                    val extraMeta = JsonObject()
                    extraMeta.addProperty("owner", skullMeta.owner)
                    metaJson.add("extra-meta", extraMeta)
                }
            } else if (meta is BannerMeta) {
                val bannerMeta = meta
                val extraMeta = JsonObject()
                extraMeta.addProperty("base-color", bannerMeta.baseColor!!.name)
                if (bannerMeta.numberOfPatterns() > 0) {
                    val patterns = JsonArray()
                    bannerMeta.patterns
                        .stream()
                        .map { pattern: Pattern ->
                            pattern.color.name + ":" + pattern.pattern.identifier
                        }
                        .forEach { str: String? ->
                            patterns.add(
                                JsonPrimitive(
                                    str
                                )
                            )
                        }
                    extraMeta.add("patterns", patterns)
                }
                metaJson.add("extra-meta", extraMeta)
            } else if (meta is EnchantmentStorageMeta) {
                val esmeta = meta
                if (esmeta.hasStoredEnchants()) {
                    val extraMeta = JsonObject()
                    val storedEnchants = JsonArray()
                    esmeta.storedEnchants.forEach { (enchantment: Enchantment, integer: Int) ->
                        storedEnchants.add(
                            JsonPrimitive(enchantment.name + ":" + integer)
                        )
                    }
                    extraMeta.add("stored-enchants", storedEnchants)
                    metaJson.add("extra-meta", extraMeta)
                }
            } else if (meta is LeatherArmorMeta) {
                val extraMeta = JsonObject()
                extraMeta.addProperty("color", Integer.toHexString(meta.color.asRGB()))
                metaJson.add("extra-meta", extraMeta)
            } else if (meta is BookMeta) {
                val bmeta = meta
                if (bmeta.hasAuthor() || bmeta.hasPages() || bmeta.hasTitle()) {
                    val extraMeta = JsonObject()
                    if (bmeta.hasTitle()) {
                        extraMeta.addProperty("title", bmeta.title)
                    }
                    if (bmeta.hasAuthor()) {
                        extraMeta.addProperty("author", bmeta.author)
                    }
                    if (bmeta.hasPages()) {
                        val pages = JsonArray()
                        bmeta.pages.forEach(Consumer { str: String? ->
                            pages.add(
                                JsonPrimitive(str)
                            )
                        })
                        extraMeta.add("pages", pages)
                    }
                    metaJson.add("extra-meta", extraMeta)
                }
            } else if (meta is PotionMeta) {
                val pmeta = meta
                if (pmeta.hasCustomEffects()) {
                    val extraMeta = JsonObject()
                    val customEffects = JsonArray()
                    pmeta.customEffects.forEach(Consumer { potionEffect: PotionEffect ->
                        customEffects.add(
                            JsonPrimitive(
                                potionEffect.type.name
                                        + ":" + potionEffect.amplifier
                                        + ":" + potionEffect.duration / 20
                            )
                        )
                    })
                    extraMeta.add("custom-effects", customEffects)
                    metaJson.add("extra-meta", extraMeta)
                }
            } else if (meta is FireworkEffectMeta) {
                val femeta = meta
                if (femeta.hasEffect()) {
                    val effect = femeta.effect
                    val extraMeta = JsonObject()
                    extraMeta.addProperty("type", effect!!.type.name)
                    if (effect.hasFlicker()) extraMeta.addProperty("flicker", true)
                    if (effect.hasTrail()) extraMeta.addProperty("trail", true)
                    if (!effect.colors.isEmpty()) {
                        val colors = JsonArray()
                        effect.colors.forEach(Consumer { color: Color ->
                            colors.add(
                                JsonPrimitive(Integer.toHexString(color.asRGB()))
                            )
                        })
                        extraMeta.add("colors", colors)
                    }
                    if (!effect.fadeColors.isEmpty()) {
                        val fadeColors = JsonArray()
                        effect.fadeColors.forEach(Consumer { color: Color ->
                            fadeColors.add(
                                JsonPrimitive(Integer.toHexString(color.asRGB()))
                            )
                        })
                        extraMeta.add("fade-colors", fadeColors)
                    }
                    metaJson.add("extra-meta", extraMeta)
                }
            } else if (meta is FireworkMeta) {
                val fmeta = meta
                val extraMeta = JsonObject()
                extraMeta.addProperty("power", fmeta.power)
                if (fmeta.hasEffects()) {
                    val effects = JsonArray()
                    fmeta.effects.forEach(Consumer { effect: FireworkEffect ->
                        val jsonObject = JsonObject()
                        jsonObject.addProperty("type", effect.type.name)
                        if (effect.hasFlicker()) jsonObject.addProperty("flicker", true)
                        if (effect.hasTrail()) jsonObject.addProperty("trail", true)
                        if (!effect.colors.isEmpty()) {
                            val colors = JsonArray()
                            effect.colors
                                .forEach(Consumer { color: Color ->
                                    colors.add(
                                        JsonPrimitive(Integer.toHexString(color.asRGB()))
                                    )
                                })
                            jsonObject.add("colors", colors)
                        }
                        if (!effect.fadeColors.isEmpty()) {
                            val fadeColors = JsonArray()
                            effect.fadeColors
                                .forEach(Consumer { color: Color ->
                                    fadeColors.add(
                                        JsonPrimitive(Integer.toHexString(color.asRGB()))
                                    )
                                })
                            jsonObject.add("fade-colors", fadeColors)
                        }
                        effects.add(jsonObject)
                    })
                    extraMeta.add("effects", effects)
                }
                metaJson.add("extra-meta", extraMeta)
            } else if (meta is MapMeta) {
                val extraMeta = JsonObject()

                /* 1.11
                if(mmeta.hasLocationName()) {
                    extraMeta.addProperty("location-name", mmeta.getLocationName());
                }
                if(mmeta.hasColor()) {
                    extraMeta.addProperty("color", Integer.toHexString(mmeta.getColor().asRGB()));
                }*/extraMeta.addProperty("scaling", meta.isScaling)
                metaJson.add("extra-meta", extraMeta)
            }
            itemJson.add("item-meta", metaJson)
        }
        return gson.toJson(itemJson)
    }

    /**
     * Parse a JSON to [ItemStack]
     *
     * @param string The JSON string
     * @return The [ItemStack] or null if not succeed
     */
    fun fromJson(string: String?): ItemStack? {
        val parser = JsonParser()
        val element = parser.parse(string)
        return if (element.isJsonObject) {
            val itemJson = element.asJsonObject
            val typeElement = itemJson["type"]
            val dataElement = itemJson["data"]
            val amountElement = itemJson["amount"]
            if (typeElement.isJsonPrimitive) {
                val type = typeElement.asString
                val data = dataElement?.asShort ?: 0
                val amount = amountElement?.asInt ?: 1
                val itemStack = ItemStack(Material.getMaterial(type)!!)
                itemStack.durability = data
                itemStack.amount = amount
                val itemMetaElement = itemJson["item-meta"]
                if (itemMetaElement != null && itemMetaElement.isJsonObject) {
                    val meta = itemStack.itemMeta
                    val metaJson = itemMetaElement.asJsonObject
                    val displaynameElement = metaJson["displayname"]
                    val loreElement = metaJson["lore"]
                    val enchants = metaJson["enchants"]
                    val flagsElement = metaJson["flags"]
                    if (displaynameElement != null && displaynameElement.isJsonPrimitive) {
                        meta.setDisplayName(displaynameElement.asString)
                    }
                    if (loreElement != null && loreElement.isJsonArray) {
                        val jarray = loreElement.asJsonArray
                        val lore: MutableList<String> = ArrayList(jarray.size())
                        jarray.forEach(Consumer { jsonElement: JsonElement ->
                            if (jsonElement.isJsonPrimitive) lore.add(
                                jsonElement.asString
                            )
                        })
                        meta.lore = lore
                    }
                    if (enchants != null && enchants.isJsonArray) {
                        val jarray = enchants.asJsonArray
                        jarray.forEach(Consumer { jsonElement: JsonElement ->
                            if (jsonElement.isJsonPrimitive) {
                                val enchantString = jsonElement.asString
                                if (enchantString.contains(":")) {
                                    try {
                                        val splitEnchant =
                                            enchantString.split(":").toTypedArray()
                                        val enchantment =
                                            Enchantment.getByName(splitEnchant[0])
                                        val level = splitEnchant[1].toInt()
                                        if (enchantment != null && level > 0) {
                                            meta.addEnchant(enchantment, level, true)
                                        }
                                    } catch (ex: NumberFormatException) {
                                    }
                                }
                            }
                        })
                    }
                    if (flagsElement != null && flagsElement.isJsonArray) {
                        val jarray = flagsElement.asJsonArray
                        jarray.forEach(Consumer { jsonElement: JsonElement ->
                            if (jsonElement.isJsonPrimitive) {
                                for (flag in ItemFlag.values()) {
                                    if (flag.name.equals(jsonElement.asString, ignoreCase = true)) {
                                        meta.addItemFlags(flag)
                                        break
                                    }
                                }
                            }
                        })
                    }
                    for (clazz in BYPASS_CLASS) {
                        if (meta.javaClass.simpleName == clazz) {
                            return itemStack
                        }
                    }
                    val extrametaElement = metaJson["extra-meta"]
                    if (extrametaElement != null
                        && extrametaElement.isJsonObject
                    ) {
                        try {
                            val extraJson = extrametaElement.asJsonObject
                            if (meta is SkullMeta) {
                                val ownerElement = extraJson["owner"]
                                if (ownerElement != null && ownerElement.isJsonPrimitive) {
                                    meta.owner = ownerElement.asString
                                }
                            } else if (meta is BannerMeta) {
                                val baseColorElement = extraJson["base-color"]
                                val patternsElement = extraJson["patterns"]
                                val bmeta = meta
                                if (baseColorElement != null && baseColorElement.isJsonPrimitive) {
                                    try {
                                        val color = Arrays.stream(DyeColor.values())
                                            .filter { dyeColor: DyeColor ->
                                                dyeColor.name.equals(
                                                    baseColorElement.asString,
                                                    ignoreCase = true
                                                )
                                            }
                                            .findFirst()
                                        if (color.isPresent) {
                                            bmeta.baseColor = color.get()
                                        }
                                    } catch (ex: NumberFormatException) {
                                    }
                                }
                                if (patternsElement != null && patternsElement.isJsonArray) {
                                    val jarray = patternsElement.asJsonArray
                                    val patterns: MutableList<Pattern> = ArrayList(jarray.size())
                                    jarray.forEach(Consumer { jsonElement: JsonElement ->
                                        val patternString = jsonElement.asString
                                        if (patternString.contains(":")) {
                                            val splitPattern =
                                                patternString.split(":").toTypedArray()
                                            val color =
                                                Arrays.stream(DyeColor.values())
                                                    .filter { dyeColor: DyeColor ->
                                                        dyeColor.name.equals(
                                                            splitPattern[0],
                                                            ignoreCase = true
                                                        )
                                                    }
                                                    .findFirst()
                                            val patternType =
                                                PatternType.getByIdentifier(splitPattern[1])
                                            if (color.isPresent && patternType != null) {
                                                patterns.add(Pattern(color.get(), patternType))
                                            }
                                        }
                                    })
                                    if (!patterns.isEmpty()) bmeta.patterns = patterns
                                }
                            } else if (meta is EnchantmentStorageMeta) {
                                val storedEnchantsElement = extraJson["stored-enchants"]
                                if (storedEnchantsElement != null && storedEnchantsElement.isJsonArray) {
                                    val jarray = storedEnchantsElement.asJsonArray
                                    jarray.forEach(Consumer { jsonElement: JsonElement ->
                                        if (jsonElement.isJsonPrimitive) {
                                            val enchantString = jsonElement.asString
                                            if (enchantString.contains(":")) {
                                                try {
                                                    val splitEnchant =
                                                        enchantString.split(":").toTypedArray()
                                                    val enchantment =
                                                        Enchantment.getByName(splitEnchant[0])
                                                    val level = splitEnchant[1].toInt()
                                                    if (enchantment != null && level > 0) {
                                                        meta.addStoredEnchant(enchantment, level, true)
                                                    }
                                                } catch (ex: NumberFormatException) {
                                                }
                                            }
                                        }
                                    })
                                }
                            } else if (meta is LeatherArmorMeta) {
                                val colorElement = extraJson["color"]
                                if (colorElement != null && colorElement.isJsonPrimitive) {
                                    try {
                                        meta.setColor(Color.fromRGB(colorElement.asString.toInt(16)))
                                    } catch (ex: NumberFormatException) {
                                    }
                                }
                            } else if (meta is BookMeta) {
                                val titleElement = extraJson["title"]
                                val authorElement = extraJson["author"]
                                val pagesElement = extraJson["pages"]
                                val bmeta = meta
                                if (titleElement != null && titleElement.isJsonPrimitive) {
                                    bmeta.title = titleElement.asString
                                }
                                if (authorElement != null && authorElement.isJsonPrimitive) {
                                    bmeta.author = authorElement.asString
                                }
                                if (pagesElement != null && pagesElement.isJsonArray) {
                                    val jarray = pagesElement.asJsonArray
                                    val pages: MutableList<String> = ArrayList(jarray.size())
                                    jarray.forEach(Consumer { jsonElement: JsonElement ->
                                        if (jsonElement.isJsonPrimitive) pages.add(
                                            jsonElement.asString
                                        )
                                    })
                                    bmeta.pages = pages
                                }
                            } else if (meta is PotionMeta) {
                                val customEffectsElement = extraJson["custom-effects"]
                                if (customEffectsElement != null && customEffectsElement.isJsonArray) {
                                    val jarray = customEffectsElement.asJsonArray
                                    jarray.forEach(Consumer { jsonElement: JsonElement ->
                                        if (jsonElement.isJsonPrimitive) {
                                            val enchantString = jsonElement.asString
                                            if (enchantString.contains(":")) {
                                                try {
                                                    val splitPotions =
                                                        enchantString.split(":").toTypedArray()
                                                    val potionType =
                                                        PotionEffectType.getByName(splitPotions[0])
                                                    val amplifier = splitPotions[1].toInt()
                                                    val duration = splitPotions[2].toInt() * 20
                                                    if (potionType != null) {
                                                        meta.addCustomEffect(
                                                            PotionEffect(
                                                                potionType,
                                                                amplifier,
                                                                duration
                                                            ), true
                                                        )
                                                    }
                                                } catch (ex: NumberFormatException) {
                                                }
                                            }
                                        }
                                    })
                                }
                            } else if (meta is FireworkEffectMeta) {
                                val effectTypeElement = extraJson["type"]
                                val flickerElement = extraJson["flicker"]
                                val trailElement = extraJson["trail"]
                                val colorsElement = extraJson["colors"]
                                val fadeColorsElement = extraJson["fade-colors"]
                                if (effectTypeElement != null && effectTypeElement.isJsonPrimitive) {
                                    val effectType = Type.valueOf(effectTypeElement.asString)
                                    if (effectType != null) {
                                        val colors: MutableList<Color?> = ArrayList()
                                        if (colorsElement != null && colorsElement.isJsonArray) colorsElement.asJsonArray.forEach(
                                            Consumer { colorElement: JsonElement ->
                                                if (colorElement.isJsonPrimitive) colors.add(
                                                    Color.fromRGB(colorElement.asString.toInt(16))
                                                )
                                            })
                                        val fadeColors: MutableList<Color?> = ArrayList()
                                        if (fadeColorsElement != null && fadeColorsElement.isJsonArray) fadeColorsElement.asJsonArray.forEach(
                                            Consumer { colorElement: JsonElement ->
                                                if (colorElement.isJsonPrimitive) fadeColors.add(
                                                    Color.fromRGB(colorElement.asString.toInt(16))
                                                )
                                            })
                                        val builder = FireworkEffect.builder().with(effectType)
                                        if (flickerElement != null && flickerElement.isJsonPrimitive) builder.flicker(
                                            flickerElement.asBoolean
                                        )
                                        if (trailElement != null && trailElement.isJsonPrimitive) builder.trail(
                                            trailElement.asBoolean
                                        )
                                        if (!colors.isEmpty()) builder.withColor(colors)
                                        if (!fadeColors.isEmpty()) builder.withFade(fadeColors)
                                        meta.effect = builder.build()
                                    }
                                }
                            } else if (meta is FireworkMeta) {
                                val fmeta = meta
                                val effectArrayElement = extraJson["effects"]
                                val powerElement = extraJson["power"]
                                if (powerElement != null && powerElement.isJsonPrimitive) {
                                    fmeta.power = powerElement.asInt
                                }
                                if (effectArrayElement != null && effectArrayElement.isJsonArray) {
                                    effectArrayElement.asJsonArray.forEach(Consumer { jsonElement: JsonElement ->
                                        if (jsonElement.isJsonObject) {
                                            val jsonObject = jsonElement.asJsonObject
                                            val effectTypeElement = jsonObject["type"]
                                            val flickerElement = jsonObject["flicker"]
                                            val trailElement = jsonObject["trail"]
                                            val colorsElement = jsonObject["colors"]
                                            val fadeColorsElement =
                                                jsonObject["fade-colors"]
                                            if (effectTypeElement != null && effectTypeElement.isJsonPrimitive) {
                                                val effectType =
                                                    Type.valueOf(effectTypeElement.asString)
                                                if (effectType != null) {
                                                    val colors: MutableList<Color?> =
                                                        ArrayList()
                                                    if (colorsElement != null && colorsElement.isJsonArray) colorsElement.asJsonArray
                                                        .forEach(
                                                            Consumer { colorElement: JsonElement ->
                                                                if (colorElement.isJsonPrimitive) colors.add(
                                                                    Color.fromRGB(
                                                                        colorElement.asString.toInt(16)
                                                                    )
                                                                )
                                                            })
                                                    val fadeColors: MutableList<Color?> =
                                                        ArrayList()
                                                    if (fadeColorsElement != null && fadeColorsElement.isJsonArray) fadeColorsElement.asJsonArray
                                                        .forEach(
                                                            Consumer { colorElement: JsonElement ->
                                                                if (colorElement.isJsonPrimitive) fadeColors.add(
                                                                    Color.fromRGB(
                                                                        colorElement.asString.toInt(16)
                                                                    )
                                                                )
                                                            })
                                                    val builder =
                                                        FireworkEffect.builder().with(effectType)
                                                    if (flickerElement != null && flickerElement.isJsonPrimitive) builder.flicker(
                                                        flickerElement.asBoolean
                                                    )
                                                    if (trailElement != null && trailElement.isJsonPrimitive) builder.trail(
                                                        trailElement.asBoolean
                                                    )
                                                    if (!colors.isEmpty()) builder.withColor(colors)
                                                    if (!fadeColors.isEmpty()) builder.withFade(fadeColors)
                                                    fmeta.addEffect(builder.build())
                                                }
                                            }
                                        }
                                    })
                                }
                            } else if (meta is MapMeta) {
                                val scalingElement = extraJson["scaling"]
                                if (scalingElement != null && scalingElement.isJsonPrimitive) {
                                    meta.isScaling = scalingElement.asBoolean
                                }

                                /* 1.11
JsonElement locationNameElement = extraJson.get("location-name");
if(locationNameElement != null && locationNameElement.isJsonPrimitive()) {
mmeta.setLocationName(locationNameElement.getAsString());
}

JsonElement colorElement = extraJson.get("color");
if(colorElement != null && colorElement.isJsonPrimitive()) {
mmeta.setColor(Color.fromRGB(Integer.parseInt(colorElement.getAsString(), 16)));
}*/
                            }
                        } catch (e: Exception) {
                            return null
                        }
                    }
                    itemStack.itemMeta = meta
                }
                itemStack
            } else null
        } else null
    }
}