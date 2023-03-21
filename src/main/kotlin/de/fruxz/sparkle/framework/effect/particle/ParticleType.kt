@file:Suppress("MemberVisibilityCanBePrivate", "DEPRECATION", "UnstableApiUsage")

package de.fruxz.sparkle.framework.effect.particle

import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.Particle.DustTransition
import org.bukkit.Vibration
import org.bukkit.block.data.BlockData
import org.bukkit.inventory.ItemStack
import org.bukkit.material.MaterialData
import org.jetbrains.annotations.ApiStatus

interface ParticleType<DATA : Any> : Keyed {

    val type: Particle

    override fun key() = Key.key(type.name)

    companion object {

        fun <T : Any> particle(particle: Particle) = object : ParticleType<T> {
            override val type = particle
        }

        fun values(): Set<ParticleType<*>> = Particle.values().map { minecraft(it) }.toSet()

        val EXPLOSION_NORMAL = particle<Nothing>(Particle.EXPLOSION_NORMAL)
        val EXPLOSION_LARGE = particle<Nothing>(Particle.EXPLOSION_NORMAL)
        val EXPLOSION_HUGE = particle<Nothing>(Particle.EXPLOSION_HUGE)
        val FIREWORKS_SPARK = particle<Nothing>(Particle.FIREWORKS_SPARK)
        val WATER_BUBBLE = particle<Nothing>(Particle.WATER_BUBBLE)
        val WATER_SPLASH = particle<Nothing>(Particle.WATER_SPLASH)
        val WATER_WAKE = particle<Nothing>(Particle.WATER_WAKE)
        val SUSPENDED = particle<Nothing>(Particle.SUSPENDED)
        val SUSPENDED_DEPTH = particle<Nothing>(Particle.SUSPENDED_DEPTH)
        val CRIT = particle<Nothing>(Particle.CRIT)
        val CRIT_MAGIC = particle<Nothing>(Particle.CRIT_MAGIC)
        val SMOKE_NORMAL = particle<Nothing>(Particle.SMOKE_NORMAL)
        val SMOKE_LARGE = particle<Nothing>(Particle.SMOKE_LARGE)
        val SPELL = particle<Nothing>(Particle.SPELL)
        val SPELL_INSTANT = particle<Nothing>(Particle.SPELL_INSTANT)
        val SPELL_MOB = particle<Nothing>(Particle.SPELL_MOB)
        val SPELL_MOB_AMBIENT = particle<Nothing>(Particle.SPELL_MOB_AMBIENT)
        val SPELL_WITCH = particle<Nothing>(Particle.SPELL_WITCH)
        val DRIP_WATER = particle<Nothing>(Particle.DRIP_WATER)
        val DRIP_LAVA = particle<Nothing>(Particle.DRIP_LAVA)
        val VILLAGER_ANGRY = particle<Nothing>(Particle.VILLAGER_ANGRY)
        val VILLAGER_HAPPY = particle<Nothing>(Particle.VILLAGER_HAPPY)
        val TOWN_AURA = particle<Nothing>(Particle.TOWN_AURA)
        val NOTE = particle<Nothing>(Particle.NOTE)
        val PORTAL = particle<Nothing>(Particle.PORTAL)
        val ENCHANTMENT_TABLE = particle<Nothing>(Particle.ENCHANTMENT_TABLE)
        val FLAME = particle<Nothing>(Particle.FLAME)
        val LAVA = particle<Nothing>(Particle.LAVA)
        val CLOUD = particle<Nothing>(Particle.CLOUD)
        val REDSTONE = particle<DustOptions>(Particle.REDSTONE)
        val SNOWBALL = particle<Nothing>(Particle.SNOWBALL)
        val SNOW_SHOVEL = particle<Nothing>(Particle.SNOW_SHOVEL)
        val SLIME = particle<Nothing>(Particle.SLIME)
        val HEART = particle<Nothing>(Particle.HEART)
        val ITEM_CRACK = particle<ItemStack>(Particle.ITEM_CRACK)
        val BLOCK_CRACK = particle<BlockData>(Particle.BLOCK_CRACK)
        val BLOCK_DUST = particle<BlockData>(Particle.BLOCK_DUST)
        val WATER_DROP = particle<Nothing>(Particle.WATER_DROP)
        val MOB_APPEARANCE = particle<Nothing>(Particle.MOB_APPEARANCE)
        val DRAGON_BREATH = particle<Nothing>(Particle.DRAGON_BREATH)
        val END_ROD = particle<Nothing>(Particle.END_ROD)
        val DAMAGE_INDICATOR = particle<Nothing>(Particle.DAMAGE_INDICATOR)
        val SWEEP_ATTACK = particle<Nothing>(Particle.SWEEP_ATTACK)
        val FALLING_DUST = particle<BlockData>(Particle.FALLING_DUST)
        val TOTEM = particle<Nothing>(Particle.TOTEM)
        val SPIT = particle<Nothing>(Particle.SPIT)
        val SQUID_INK = particle<Nothing>(Particle.SQUID_INK)
        val BUBBLE_POP = particle<Nothing>(Particle.BUBBLE_POP)
        val CURRENT_DOWN = particle<Nothing>(Particle.CURRENT_DOWN)
        val BUBBLE_COLUMN_UP = particle<Nothing>(Particle.BUBBLE_COLUMN_UP)
        val NAUTILUS = particle<Nothing>(Particle.NAUTILUS)
        val DOLPHIN = particle<Nothing>(Particle.DOLPHIN)
        val SNEEZE = particle<Nothing>(Particle.SNEEZE)
        val CAMPFIRE_COSY_SMOKE = particle<Nothing>(Particle.CAMPFIRE_COSY_SMOKE)
        val CAMPFIRE_SIGNAL_SMOKE = particle<Nothing>(Particle.CAMPFIRE_SIGNAL_SMOKE)
        val COMPOSTER = particle<Nothing>(Particle.COMPOSTER)
        val FLASH = particle<Nothing>(Particle.FLASH)
        val FALLING_LAVA = particle<Nothing>(Particle.FALLING_LAVA)
        val LANDING_LAVA = particle<Nothing>(Particle.LANDING_LAVA)
        val FALLING_WATER = particle<Nothing>(Particle.FALLING_WATER)
        val DRIPPING_HONEY = particle<Nothing>(Particle.DRIPPING_HONEY)
        val FALLING_HONEY = particle<Nothing>(Particle.FALLING_HONEY)
        val LANDING_HONEY = particle<Nothing>(Particle.LANDING_HONEY)
        val FALLING_NECTAR = particle<Nothing>(Particle.FALLING_NECTAR)
        val SOUL_FIRE_FLAME = particle<Nothing>(Particle.SOUL_FIRE_FLAME)
        val ASH = particle<Nothing>(Particle.ASH)
        val CRIMSON_SPORE = particle<Nothing>(Particle.CRIMSON_SPORE)
        val WARPED_SPORE = particle<Nothing>(Particle.WARPED_SPORE)
        val SOUL = particle<Nothing>(Particle.SOUL)
        val DRIPPING_OBSIDIAN_TEAR = particle<Nothing>(Particle.DRIPPING_OBSIDIAN_TEAR)
        val FALLING_OBSIDIAN_TEAR = particle<Nothing>(Particle.FALLING_OBSIDIAN_TEAR)
        val LANDING_OBSIDIAN_TEAR = particle<Nothing>(Particle.LANDING_OBSIDIAN_TEAR)
        val REVERSE_PORTAL = particle<Nothing>(Particle.REVERSE_PORTAL)
        val WHITE_ASH = particle<Nothing>(Particle.WHITE_ASH)
        val DUST_COLOR_TRANSITION = particle<DustTransition>(Particle.DUST_COLOR_TRANSITION)
        val VIBRATION = particle<Vibration>(Particle.VIBRATION)
        val FALLING_SPORE_BLOSSOM = particle<Nothing>(Particle.FALLING_SPORE_BLOSSOM)
        val SPORE_BLOSSOM_AIR = particle<Nothing>(Particle.SPORE_BLOSSOM_AIR)
        val SMALL_FLAME = particle<Nothing>(Particle.SMALL_FLAME)
        val SNOWFLAKE = particle<Nothing>(Particle.SNOWFLAKE)
        val DRIPPING_DRIPSTONE_LAVA = particle<Nothing>(Particle.DRIPPING_DRIPSTONE_LAVA)
        val FALLING_DRIPSTONE_LAVA = particle<Nothing>(Particle.FALLING_DRIPSTONE_LAVA)
        val DRIPPING_DRIPSTONE_WATER = particle<Nothing>(Particle.DRIPPING_DRIPSTONE_WATER)
        val FALLING_DRIPSTONE_WATER = particle<Nothing>(Particle.FALLING_DRIPSTONE_WATER)
        val GLOW_SQUID_INK = particle<Nothing>(Particle.GLOW_SQUID_INK)
        val GLOW = particle<Nothing>(Particle.GLOW)
        val WAX_ON = particle<Nothing>(Particle.WAX_ON)
        val WAX_OFF = particle<Nothing>(Particle.WAX_OFF)
        val ELECTRIC_SPARK = particle<Nothing>(Particle.ELECTRIC_SPARK)
        val SCRAPE = particle<Nothing>(Particle.SCRAPE)
        val BLOCK_MARKER = particle<BlockData>(Particle.BLOCK_MARKER)
        val LEGACY_BLOCK_CRACK = particle<MaterialData>(Particle.LEGACY_BLOCK_CRACK)
        val LEGACY_BLOCK_DUST = particle<MaterialData>(Particle.LEGACY_BLOCK_DUST)
        val LEGACY_FALLING_DUST = particle<MaterialData>(Particle.LEGACY_FALLING_DUST)
        val SONIC_BOOM = particle<Nothing>(Particle.SONIC_BOOM)
        val SCULK_SOUL = particle<Nothing>(Particle.SCULK_SOUL)
        val SCULK_CHARGE = particle<Float>(Particle.SCULK_CHARGE)
        val SCULK_CHARGE_POP = particle<Nothing>(Particle.SCULK_CHARGE_POP)
        val SHRIEK = particle<Int>(Particle.SHRIEK)
        @ApiStatus.Experimental val DRIPPING_CHERRY_LEAVES = particle<Nothing>(Particle.DRIPPING_CHERRY_LEAVES)
        @ApiStatus.Experimental val FALLING_CHERRY_LEAVES = particle<Nothing>(Particle.FALLING_CHERRY_LEAVES)
        @ApiStatus.Experimental val LANDING_CHERRY_LEAVES = particle<Nothing>(Particle.LANDING_CHERRY_LEAVES)

        fun minecraft(particle: Particle): ParticleType<*> = when (particle) {
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
            Particle.SONIC_BOOM -> SONIC_BOOM
            Particle.SCULK_SOUL -> SCULK_SOUL
            Particle.SCULK_CHARGE -> SCULK_CHARGE
            Particle.SCULK_CHARGE_POP -> SCULK_CHARGE_POP
            Particle.SHRIEK -> SHRIEK
            Particle.DRIPPING_CHERRY_LEAVES -> DRIPPING_CHERRY_LEAVES
            Particle.FALLING_CHERRY_LEAVES -> FALLING_CHERRY_LEAVES
            Particle.LANDING_CHERRY_LEAVES -> LANDING_CHERRY_LEAVES
        }

    }

}