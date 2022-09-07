package de.moltenKt.paper.app

import de.moltenKt.core.extension.data.fromJson
import de.moltenKt.core.extension.data.readJson
import de.moltenKt.core.extension.data.toJson
import de.moltenKt.core.extension.data.writeJson
import de.moltenKt.paper.extension.debugLog
import de.moltenKt.paper.extension.mainLog
import de.moltenKt.paper.tool.data.file.MoltenPath
import de.moltenKt.unfold.extension.asComponent
import de.moltenKt.unfold.extension.asStyledComponent
import kotlinx.serialization.Serializable
import java.util.*
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.div
import kotlin.io.path.notExists
import kotlin.io.path.readText
import kotlin.io.path.writeText

object MoltenLanguage {

    private val configuredLanguage: String = MoltenData.systemConfig.language
    private val configuredDefault: String = Locale.ENGLISH.language
    var path = MoltenPath.rootPath() / "lang" / "${MoltenData.systemConfig.language}.lang.json"
        private set

    lateinit var container: MoltenLanguageContainer

    init {
        fun check() {
            if (path.notExists()) {

                if (configuredLanguage != configuredDefault) {
                    mainLog.warning("Language $configuredLanguage not found! Using default language $configuredDefault instead!")
                    MoltenData.systemConfig = MoltenData.systemConfig.copy(
                        language = configuredLanguage
                    )
                    MoltenPath.rootPath() / "lang" / configuredDefault
                    check()
                } else {

                    debugLog("Generating language file...")
                    path.parent.createDirectories()
                    path.createFile()
                    path.writeJson(MoltenLanguageData())
                    debugLog("Successfully generated language file!")
                    check()

                }

            } else {

                container = MoltenLanguageContainer(
                    path.readJson<MoltenLanguageData>().languageData.associate { it.key to it.content }
                )
                debugLog("Successfully loaded language file!")

            }
        }

        check()

    }

    data class MoltenLanguageContainer(
        private val data: Map<String, String>,
    ) {

        operator fun get(key: String): String {
            return data[key] ?: key
        }

        fun getComponent(key: String) =
            get(key).asComponent

        fun getStyledComponent(key: String) =
            get(key).asStyledComponent

    }

