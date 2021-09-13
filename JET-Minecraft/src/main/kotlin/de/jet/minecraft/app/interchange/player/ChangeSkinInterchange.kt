package de.jet.minecraft.app.interchange.player

import com.destroystokyo.paper.profile.ProfileProperty
import de.jet.library.extension.paper.createProfile
import de.jet.minecraft.app.JetData
import de.jet.minecraft.extension.system
import de.jet.minecraft.extension.tasky.async
import de.jet.minecraft.extension.tasky.sync
import de.jet.minecraft.structure.app.App
import de.jet.minecraft.structure.command.Interchange
import de.jet.minecraft.structure.command.InterchangeExecutorType.PLAYER
import de.jet.minecraft.structure.command.InterchangeResult
import de.jet.minecraft.structure.command.InterchangeResult.SUCCESS
import de.jet.minecraft.structure.command.emptyCompletion
import de.jet.minecraft.structure.command.live.InterchangeAccess
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