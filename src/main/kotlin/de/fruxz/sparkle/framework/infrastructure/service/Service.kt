package de.fruxz.sparkle.framework.infrastructure.service

import de.fruxz.ascend.extension.container.firstOrNull
import de.fruxz.ascend.extension.switchResult
import de.fruxz.ascend.tool.timing.calendar.Calendar
import de.fruxz.sparkle.framework.infrastructure.Hoster
import de.fruxz.sparkle.framework.infrastructure.app.App
import de.fruxz.sparkle.framework.infrastructure.service.Service.ServiceState
import de.fruxz.sparkle.server.SparkleCache
import de.fruxz.stacked.extension.KeyingStrategy.CONTINUE
import de.fruxz.stacked.extension.subKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import net.kyori.adventure.key.Key
import java.util.logging.Logger
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

interface Service : Hoster<Unit, Unit, Service> {

	val vendor: App

	val serviceTimes: ServiceTimes

	val serviceActions: ServiceActions

	val iteration: ServiceIteration

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

							iteration.invoke(serviceState.also {
								this@Service.serviceState = serviceState
							}, this)

							delay(serviceTimes.interval)
						}
					} else iteration.invoke(ServiceState(this@Service, vendor, time, 0, serviceTimes), this)
				} catch (e: Exception) {
					serviceLogger.warning("Service '${key()}' failed:")
					e.printStackTrace()
					serviceActions.onCrash.invoke(this@Service, e)
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

}

typealias ServiceIteration = suspend ServiceState.(CoroutineScope) -> Unit