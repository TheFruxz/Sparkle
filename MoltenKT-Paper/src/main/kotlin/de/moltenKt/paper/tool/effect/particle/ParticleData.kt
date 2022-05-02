package de.moltenKt.paper.tool.effect.particle

data class ParticleData<T : Any>(
    val type: ParticleType<T>,
    var data: T? = null,
) {

    fun data(data: T) {
        this.data = data
    }

}