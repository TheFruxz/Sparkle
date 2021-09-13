package de.jet.minecraft.structure.command

import de.jet.library.extension.collection.mapToString
import de.jet.library.extension.collection.replace
import de.jet.library.extension.collection.withMap
import de.jet.library.extension.data.isDouble
import de.jet.library.extension.data.isInt
import de.jet.library.extension.modifiedIf
import de.jet.library.extension.paper.getPlayer
import de.jet.library.extension.paper.name
import de.jet.library.extension.paper.onlinePlayers
import de.jet.library.extension.paper.worlds
import de.jet.library.tool.smart.Identifiable
import de.jet.minecraft.app.JetCache
import de.jet.minecraft.extension.display.notification
import de.jet.minecraft.extension.lang
import de.jet.minecraft.extension.paper.hasApproval
import de.jet.minecraft.extension.system
import de.jet.minecraft.structure.app.cache.CacheDepthLevel
import de.jet.minecraft.tool.display.color.ColorType
import de.jet.minecraft.tool.display.color.DyeableMaterial
import de.jet.minecraft.tool.display.message.Transmission.Level.FAIL
import de.jet.minecraft.tool.permission.Approval
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.EntityType
import java.util.*

// Variables

data class CompletionVariable(
	val vendor: Identifiable<*>,
	val label: String,
	val refreshing: Boolean,
	var check: (input: String, ignoreCase: Boolean) -> Boolean = { _, _ -> true },
	val generator: CompletionVariable.() -> Collection<String>,
) {

	val storagePath = "${vendor.identity}:$label"

	fun computeContent() =
		if (!refreshing && JetCache.registeredCompletionVariables.containsKey(storagePath)) {
			JetCache.registeredCompletionVariables[storagePath]!!
		} else {
			generator(this).toSet().apply {

				if (!refreshing)
					JetCache.registeredCompletionVariables[storagePath] = this

			}
		}

	fun checker(check: (input: String, ignoreCase: Boolean) -> Boolean) = apply {
		this.check = check
	}

	companion object {

		val INT = CompletionVariable(system, "INT", false) {
			(0..99).mapToString()
		}.checker { input, _ ->
			return@checker input.isInt()
		}

		val DOUBLE = CompletionVariable(system, "DOUBLE", false) {
			setOf(.0, .1, .2, .3, .4, .5, .6, .7, .8, .9, 1.0).mapToString()
		}.checker { input, _ ->
			return@checker input.isDouble()
		}

		val PLAYER_NAME = CompletionVariable(system, "PLAYER-NAME", true) {
			onlinePlayers.withMap { name }
		}.checker { input, _ ->
			return@checker getPlayer(input) != null
		}

		val PLAYER_IDENTITY = CompletionVariable(system, "PLAYER-ID", true) {
			onlinePlayers.withMap { "$uniqueId" }
		}.checker { input, _ ->
			return@checker try {
				getPlayer(UUID.fromString(input)) != null
			} catch (e: IllegalArgumentException) {
				false
			}
		}

		val MATERIAL = CompletionVariable(system, "MATERIAL", false) {
			Material.values().withMap { name }
		}.checker { input, _ ->
			return@checker Material.matchMaterial(input) != null
		}

		val MATERIAL_VARIANT = CompletionVariable(system, "MATERIAL-VARIANT", false) {
			buildSet {
				DyeableMaterial.values().forEach { flex ->
					val key = flex.key.toString()
					add(key)
					addAll(ColorType.values().withMap { "$key#$name" })
				}
			}
		}.checker { input, _ ->
			return@checker DyeableMaterial.materialFromMaterialCode(input) != null
		}

		val MATERIAL_CODE = CompletionVariable(system, "MATERIAL-CODE", false) {
			buildSet {

				addAll(Material.values().withMap { "minecraft:$name" })

				DyeableMaterial.values().forEach { flex ->
					val key = flex.key.toString()
					add("jet:$key")
					addAll(ColorType.values().withMap { "jet:$key#$name" })
				}

			}
		}.checker { input, _ ->
			return@checker DyeableMaterial.materialFromMaterialCode(input) != null
		}

		val ENTITY_TYPE = CompletionVariable(system, "ENTITY-TYPE", false) {
			EntityType.values().withMap { name }
		}.checker { input, ignoreCase ->
			return@checker EntityType.values().any { name == input.modifiedIf(ignoreCase) { uppercase() } }
		}

		val WORLD_NAME = CompletionVariable(system, "WORLD-NAME", true) {
			worlds.withMap { name }
		}.checker { input, ignoreCase ->
			return@checker worlds.any { it.name.equals(input, ignoreCase) }
		}

		val APP = CompletionVariable(system, "APP", true) {
			JetCache.registeredApplications.withMap { identity }
		}.checker { input, ignoreCase ->
			return@checker JetCache.registeredApplications.any { it.identity.equals(input, ignoreCase) }
		}

		val INTERCHANGE = CompletionVariable(system, "INTERCHANGE", true) {
			JetCache.registeredInterchanges.withMap { identity }
		}.checker { input, ignoreCase ->
			return@checker JetCache.registeredInterchanges.any { it.identity.equals(input, ignoreCase) }
		}

		val SERVICE = CompletionVariable(system, "SERVICE", true) {
			JetCache.registeredServices.withMap { "$key" }
		}.checker { input, ignoreCase ->
			return@checker JetCache.registeredServices.any { "${it.key}".equals(input, ignoreCase) }
		}

		val SANDBOX = CompletionVariable(system, "SANDBOX", true) {
			JetCache.registeredSandBoxes.withMap { identity }
		}.checker { input, ignoreCase ->
			return@checker JetCache.registeredSandBoxes.any { it.identity.equals(input, ignoreCase) }
		}

		val CACHE_DEPTH_LEVEL = CompletionVariable(system, "CACHE-DEPTH-LEVEL", false) {
			CacheDepthLevel.values().withMap { name }
		}.checker { input, ignoreCase ->
			return@checker CacheDepthLevel.values().any { it.name.equals(input, ignoreCase) }
		}

	}

}

