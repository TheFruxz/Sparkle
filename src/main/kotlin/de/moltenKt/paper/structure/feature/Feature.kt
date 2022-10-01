package de.moltenKt.paper.structure.feature

import de.fruxz.ascend.tool.smart.identification.Identity
import de.moltenKt.paper.app.MoltenCache
import de.moltenKt.paper.structure.app.App
import de.moltenKt.paper.tool.smart.VendorsIdentifiable

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
        get() = MoltenCache.featureStates[identityObject] == FeatureState.ENABLED
        set(value) {
            MoltenCache.featureStates += identityObject to if (value) FeatureState.ENABLED else FeatureState.DISABLED
        }

    fun registerIfNotRegistered(state: FeatureState = FeatureState.ENABLED) {
        if (!MoltenCache.featureStates.contains(identityObject)) {
            MoltenCache.featureStates += identityObject to state
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