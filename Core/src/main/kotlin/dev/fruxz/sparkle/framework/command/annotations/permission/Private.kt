package dev.fruxz.sparkle.framework.command.annotations.permission

/**
 * Assigns a specific permission to a command, if the command is not marked with [Public]
 */
annotation class Private(val permission: String)