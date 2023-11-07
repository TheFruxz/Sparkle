package de.fruxz.sparkle.framework.infrastructure.service

import dev.fruxz.ascend.extension.container.firstOrNull
import dev.fruxz.ascend.extension.switchResult
import dev.fruxz.ascend.tool.time.calendar.Calendar
import de.fruxz.sparkle.framework.annotation.RequiresComponent.Companion.missingComponents
import de.fruxz.sparkle.framework.annotation.RequiresComponent.Companion.requirementsMet
import de.fruxz.sparkle.framework.infrastructure.Attachable
import de.fruxz.sparkle.framework.infrastructure.Hoster
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.service.Service.ServiceState
import de.fruxz.sparkle.server.SparkleCache
import dev.fruxz.stacked.extension.KeyingStrategy.CONTINUE
import dev.fruxz.stacked.extension.subKey
import dev.fruxz.stacked.extension.subKey
import kotlinx.coroutines.*
import net.kyori.adventure.key.Key
import java.util.logging.Logger
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

interface Service : Attachable, Hoster<Unit, Unit, Service> {

	val vendor: App

	val serviceTimes: ServiceTimes

	val serviceActions: ServiceActions

	val iteration: ServiceIteration

	val crashBehavior: CrashBehavior
		get() = CrashBehavior.CONTINUE

	override val identityKey: Key
		get() = vendor.subKey(label, CONTINUE)

	var serviceScope: CoroutineScope?
		get() = SparkleCache.services.firstOrNull { it.key == identityKey }?.value
		set(value) {
			if (value != null) {
				SparkleCache.services += identityKey to value
			} else
				SparkleCache.services -= identityKey
		}

	var serviceState: ServiceState?
		get() = SparkleCache.serviceStates.firstOrNull { it.key == identityKey }?.value
		set(value) {
			if (value != null) {
				SparkleCache.serviceStates += identityKey to value
			} else
				SparkleCache.serviceStates -= identityKey
		}

	val coroutineContext: CoroutineContext
		get() = serviceTimes.isAsync.switchResult(vendor.asyncDispatcher, vendor.syncDispatcher)

	val serviceLogger: Logger
		get() = App.createLog(vendor.key.value(), key.value())

	val isRegistered: Boolean
		get() = SparkleCache.services.containsKey(identityKey)

	val isRunning: Boolean
		get() = serviceState?.runningSince != null

	fun requestRegister() {
		internalRegisteredScope()
		serviceState = ServiceState(this, vendor, null, -1, serviceTimes)
	}

	override fun requestStart() {
		if (isRunning) return
		if (!requirementsMet()) {
			serviceLogger.warning("Service $label could not be started because components ${missingComponents().map { it.simpleName }} are missing.")
			return
		}

		internalRegisteredScope().also { originScope ->
			val time = Calendar.now()

			serviceState = ServiceState(this, vendor, time, -1, serviceTimes)

			originScope.launch(context = coroutineContext) {
				serviceActions.onStart.invoke(this@Service) // start-action
				if (serviceTimes.delay.isPositive()) delay(serviceTimes.delay)

				try {
					if (serviceTimes.interval.isPositive()) {
						var repetition = 0
						while (originScope.isActive) {
							val serviceState = ServiceState(this@Service, vendor, time, repetition++, serviceTimes)

							try {

								iteration.invoke(serviceState.also {
									this@Service.serviceState = serviceState
								}, this)

							} catch (exception: Exception) {
								when (crashBehavior) {
									CrashBehavior.STOP -> throw exception
									CrashBehavior.CONTINUE -> {
										serviceLogger.warning("Service '$key' throw exception, but continues:")
										exception.printStackTrace()
									}
									CrashBehavior.RESTART -> {
										serviceLogger.warning("Service '$key' throw exception, now restarting:")
										exception.printStackTrace()
										requestStop()
										requestStart()
									}
								}
							}

							delay(serviceTimes.interval)
						}
					} else iteration.invoke(ServiceState(this@Service, vendor, time, 0, serviceTimes), this)
				} catch (exception: Exception) {
					serviceLogger.warning("Service '${key}' crashed:")
					exception.printStackTrace()
					serviceActions.onCrash.invoke(this@Service, exception)
				}

			}
		}

	}

	override fun requestStop() {
		if (!isRunning) return

		serviceActions.onStop(this)
		serviceScope?.cancel()
		serviceState = ServiceState(this, vendor, null, -1, serviceTimes)

	}

	fun requestUnregister() {
		requestStop()
		serviceScope = null
		serviceState = null
	}

	private fun internalRegisteredScope() = CoroutineScope(coroutineContext).also { serviceScope = it }

	data class ServiceTimes(
		val delay: Duration = Duration.ZERO,
		val interval: Duration = Duration.ZERO,
		val isAsync: Boolean = true,
	)

	data class ServiceActions(
		val onStart: suspend (Service) -> Unit = { },
		val onStop: (Service) -> Unit = { },
		val onCrash: suspend (Service, Exception) -> Unit = { _, _ -> },
	)

	data class ServiceState(
		val service: Service,
		val vendor: App,
		val runningSince: Calendar?,
		val repetition: Int,
		val timing: ServiceTimes,
	)

	enum class CrashBehavior {
		CONTINUE,
		STOP,
		RESTART;
	}

}

typealias ServiceIteration = suspend ServiceState.(CoroutineScope) -> Unit