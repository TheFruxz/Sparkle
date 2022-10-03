package de.fruxz.sparkle.framework.infrastructure.feature

import de.fruxz.ascend.tool.smart.identification.Identity
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.feature.Feature.FeatureState.DISABLED
import de.fruxz.sparkle.framework.infrastructure.feature.Feature.FeatureState.ENABLED
import de.fruxz.sparkle.framework.util.identification.VendorsIdentifiable

class Feature(override val vendorIdentity: Identity<out App>, name: String, description: String = "", version: String = "1.0") :
    VendorsIdentifiable<Feature> {

    class FeatureProperties internal constructor(
        identity: Identity<Feature>,
        name: String,
        description: String,
        version: String
    ) {

        var identity: Identity<Feature> = identity
            internal set

        var name: String = name
            internal set

        var description: String = description
            internal set

        var version: String = version
            internal set

    }

    enum class FeatureState {
        ENABLED,
        DISABLED;
    }

    val properties: FeatureProperties

    /**
     * Communicates directly with the cache
     */
    var isEnabled: Boolean
        get() = SparkleCache.featureStates[identityObject] == ENABLED
        set(value) {
            SparkleCache.featureStates += identityObject to if (value) ENABLED else DISABLED
        }

    fun registerIfNotRegistered(state: FeatureState = ENABLED) {
        if (!SparkleCache.featureStates.contains(identityObject)) {
            SparkleCache.featureStates += identityObject to state
        }
    }

    init {
        this.properties = FeatureProperties(
            identity = identityObject,
            name = name,
            description = description,
            version = version,
        )
    }

    override val thisIdentity = name

}