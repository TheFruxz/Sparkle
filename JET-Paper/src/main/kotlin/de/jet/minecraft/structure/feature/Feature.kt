package de.jet.minecraft.structure.feature

import de.jet.jvm.tool.smart.identification.Identity
import de.jet.minecraft.app.JetCache
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.tool.smart.VendorsIdentifiable

class Feature(override val vendorIdentity: Identity<out App>, name: String, description: String = "", version: String = "1.0") : VendorsIdentifiable<Feature> {

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
        get() = JetCache.featureStates[identityObject] == FeatureState.ENABLED
        set(value) {
            JetCache.featureStates[identityObject] = if (value) FeatureState.ENABLED else FeatureState.DISABLED
        }

    fun registerIfNotRegistered(state: FeatureState = FeatureState.ENABLED) {
        if (!JetCache.featureStates.contains(identityObject)) {
            JetCache.featureStates[identityObject] = state
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