package de.jet.app.interchange.player

import com.destroystokyo.paper.profile.ProfileProperty
import de.jet.app.JetData
import de.jet.library.extension.paper.createProfile
import de.jet.library.extension.system
import de.jet.library.extension.tasky.async
import de.jet.library.extension.tasky.sync
import de.jet.library.structure.app.App
import de.jet.library.structure.command.Interchange
import de.jet.library.structure.command.InterchangeExecutorType.PLAYER
import de.jet.library.structure.command.InterchangeResult
import de.jet.library.structure.command.InterchangeResult.SUCCESS
import de.jet.library.structure.command.emptyCompletion
import de.jet.library.structure.command.live.InterchangeAccess
import org.bukkit.entity.Player

class ChangeSkinInterchange(vendor: App = system) : Interchange(vendor, "changeskin", requiredExecutorType = PLAYER, completion = emptyCompletion()) {

	override val execution: InterchangeAccess.() -> InterchangeResult = {

		val player = executor as Player

		createProfile(player.uniqueId, player.name).apply {
			setProperty(ProfileProperty(
				"textures",
				JetData.profileDataFirst.content,
				JetData.profileDataSecond.content,
			))

			async {
				this@apply.complete()

				sync {
					player.playerProfile = this@apply
				}

			}

		}

		player.sendMessage("success!")

		SUCCESS
	}

}