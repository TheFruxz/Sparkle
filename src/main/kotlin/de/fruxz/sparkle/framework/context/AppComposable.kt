package de.fruxz.sparkle.framework.context

import de.fruxz.ascend.tool.smart.composition.ParameterizedComposable
import de.fruxz.sparkle.framework.infrastructure.app.App

/**
 * This function interface is used to generate content, which
 * needs an app, to generate or work with.
 * This can, for example, be a default value for a constructor-property,
 * where an App is only selected, during runtime.
 * @author Fruxz
 * @since 1.0
 */
fun interface AppComposable<O> : ParameterizedComposable<O, App>