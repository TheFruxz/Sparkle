package de.jet.javacord

import de.jet.javacord.extension.activity
import de.jet.javacord.extension.appearance
import de.jet.javacord.extension.credentials
import de.jet.javacord.extension.postLogin
import de.jet.javacord.extension.postProcess
import de.jet.javacord.extension.preLogin
import de.jet.javacord.extension.preProcess
import de.jet.javacord.extension.preSetup
import de.jet.javacord.extension.runBot
import de.jet.jvm.extension.data.url
import de.jet.jvm.extension.input.buildConsoleInterchange
import org.javacord.api.entity.activity.ActivityType.LISTENING
import org.javacord.api.entity.activity.ActivityType.STREAMING
import org.javacord.api.entity.user.UserStatus.ONLINE

fun main() = runBot {

	credentials {
		botToken = "OTA5OXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXIvDkM"
	}

	appearance {

		displayName = "JavaCord-Bot"
		status = ONLINE

		activity {
			name = "JET-JavaCord"
			activityType = STREAMING
			streamingUrl = url("https://www.youtube.com/watch?v=ow5kdhDa_pk")
		}

	}

	preProcess {
		println("PreProcess")
	}

	preSetup {
		println("PreSetup")
	}

	preLogin {
		println("PreLogin")
	}

	postLogin {
		println("PostLogin")
	}

	postProcess {
		println("PostProcess")
	}

}