// Components

sealed interface CompletionComponent {

	fun completion(): Set<String>

	val label: String

	val displayRequirement: ((executor: CommandSender, parameters: Array<String>, completion: Set<String>) -> Boolean)?

	val inputExpressionCheck: ((input: String, ignoreCase: Boolean) -> Boolean)

	val accessApproval: Approval?

}

data class StaticCompletionComponent(
	val completion: Set<String>,
	override val displayRequirement: ((executor: CommandSender, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
	override val accessApproval: Approval? = null,
) : CompletionComponent {

	override fun completion() = completion

	override val label = "[${completion.joinToString("/")}]"

	override val inputExpressionCheck: (String, Boolean) -> Boolean = { input, ignoreCase ->
			completion.none { it.equals(input, ignoreCase) }
	}

}

data class VariableCompletionComponent(
	val variable: CompletionVariable,
	override val displayRequirement: ((executor: CommandSender, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
	override val accessApproval: Approval? = null,
) : CompletionComponent {

	override fun completion() = variable.computeContent()

	override val label = "<${variable.storagePath}>"

	override val inputExpressionCheck: (String, Boolean) -> Boolean = check@{ input, ignoreCase ->
		return@check variable.check(input, ignoreCase)
	}

}

// Component-Sections

data class CompletionComponentSection(
	val components: MutableList<CompletionComponent>,
	var label: String? = null,
	var isRequired: Boolean = true,
	var mustMatchOutput: Boolean = true,
) {

	val inputExpressionCheck: (String) -> Boolean = check@{ input ->
		return@check components.any { it.inputExpressionCheck(input, mustMatchOutput) }
	}

}

// Completion-Structure

data class Completion(
	var sections: MutableList<CompletionComponentSection>,
	var infinite: Boolean
) {

	val currentSlot: Int
		get() = sections.indices.last

	val nextSlot: Int
		get() = sections.size

	fun current(apply: CompletionComponentSection.() -> Unit) : Completion {
		sections[currentSlot] = sections[currentSlot].apply(apply)
		return this
	}

	internal fun buildCompletion() = TabCompleter { executor, _, _, parameters ->
		val layer = parameters.lastIndex
		val output = mutableListOf<String>()

		if (sections.lastIndex >= layer) {
			val currentSection = sections[layer]

			currentSection.components.forEach { component ->
				val accessApproval = component.accessApproval
				val displayRequirement = component.displayRequirement
				if (accessApproval == null || executor.hasApproval(accessApproval)) {
					if (displayRequirement == null || displayRequirement(executor, parameters, output.toSet())) {
						output.addAll(component.completion())
					}
				}
			}

			return@TabCompleter output.let { out -> if (out.isEmpty()) listOf(" ") else out }.toMutableList().let { completion ->
				val first = buildList {
					completion.forEach {
						if (it.startsWith(parameters.last(), false)) {
							add(it)
						}
					}
				}.sorted()

				val second = buildList {
					completion
						.filter { !first.contains(it) }
						.forEach {
							if (it.startsWith(parameters.last(), true)) {
								add(it)
							}
						}
				}.sorted()

				val third = buildList {
					completion
						.filter { !second.contains(it) && !second.contains(it) }
						.forEach {
							if (it.contains(parameters.last(), true)) {
								add(it)
							}
						}
				}

				return@let first + second + third
			}

		} else
			return@TabCompleter listOf(" ")

	}

	internal fun buildDisplay() = buildString {

		sections.forEach {
			val multiComponent = it.components.size > 1
			val display = if (it.label != null) {
				"<${it.label}>"
			} else
				buildString {
					append("{")
					it.components.forEach { internalComponent ->

						append(internalComponent.label)

						if (multiComponent)
							append("/")

					}
					if (multiComponent)
						removeSuffix("/")
					append("}")
				}

			if (!it.isRequired)
				append("(")

			if (it.mustMatchOutput)
				append("^")

			append(display)

			if (!it.isRequired)
				append(")")

			append(" ")

		}

		removeSuffix(" ")

	}

	internal fun buildCheck(): (executor: CommandSender, interchange: Interchange, parameters: List<String>) -> Boolean = check@{ executor, _, parameters ->
		val minimumParameters = sections.indexOfLast { it.isRequired }
		val maximumParameters = if (infinite) -1 else sections.lastIndex

		if (parameters.lastIndex >= minimumParameters) {

			if (maximumParameters == -1 || parameters.lastIndex <= maximumParameters) {

				this.sections.withIndex().forEach { (index, value) ->

					if (index <= parameters.size) {

						if (value.isRequired || (!value.isRequired && parameters.lastIndex >= index)) {

							if (value.mustMatchOutput && !value.components.flatMap { it.completion() }
									.contains(parameters[index])) {

								lang("interchange.run.check.noMatch")
									.replace(
										"[input]" to parameters[index],
										"[usage]" to (value.components.joinToString("; ") {
											it.completion().joinToString("/")
										}).let { out ->
											if (out.length <= 40) {
												out
											} else
												"${out.take(40).dropLast(3)}..."
										},
									)
									.notification(FAIL, executor)
									.display()

								return@check false

							}

						}

					}

				}

				return@check true

			} else {
				lang("interchange.run.check.tooLarge")
					.replace("[maximumParameters]" to maximumParameters+1)
					.notification(FAIL, executor)
					.display()
				return@check false
			}

		} else {
			lang("interchange.run.check.tooShort")
				.replace("[minimumParameters]" to minimumParameters+1)
				.notification(FAIL, executor)
				.display()
			return@check false
		}

	}

}

// Next-Section blocks

infix fun Completion.next(completionComponent: CompletionComponent) =
	apply {
		sections.add(CompletionComponentSection(mutableListOf(completionComponent), isRequired = sections.none { !it.isRequired }))
	}

infix fun Completion.next(completionVariable: CompletionVariable) =
	next(VariableCompletionComponent(completionVariable))

infix fun Completion.next(staticCompletion: Collection<String>) =
	next(StaticCompletionComponent(staticCompletion.toSet()))

fun Completion.next(
	staticCompletion: Collection<String>,
	displayRequirement: ((executor: CommandSender, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
	accessApproval: Approval? = null,
) =
	next(StaticCompletionComponent(staticCompletion.toSet(), displayRequirement, accessApproval))

infix fun Completion.next(
	staticCompletion: Array<String>,
) =
	next(StaticCompletionComponent(staticCompletion.toSet()))

fun Completion.next(
	staticCompletion: Array<String>,
	displayRequirement: ((executor: CommandSender, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
	accessApproval: Approval? = null,
) =
	next(StaticCompletionComponent(staticCompletion.toSet(), displayRequirement, accessApproval))

infix fun Completion.next(
	staticCompletion: String,
) =
	next(StaticCompletionComponent(setOf(staticCompletion)))

fun Completion.next(
	staticCompletion: String,
	displayRequirement: ((executor: CommandSender, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
	accessApproval: Approval? = null,
) =
	next(StaticCompletionComponent(setOf(staticCompletion), displayRequirement, accessApproval))

// And-Section blocks

infix operator fun Completion.plus(completionComponent: CompletionComponent) =
	apply {
		sections[currentSlot] = sections[currentSlot].apply { components.add(completionComponent) }
	}

infix operator fun Completion.plus(completionVariable: CompletionVariable) =
	plus(VariableCompletionComponent(completionVariable))

infix operator fun Completion.plus(staticCompletion: Collection<String>) =
	plus(StaticCompletionComponent(staticCompletion.toSet()))

fun Completion.plus(
	staticCompletion: Collection<String>,
	displayRequirement: ((executor: CommandSender, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
	accessApproval: Approval? = null,
) =
	plus(StaticCompletionComponent(staticCompletion.toSet(), displayRequirement, accessApproval))

infix operator fun Completion.plus(
	staticCompletion: Array<String>,
) =
	plus(StaticCompletionComponent(staticCompletion.toSet()))

fun Completion.plus(
	staticCompletion: Array<String>,
	displayRequirement: ((executor: CommandSender, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
	accessApproval: Approval? = null,
) =
	plus(StaticCompletionComponent(staticCompletion.toSet(), displayRequirement, accessApproval))

infix operator fun Completion.plus(
	staticCompletion: String,
) =
	plus(StaticCompletionComponent(setOf(staticCompletion)))

fun Completion.plus(
	staticCompletion: String,
	displayRequirement: ((executor: CommandSender, parameters: Array<String>, completion: Set<String>) -> Boolean)? = null,
	accessApproval: Approval? = null,
) =
	plus(StaticCompletionComponent(setOf(staticCompletion), displayRequirement, accessApproval))

// Properties

infix fun Completion.isRequired(isRequired: Boolean) = current {

	if (!(isRequired && sections.any { !it.isRequired })) {

		this.isRequired = isRequired

	} else
		throw IllegalStateException("No required parameters allowed after a non-required parameter used!")

}

infix fun Completion.mustMatchOutput(mustMatchOutput: Boolean) = current {
	this.mustMatchOutput = mustMatchOutput
}

infix fun Completion.label(label: String?) = current {
	this.label = label
}

infix fun Completion.infinite(infinite: Boolean) = apply {
	this.infinite = infinite
}

// Builder

fun emptyCompletion() = Completion(mutableListOf(), false)

inline fun buildCompletion(builderAction: Completion.() -> Unit) =
	emptyCompletion().apply(builderAction)
