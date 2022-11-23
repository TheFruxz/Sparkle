package de.fruxz.sparkle.framework.infrastructure.command.completion.content

import de.fruxz.ascend.tool.smart.composition.Producible
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset.CompletionContext
import net.kyori.adventure.key.Key

data class CompletionAssetBuilder<T>(
	var key: Key,
	var dynamic: Boolean = false,
	var validation: (CompletionContext.() -> Boolean)? = null,
	var translation: (CompletionContext.() -> T?)? = null,
	var completion: (CompletionContext.(CompletionAsset<T>) -> Collection<String>)? = null,
) : Producible<CompletionAsset<T>>{

	@AssetDsl fun dynamicAsset() = apply { dynamic = true }
	@AssetDsl fun staticAsset() = apply { dynamic = false }
	@AssetDsl fun validate(validation: CompletionContext.() -> Boolean) = apply { this.validation = validation }
	@AssetDsl fun translate(translation: CompletionContext.() -> T?) = apply { this.translation = translation }
	@AssetDsl fun complete(completion: CompletionContext.(CompletionAsset<T>) -> Collection<String>) = apply { this.completion = completion }

	override fun produce(): CompletionAsset<T> {
		assert(completion != null) { "Completion must not be null!" }
		return CompletionAsset(
			identityKey = key,
			refreshing = dynamic,
			check = validation,
			transformer = translation,
			generator = completion!!,
		)
	}

	companion object {

		fun <T> buildAsset(key: Key, builder: CompletionAssetBuilder<T>.() -> Unit) =
			CompletionAssetBuilder<T>(key).apply(builder).produce()

	}

	@DslMarker
	annotation class AssetDsl

}