    @Serializable
    data class MoltenLanguageData(
        val languageId: String = Locale.ENGLISH.language,
        val languageVendor: String = MoltenApp.identity,
        val languageVendorWebsite: String = MoltenApp.instance.description.website ?: "",
        val languageVersion: String = MoltenApp.instance.description.version,
        val moltenVersion: String = MoltenApp.instance.description.version,
        val languageData: List<MoltenLanguageElement> = languageDataOf(
            "system.hello" to "Welcome to MoltenKT! - Welcome to this Language!",

            "system.prefix.general" to "<gold>MoltenKT <dark_gray>» ",
            "system.prefix.info" to "<gold>INFO <dark_gray>» ",
            "system.prefix.fail" to "<red>FAIL <dark_gray>» ",
            "system.prefix.error" to "<red><bold>ERROR</bold> <dark_gray>» ",
            "system.prefix.level" to "<purple>STATS <dark_gray>» ",
            "system.prefix.warning" to "<yellow><bold>WARNING</bold> <dark_gray>» ",
            "system.prefix.attention" to "<yellow>ATTENTION <dark_gray>» ",
            "system.prefix.payment" to "<aqua>PAYMENT <dark_gray>» ",
            "system.prefix.applied" to "<green>APPLIED <dark_gray>» ",

            "system.message.style" to "[sender] <gray>to [receiver] <dark_gray>» <gray>[message]",
            "system.message.style.you" to "YOU",

            "splashscreen.loading" to "<bold><gradient:yellow:#eb6b34>Loading...</gradient></bold>",

            "interchange.run.issue.wrongApproval" to "<gray>This action<red> requires<gray> the approval <yellow>'[approval]'<gray>, to be executed!",
            "interchange.run.issue.wrongUsage" to "<gray>Follow the <red>syntax<gray>, to execute this! See:",
            "interchange.run.issue.wrongClient" to "<gray>This action<red> needs<gray> you as a '<yellow>[client]<gray>' to be executed!",
            "interchange.run.issue.issue" to "<red><bold>Oops!</bold><gray> A<red> critical error<gray> occurred unexpectedly while executing the '<yellow>[interchange]<gray>' action! Please report this to a technician!",
            "interchange.run.check.tooShort" to "<gray>The input <red>must<gray> contain at least <yellow>[minimumParameters]<gray> parameters!",
            "interchange.run.check.tooLarge" to "<gray>The input should contain a <red>maximum<gray> of <yellow>[maximumParameters]<gray> parameters!",
            "interchange.run.check.noMatch" to "<gray>The input '<red>[input]<gray>' is not matching the requirements! Please use '<yellow>[usage]<gray>'!",

            "interchange.structure.issue.register" to "<red><bold>Oops!</bold><gray> This interchange <red>crashed <gray>during the <yellow>registration<gray> process, please report this to a technician!",

            "interchange.internal.component.alreadyRunning" to "<gray>The component '<yellow>[component]<gray>' is <red>already<gray> running!",
            "interchange.internal.component.blocked" to "<gray>The component '<yellow>[component]<gray>' is <red>blocked<gray> inside the components.json!",
            "interchange.internal.component.nowRunning" to "<gray>The component '<yellow>[component]<gray>' got <green>successfully<gray> started!",
            "interchange.internal.component.missingRunning" to "<gray>The component '<yellow>[component]<gray>' is <red>not<gray> running!",
            "interchange.internal.component.nowStopped" to "<gray>The component '<yellow>[component]<gray>' got <green>successfully<gray> stopped!",
            "interchange.internal.component.runningStatic" to "<gray>The component '<yellow>[component]<gray>' is <red>programmed<gray> to stay online!",
            "interchange.internal.component.autoStartRemoved" to "<gray>The component '<yellow>[component]<gray>' is <red>no longer<gray> on the AutoStart list!",
            "interchange.internal.component.autoStartAdded" to "<gray>The component '<yellow>[component]<gray>' is <dark_green>now<gray> on the AutoStart list!",
            "interchange.internal.component.autoStartStatic" to "<gray>The component '<yellow>[component]<gray>' is <red>programmed<gray> to stay on the AutoStart list!",
            "interchange.internal.component.list.header" to "<gray>List of all registered components <yellow>(Page [p1] of [p2])<gray>:",
            "interchange.internal.component.list.description" to "<gray>[1] Power; [2] Autostart; [3] Forced; [4] Experimental",
            "interchange.internal.component.list.line" to "[status] [autoStart] [force] [experimental] <dark_gray>| <yellow>[component]",
            "interchange.internal.component.reset" to "<gray>The component '<yellow>[component]<gray>' was <dark_green>successfully<gray> reset!",
            "interchange.internal.component.info.header" to "<gray>Information about the component '<yellow>[component]<gray>':",
            "interchange.internal.component.info.line" to "<gray>[key]: <yellow>[value]<gray>;",
            "interchange.internal.component.info.dict.name" to "Identity",
            "interchange.internal.component.info.dict.running" to "Running",
            "interchange.internal.component.info.dict.true" to "<dark_green>YES",
            "interchange.internal.component.info.dict.false" to "<red>NO",
            "interchange.internal.component.info.dict.vendor" to "Vendor",
            "interchange.internal.component.info.dict.isAutoStart" to "Auto-Starting",
            "interchange.internal.component.info.dict.isExperimental" to "Experimental",
            "interchange.internal.component.info.dict.configuration" to "Configuration-Tag",
            "interchange.internal.component.info.dict.runningSince" to "Running since",

            "interchange.internal.service.serviceStarted" to "<gray>The service '<yellow>[id]<gray>' was started <green>successfully<gray>!",
            "interchange.internal.service.serviceAlreadyOnline" to "<gray>The service '<yellow>[id]<gray>' is <red>already<gray> active!",
            "interchange.internal.service.serviceStopped" to "<gray>The service '<yellow>[id]<gray>' was stopped <green>successfully<gray>!",
            "interchange.internal.service.serviceAlreadyOffline" to "<gray>The service '<yellow>[id]<gray>' is <red>already<gray> inactive!",
            "interchange.internal.service.serviceRestarted" to "<gray>The service '<yellow>[id]<gray>' was restarted <green>successfully<gray>!",
            "interchange.internal.service.serviceUnregistered" to "<gray>The service '<yellow>[id]<gray>' was unregistered <green>successfully<gray>!",
            "interchange.internal.service.serviceReset" to "<gray>The service '<yellow>[id]<gray>' got a reset <green>successfully<gray>!",
            "interchange.internal.service.serviceNotFound" to "<gray>There are <red>no<gray> services with the group or id '<yellow>[id]<gray>' registered!",
            "interchange.internal.service.noServices" to "<gray>There are <red>no<gray> services registered!",
            "interchange.internal.service.list.header" to "<gray>List of all registered services <yellow>(Page [p1] of [p2])<gray>:",
            "interchange.internal.service.list.line" to "<yellow>[service]<gray> -> [enabled]<gray> ([activeSince]),",

            "interchange.internal.sandbox.list.header" to "<gray>List of all registered SandBoxes <yellow>(Page [p1] of [p2])<gray>:",
            "interchange.internal.sandbox.list.line" to "<yellow>[sandbox]<gray> (from: [vendor])",
            "interchange.internal.sandbox.dropAll" to "<green>Successfully<gray> removed<green> [amount]<gray> SandBoxes!",
            "interchange.internal.sandbox.runAll" to "<green>Successfully<gray> started<green> [amount]<gray> SandBoxes!",
            "interchange.internal.sandbox.noFound" to "<gray>There are<red> no<gray> registered SandBoxes!",
            "interchange.internal.sandbox.notFound" to "<gray>There is <red>no<gray> SandBox called '<yellow>[sandbox]<gray>' registered!",
            "interchange.internal.sandbox.run" to "<gray>You're now running the SandBox '<gold>[sandbox]<gray>'!",
            "interchange.internal.sandbox.failed" to "<gray>You're SandBox '<yellow>[sandbox]<gray>' failed due an <red>exception<gray>, StackTrace available in the console & logs!",
            "interchange.internal.sandbox.drop" to "<gray>Successfully<gray> dropped the SandBox '<gold>[sandbox]<gray>'!",
            "interchange.internal.sandbox.info.header" to "<gray>Info of registered SandBox:",
            "interchange.internal.sandbox.info.line" to "<gray>Key: <gold>[key]<gray>; value: '<yellow>[value]<gray>'",

            "interchange.internal.buildmode.neverOnline" to "<gray>The player '<yellow>[player]<gray>' was <red>never<gray> online!",
            "interchange.internal.buildmode.info" to "<gray>The player '<yellow>[player]<gray>'s BuildMode is currently: <gold>[state]<gray>!",
            "interchange.internal.buildmode.enable" to "<gray>The player '<yellow>[player]<gray>'s BuildMode is now <dark_green>enabled<gray>!",
            "interchange.internal.buildmode.disable" to "<gray>The player '<yellow>[player]<gray>'s BuildMode is now <red>disabled<gray>!",
            "interchange.internal.buildmode.stay" to "<gray>The player '<yellow>[player]<gray>' had <red>already<gray> that BuildMode state!",
            "interchange.internal.buildmode.list.empty" to "<gray>There are currently <red>no<gray> players with active BuildMode status!",
            "interchange.internal.buildmode.list.header" to "<gray>These players have BuildMode enabled <yellow>(Page [p1] of [p2])<gray>:",
            "interchange.internal.buildmode.list.line" to "<gray>- [statusColor]<bold>[player]</bold>",

            "interchange.internal.changeskin.reset" to "<green>Successfully <gray>reset skin of '<yellow>[player]<gray>' back to original skin!",
            "interchange.internal.changeskin.change" to "<green>Successfully <gray>applied skin of '<yellow>[skin]<gray>' at '<gold>[player]<gray>'!",
            "interchange.internal.changeskin.failed" to "<red>FAILED <gray>to get skin of player called '<yellow>[target]<gray>'!",
            "interchange.internal.changeskin.remote" to "<gray>A <yellow>remote <gray>user applied <gold>skin changes <gray>at your profile!",

            "component.markingTool.interchange.success" to "<gray>The <yellow>MARKING-TOOL<gray> got <dark_green>successfully<gray> added to your inventory!",
            "component.markingTool.action.set" to "<gray>Position #[n] set to <light_purple>[pos]<gray>!",
            "component.markingTool.action.duplicate" to "<gray>This position is <red>already<gray> set to <yellow>[pos]<gray>!",
            "component.markingTool.action.wrongLook" to "<gray>You are <red>not <gray>looking at a valid <yellow>block<gray>!",
            "component.markingTool.action.view.notSet" to "<gray>You have <red>no</red> valid <yellow>locations<gray> set!",
            "component.markingTool.action.view.detail" to "<gray>You have the locations <light_purple>[1] <gray>and <light_purple>[2] <gray>set!",
            "component.markingTool.action.view.distance.both" to "<gray>Both locations are <gold>[distance] Block(s) <gray>away!",
            "component.markingTool.action.view.distance.other" to "<gray>Other position is <gold>[distance] Block(s) <gray>away!",
            "component.markingTool.action.view.distance.volume" to "<gray>Volume: <gold>[volume] Block(s) <gray>containing!",
            "component.markingTool.action.view.distance.acrossWorlds" to "<red>This information is not available at markings across different worlds!",

            "interchange.internal.preference.list.empty" to "<gray>There are currently <red>no<gray> preferences registered!",
            "interchange.internal.preference.list.header" to "<gray>These '<gold>[amount]<gray>' preferences are currently registered:",
            "interchange.internal.preference.list.stash" to "<gray>[preference-array]",

            "interchange.internal.essentials.point.list.empty" to "<gray>There are currently <red>no<gray> points saved!",
            "interchange.internal.essentials.point.list.header" to "<gray>List of all saved points <yellow>(Page [p1] of [p2])<gray>:",
            "interchange.internal.essentials.point.list.entry" to "<gray>- <yellow>[point]<gray> <light_purple>[x] | [y] | [z]",
            "interchange.internal.essentials.point.edit.removed" to "<dark_green>Successfully <gray>removed the Point '<yellow>[point]<gray>'!",
            "interchange.internal.essentials.point.edit.created" to "<gray>A Point called '<yellow>[point]<gray>' got <dark_green>successfully<gray> created!",
            "interchange.internal.essentials.point.edit.notFound" to "<gray>A Point called '<yellow>[point]<gray>' is not saved!",
            "interchange.internal.essentials.point.edit.exists" to "<gray>A Point called '<yellow>[point]<gray>' <red>already exists<gray>!",
            "interchange.internal.essentials.point.edit.teleportedSelf" to "<gray>Teleported you to the Point '<yellow>[point]<gray>'!",
            "interchange.internal.essentials.point.edit.teleportedAll" to "<gray>Teleported everybody to the Point '<yellow>[point]<gray>'!",

            "interchange.internal.message.reply.failed.noChatReceiver" to "<gray>You're <red>not<gray> having an active conversation with anyone online!",

        ),
    ) {

        @Serializable
        data class MoltenLanguageElement(
            val key: String,
            val content: String,
        )

        companion object {

            fun languageDataOf(vararg pairs: Pair<String, String>) = pairs.map { MoltenLanguageElement(it.first, it.second) }

        }

    }

}