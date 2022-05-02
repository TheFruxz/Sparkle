@file:Suppress("MemberVisibilityCanBePrivate", "DEPRECATION")

package de.moltenKt.paper.tool.effect.particle

import org.bukkit.Particle
import org.bukkit.Vibration
import org.bukkit.block.data.BlockData
import org.bukkit.inventory.ItemStack
import org.bukkit.material.MaterialData

interface ParticleType<DATA : Any> {

    companion object {

        val EXPLOSION_NORMAL = object : ParticleType<Nothing> { }
        val EXPLOSION_LARGE = object : ParticleType<Nothing> { }
        val EXPLOSION_HUGE = object : ParticleType<Nothing> { }
        val FIREWORKS_SPARK = object : ParticleType<Nothing> { }
        val WATER_BUBBLE = object : ParticleType<Nothing> { }
        val WATER_SPLASH = object : ParticleType<Nothing> { }
        val WATER_WAKE = object : ParticleType<Nothing> { }
        val SUSPENDED = object : ParticleType<Nothing> { }
        val SUSPENDED_DEPTH = object : ParticleType<Nothing> { }
        val CRIT = object : ParticleType<Nothing> { }
        val CRIT_MAGIC = object : ParticleType<Nothing> { }
        val SMOKE_NORMAL = object : ParticleType<Nothing> { }
        val SMOKE_LARGE = object : ParticleType<Nothing> { }
        val SPELL = object : ParticleType<Nothing> { }
        val SPELL_INSTANT = object : ParticleType<Nothing> { }
        val SPELL_MOB = object : ParticleType<Nothing> { }
        val SPELL_MOB_AMBIENT = object : ParticleType<Nothing> { }
        val SPELL_WITCH = object : ParticleType<Nothing> { }
        val DRIP_WATER = object : ParticleType<Nothing> { }
        val DRIP_LAVA = object : ParticleType<Nothing> { }
        val VILLAGER_ANGRY = object : ParticleType<Nothing> { }
        val VILLAGER_HAPPY = object : ParticleType<Nothing> { }
        val TOWN_AURA = object : ParticleType<Nothing> { }
        val NOTE = object : ParticleType<Nothing> { }
        val PORTAL = object : ParticleType<Nothing> { }
        val ENCHANTMENT_TABLE = object : ParticleType<Nothing> { }
        val FLAME = object : ParticleType<Nothing> { }
        val LAVA = object : ParticleType<Nothing> { }
        val CLOUD = object : ParticleType<Nothing> { }
        val REDSTONE = object : ParticleType<Particle.DustOptions> { }
        val SNOWBALL = object : ParticleType<Nothing> { }
        val SNOW_SHOVEL = object : ParticleType<Nothing> { }
        val SLIME = object : ParticleType<Nothing> { }
        val HEART = object : ParticleType<Nothing> { }
        val ITEM_CRACK = object : ParticleType<ItemStack> { }
        val BLOCK_CRACK = object : ParticleType<BlockData> { }
        val BLOCK_DUST = object : ParticleType<BlockData> { }
        val WATER_DROP = object : ParticleType<Nothing> { }
        val MOB_APPEARANCE = object : ParticleType<Nothing> { }
        val DRAGON_BREATH = object : ParticleType<Nothing> { }
        val END_ROD = object : ParticleType<Nothing> { }
        val DAMAGE_INDICATOR = object : ParticleType<Nothing> { }
        val SWEEP_ATTACK = object : ParticleType<Nothing> { }
        val FALLING_DUST = object : ParticleType<BlockData> { }
        val TOTEM = object : ParticleType<Nothing> { }
        val SPIT = object : ParticleType<Nothing> { }
        val SQUID_INK = object : ParticleType<Nothing> { }
        val BUBBLE_POP = object : ParticleType<Nothing> { }
        val CURRENT_DOWN = object : ParticleType<Nothing> { }
        val BUBBLE_COLUMN_UP = object : ParticleType<Nothing> { }
        val NAUTILUS = object : ParticleType<Nothing> { }
        val DOLPHIN = object : ParticleType<Nothing> { }
        val SNEEZE = object : ParticleType<Nothing> { }
        val CAMPFIRE_COSY_SMOKE = object : ParticleType<Nothing> { }
        val CAMPFIRE_SIGNAL_SMOKE = object : ParticleType<Nothing> { }
        val COMPOSTER = object : ParticleType<Nothing> { }
        val FLASH = object : ParticleType<Nothing> { }
        val FALLING_LAVA = object : ParticleType<Nothing> { }
        val LANDING_LAVA = object : ParticleType<Nothing> { }
        val FALLING_WATER = object : ParticleType<Nothing> { }
        val DRIPPING_HONEY = object : ParticleType<Nothing> { }
        val FALLING_HONEY = object : ParticleType<Nothing> { }
        val LANDING_HONEY = object : ParticleType<Nothing> { }
        val FALLING_NECTAR = object : ParticleType<Nothing> { }
        val SOUL_FIRE_FLAME = object : ParticleType<Nothing> { }
        val ASH = object : ParticleType<Nothing> { }
        val CRIMSON_SPORE = object : ParticleType<Nothing> { }
        val WARPED_SPORE = object : ParticleType<Nothing> { }
        val SOUL = object : ParticleType<Nothing> { }
        val DRIPPING_OBSIDIAN_TEAR = object : ParticleType<Nothing> { }
        val FALLING_OBSIDIAN_TEAR = object : ParticleType<Nothing> { }
        val LANDING_OBSIDIAN_TEAR = object : ParticleType<Nothing> { }
        val REVERSE_PORTAL = object : ParticleType<Nothing> { }
        val WHITE_ASH = object : ParticleType<Nothing> { }
        val DUST_COLOR_TRANSITION = object : ParticleType<Particle.DustTransition> { }
        val VIBRATION = object : ParticleType<Vibration> { }
        val FALLING_SPORE_BLOSSOM = object : ParticleType<Nothing> { }
        val SPORE_BLOSSOM_AIR = object : ParticleType<Nothing> { }
        val SMALL_FLAME = object : ParticleType<Nothing> { }
        val SNOWFLAKE = object : ParticleType<Nothing> { }
        val DRIPPING_DRIPSTONE_LAVA = object : ParticleType<Nothing> { }
        val FALLING_DRIPSTONE_LAVA = object : ParticleType<Nothing> { }
        val DRIPPING_DRIPSTONE_WATER = object : ParticleType<Nothing> { }
        val FALLING_DRIPSTONE_WATER = object : ParticleType<Nothing> { }
        val GLOW_SQUID_INK = object : ParticleType<Nothing> { }
        val GLOW = object : ParticleType<Nothing> { }
        val WAX_ON = object : ParticleType<Nothing> { }
        val WAX_OFF = object : ParticleType<Nothing> { }
        val ELECTRIC_SPARK = object : ParticleType<Nothing> { }
        val SCRAPE = object : ParticleType<Nothing> { }
        val BLOCK_MARKER = object : ParticleType<BlockData> { }
        val LEGACY_BLOCK_CRACK = object : ParticleType<MaterialData> { }
        val LEGACY_BLOCK_DUST = object : ParticleType<MaterialData> { }
        val LEGACY_FALLING_DUST = object : ParticleType<MaterialData> { }

        fun fromLegacy(particle: Particle): ParticleType<*> = when (particle) {
            Particle.EXPLOSION_NORMAL -> EXPLOSION_NORMAL
            Particle.EXPLOSION_LARGE -> EXPLOSION_LARGE
            Particle.EXPLOSION_HUGE -> EXPLOSION_HUGE
            Particle.FIREWORKS_SPARK -> FIREWORKS_SPARK
            Particle.WATER_BUBBLE -> WATER_BUBBLE
            Particle.WATER_SPLASH -> WATER_SPLASH
            Particle.WATER_WAKE -> WATER_WAKE
            Particle.SUSPENDED -> SUSPENDED
            Particle.SUSPENDED_DEPTH -> SUSPENDED_DEPTH
            Particle.CRIT -> CRIT
            Particle.CRIT_MAGIC -> CRIT_MAGIC
            Particle.SMOKE_NORMAL -> SMOKE_NORMAL
            Particle.SMOKE_LARGE -> SMOKE_LARGE
            Particle.SPELL -> SPELL
            Particle.SPELL_INSTANT -> SPELL_INSTANT
            Particle.SPELL_MOB -> SPELL_MOB
            Particle.SPELL_MOB_AMBIENT -> SPELL_MOB_AMBIENT
            Particle.SPELL_WITCH -> SPELL_WITCH
            Particle.DRIP_WATER -> DRIP_WATER
            Particle.DRIP_LAVA -> DRIP_LAVA
            Particle.VILLAGER_ANGRY -> VILLAGER_ANGRY
            Particle.VILLAGER_HAPPY -> VILLAGER_HAPPY
            Particle.TOWN_AURA -> TOWN_AURA
            Particle.NOTE -> NOTE
            Particle.PORTAL -> PORTAL
            Particle.ENCHANTMENT_TABLE -> ENCHANTMENT_TABLE
            Particle.FLAME -> FLAME
            Particle.LAVA -> LAVA
            Particle.CLOUD -> CLOUD
            Particle.REDSTONE -> REDSTONE
            Particle.SNOWBALL -> SNOWBALL
            Particle.SNOW_SHOVEL -> SNOW_SHOVEL
            Particle.SLIME -> SLIME
            Particle.HEART -> HEART
            Particle.ITEM_CRACK -> ITEM_CRACK
            Particle.BLOCK_CRACK -> BLOCK_CRACK
            Particle.BLOCK_DUST -> BLOCK_DUST
            Particle.WATER_DROP -> WATER_DROP
            Particle.MOB_APPEARANCE -> MOB_APPEARANCE
            Particle.DRAGON_BREATH -> DRAGON_BREATH
            Particle.END_ROD -> END_ROD
            Particle.DAMAGE_INDICATOR -> DAMAGE_INDICATOR
            Particle.SWEEP_ATTACK -> SWEEP_ATTACK
            Particle.FALLING_DUST -> FALLING_DUST
            Particle.TOTEM -> TOTEM
            Particle.SPIT -> SPIT
            Particle.SQUID_INK -> SQUID_INK
            Particle.BUBBLE_POP -> BUBBLE_POP
            Particle.CURRENT_DOWN -> CURRENT_DOWN
            Particle.BUBBLE_COLUMN_UP -> BUBBLE_COLUMN_UP
            Particle.NAUTILUS -> NAUTILUS
            Particle.DOLPHIN -> DOLPHIN
            Particle.SNEEZE -> SNEEZE
            Particle.CAMPFIRE_COSY_SMOKE -> CAMPFIRE_COSY_SMOKE
            Particle.CAMPFIRE_SIGNAL_SMOKE -> CAMPFIRE_SIGNAL_SMOKE
            Particle.COMPOSTER -> COMPOSTER
            Particle.FLASH -> FLASH
            Particle.FALLING_LAVA -> FALLING_LAVA
            Particle.LANDING_LAVA -> LANDING_LAVA
            Particle.FALLING_WATER -> FALLING_WATER
            Particle.DRIPPING_HONEY -> DRIPPING_HONEY
            Particle.FALLING_HONEY -> FALLING_HONEY
            Particle.LANDING_HONEY -> LANDING_HONEY
            Particle.FALLING_NECTAR -> FALLING_NECTAR
            Particle.SOUL_FIRE_FLAME -> SOUL_FIRE_FLAME
            Particle.ASH -> ASH
            Particle.CRIMSON_SPORE -> CRIMSON_SPORE
            Particle.WARPED_SPORE -> WARPED_SPORE
            Particle.SOUL -> SOUL
            Particle.DRIPPING_OBSIDIAN_TEAR -> DRIPPING_OBSIDIAN_TEAR
            Particle.FALLING_OBSIDIAN_TEAR -> FALLING_OBSIDIAN_TEAR
            Particle.LANDING_OBSIDIAN_TEAR -> LANDING_OBSIDIAN_TEAR
            Particle.REVERSE_PORTAL -> REVERSE_PORTAL
            Particle.WHITE_ASH -> WHITE_ASH
            Particle.DUST_COLOR_TRANSITION -> DUST_COLOR_TRANSITION
            Particle.VIBRATION -> VIBRATION
            Particle.FALLING_SPORE_BLOSSOM -> FALLING_SPORE_BLOSSOM
            Particle.SPORE_BLOSSOM_AIR -> SPORE_BLOSSOM_AIR
            Particle.SMALL_FLAME -> SMALL_FLAME
            Particle.SNOWFLAKE -> SNOWFLAKE
            Particle.DRIPPING_DRIPSTONE_LAVA -> DRIPPING_DRIPSTONE_LAVA
            Particle.FALLING_DRIPSTONE_LAVA -> FALLING_DRIPSTONE_LAVA
            Particle.DRIPPING_DRIPSTONE_WATER -> DRIPPING_DRIPSTONE_WATER
            Particle.FALLING_DRIPSTONE_WATER -> FALLING_DRIPSTONE_WATER
            Particle.GLOW_SQUID_INK -> GLOW_SQUID_INK
            Particle.GLOW -> GLOW
            Particle.WAX_ON -> WAX_ON
            Particle.WAX_OFF -> WAX_OFF
            Particle.ELECTRIC_SPARK -> ELECTRIC_SPARK
            Particle.SCRAPE -> SCRAPE
            Particle.BLOCK_MARKER -> BLOCK_MARKER
            Particle.LEGACY_BLOCK_CRACK -> LEGACY_BLOCK_CRACK
            Particle.LEGACY_BLOCK_DUST -> LEGACY_BLOCK_DUST
            Particle.LEGACY_FALLING_DUST -> LEGACY_FALLING_DUST
        }

    }

}