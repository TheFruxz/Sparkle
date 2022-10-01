package de.fruxz.sparkle.extension.paper

import de.fruxz.sparkle.app.MoltenApp
import de.fruxz.sparkle.extension.data.ticks
import de.fruxz.sparkle.extension.paper.KeyFramesAlignment.*
import de.fruxz.stacked.extension.Times
import de.fruxz.stacked.extension.Title
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.Title.Times
import org.bukkit.entity.Entity
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toKotlinDuration

fun Title.copy(
	title: Component = this.title(),
	subtitle: Component = this.subtitle(),
	times: Times? = this.times(),
) = Title(title, subtitle, times)

/**
 * This function shows the animated title to [this] given [Entity],
 * @param title the title-frames to show
 * @param subtitle the subtitle-frames to show
 * @param times the times of the title
 * @param timeBetweenTicks the duration between every frame
 * @param alignment the alignment of the shorter animation-frames (if title or subtitle animation is shorter than the other one)
 * @param renderFadeIn if the first frame should have the fadeIn-effect of [times]
 * @return the running job
 * @author Fruxz
 * @since 1.0
 */
fun Entity.showAnimatedTitle(title: List<Component>, subtitle: List<Component>, times: Times = Times(20.ticks, 60.ticks, 20.ticks), timeBetweenTicks: Duration = .1.seconds, alignment: KeyFramesAlignment = CENTER, renderFadeIn: Boolean = true) = de.fruxz.sparkle.app.MoltenApp.coroutineScope.launch {
	if (title.isEmpty() || subtitle.isEmpty()) throw IllegalArgumentException("Title and subtitle must not be empty")

	val (titles, subtitles) = title.toMutableList() to subtitle.toMutableList()

	fun alignToSize(target: MutableList<Component>, size: Int) {
		while (target.size < size) {
			when (alignment) {
				START -> target.add(target.first())
				END -> target.add(0, target.last())
				CENTER -> {
					if (target.size % 2 == 0) {
						target.add(0, target.first())
					} else
						target.add(target.last())
				}
			}
		}
	}

	if (titles.size > subtitles.size) alignToSize(subtitles, titles.size) else if (titles.size < subtitles.size) alignToSize(titles, subtitles.size)

	if (renderFadeIn) {
		this@showAnimatedTitle.showTitle(Title(titles.first(), subtitles.first(), times))
		delay(times.fadeIn().toKotlinDuration())
		titles.drop(1)
		subtitles.drop(1)
	}

	titles.withIndex().forEach { (index, title) ->
		subtitles[index].let { subtitle ->
			this@showAnimatedTitle.showTitle(Title(title, subtitle, Times(Duration.ZERO, times.stay().toKotlinDuration(), times.fadeOut().toKotlinDuration())))
		}
		delay(timeBetweenTicks)
	}

}

/**
 * This function shows the animated title to [this] given [Entity],
 * @param title the title-frames to show
 * @param subtitle the single subtitle-frame to show
 * @param times the times of the title
 * @param timeBetweenTicks the duration between every frame
 * @param alignment the alignment of the shorter animation-frames (if title or subtitle animation is shorter than the other one)
 * @param renderFadeIn if the first frame should have the fadeIn-effect of [times]
 * @return the running job
 * @author Fruxz
 * @since 1.0
 */
fun Entity.showAnimatedTitle(title: List<Component>, subtitle: Component, times: Times = Times(20.ticks, 60.ticks, 20.ticks), timeBetweenTicks: Duration = .15.seconds, alignment: KeyFramesAlignment = CENTER, renderFadeIn: Boolean = true) =
	showAnimatedTitle(title, listOf(subtitle), times, timeBetweenTicks, alignment, renderFadeIn)

/**
 * This function shows the animated title to [this] given [Entity],
 * @param title the single title-frame to show
 * @param subtitle the subtitle-frames to show
 * @param times the times of the title
 * @param timeBetweenTicks the duration between every frame
 * @param alignment the alignment of the shorter animation-frames (if title or subtitle animation is shorter than the other one)
 * @param renderFadeIn if the first frame should have the fadeIn-effect of [times]
 * @return the running job
 * @author Fruxz
 * @since 1.0
 */
fun Entity.showAnimatedTitle(title: Component, subtitle: List<Component>, times: Times = Times(20.ticks, 60.ticks, 20.ticks), timeBetweenTicks: Duration = .15.seconds, alignment: KeyFramesAlignment = CENTER, renderFadeIn: Boolean = true) =
	showAnimatedTitle(listOf(title), subtitle, times, timeBetweenTicks, alignment, renderFadeIn)

/**
 * This enum class helps to define, in which timing
 * the 2 different animations of the [showAnimatedTitle]
 * function should be aligned.
 * @author Fruxz
 * @since 1.0
 */
enum class KeyFramesAlignment {
	START,
	CENTER,
	END;
}