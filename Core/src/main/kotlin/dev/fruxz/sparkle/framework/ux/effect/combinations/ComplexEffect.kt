package dev.fruxz.sparkle.framework.ux.effect.combinations

import dev.fruxz.sparkle.framework.ux.effect.EffectDsl
import org.bukkit.Location
import org.bukkit.entity.Entity

data class ComplexEffect(
    private val _effects: MutableList<CrossBasedEffect>,
) : CrossBasedEffect {

    val effects: List<CrossBasedEffect>
        get() = _effects.toList()

    constructor(builder: ComplexEffect.() -> Unit) : this(ComplexEffect(mutableListOf()).apply(builder)._effects)

    override fun play(locationSet: Set<Location>, entitySet: Set<Entity>) = _effects.forEach { it.play(locationSet, entitySet) }

    override fun play(vararg entities: Entity?) = _effects.forEach { it.play(*entities) }

    override fun play(vararg locations: Location?) = _effects.forEach { it.play(*locations) }

    @EffectDsl
    fun effect(effect: CrossBasedEffect) = _effects.add(effect)

    @EffectDsl
    fun merge(complexEffect: ComplexEffect) = _effects.addAll(complexEffect.effects)

}