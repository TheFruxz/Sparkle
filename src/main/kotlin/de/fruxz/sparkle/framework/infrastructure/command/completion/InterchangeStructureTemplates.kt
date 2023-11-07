package de.fruxz.sparkle.framework.infrastructure.command.completion

import dev.fruxz.ascend.extension.container.paged
import dev.fruxz.ascend.extension.math.ceilToInt
import dev.fruxz.ascend.tool.collection.PagedIterable
import dev.fruxz.ascend.tool.smart.composition.Composable
import dev.fruxz.ascend.tool.smart.composition.ParameterizedComposable
import de.fruxz.sparkle.framework.extension.interchange.InterchangeExecutor
import de.fruxz.sparkle.framework.extension.visual.notification
import de.fruxz.sparkle.framework.infrastructure.command.InterchangeUserRestriction
import de.fruxz.sparkle.framework.infrastructure.command.completion.content.CompletionAsset
import de.fruxz.sparkle.framework.visual.message.TransmissionAppearance
import de.fruxz.sparkle.server.SparkleData
import dev.fruxz.stacked.extension.dyeGray
import dev.fruxz.stacked.extension.dyeYellow
import dev.fruxz.stacked.extension.newline
import dev.fruxz.stacked.extension.newlines
import dev.fruxz.stacked.plus
import dev.fruxz.stacked.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike

fun <T, EXECUTOR : InterchangeExecutor> InterchangeStructure<EXECUTOR>.branchTemplateList(
	contents: Composable<Iterable<T>>,
	contentNames: String = "elements",
	branchName: String = "list",
	header: ParameterizedComposable<ComponentLike, PagedIterable<T>> = ParameterizedComposable { text {
		this + text("List of all available $contentNames: ").dyeGray()
		this + text("(Page ${it.pageNumber} of ${it.availablePages.last})").dyeYellow()
	} },
	userRestriction: InterchangeUserRestriction = this.userRestriction,
	configuration: CompletionBranchConfiguration = CompletionBranchConfiguration(),
	process: InterchangeStructure<EXECUTOR>.() -> Unit = { },
	entry: ParameterizedComposable<ComponentLike, T>,
) {
	val entriesPerPage = SparkleData.systemConfig.entriesPerListPage

	fun pageView(executor: EXECUTOR, page: Int) {
		val composedContent = contents.compose()
		val paged = composedContent.paged(page, entriesPerPage)

		if (composedContent.any()) {
			text {
				this + header.compose(paged)
				newlines(2)
				composedContent.forEach { this + entry.compose(it) + Component.newline() }
				newline()
			}.notification(TransmissionAppearance.GENERAL, executor).display()
		} else {
			text {
				this + text("There are currently ").dyeGray()
				this + text("no $contentNames").dyeYellow()
				this + text(" available!").dyeGray()
			}.notification(TransmissionAppearance.FAIL, executor).display()
		}

	}

	this.branch(
		userRestriction = userRestriction,
		configuration = configuration,
	) {
		apply(process)
		addContent(branchName)

		concludedExecution {
			pageView(executor, 0)
		}

		branch {
			addContent(CompletionAsset.pageCompletion { ceilToInt(contents.compose().count().toDouble() / entriesPerPage) })
			isNotRequired()
			concludedExecution {
				pageView(executor, getInput().toInt())
			}
		}

	}

}