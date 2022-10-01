package de.moltenKt.paper.tool.display.canvas

/**
 * This enum defines multiple possible reactions of a canvas.
 * These enum values can be attached to a [MutableCanvas].
 * @author Fruxz
 * @since 1.0
 */
enum class CanvasFlag {

	/**
	 * This flag defines, that no onClick-based action is triggered.
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_CLICK_ACTIONS,

	/**
	 * This flag defines, that every item grab is cancelled.
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_GRAB,

	/**
	 * This flag defines, that every item move between inventories is cancelled.
	 * **see InventoryMoveItemEvent**
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_MOVE,

	/**
	 * This flag defines, that every item drag is cancelled.
	 * **see InventoryDragEvent**
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_DRAG,

	/**
	 * This flag defines, that every item swap is cancelled.
	 * **see PlayerSwapHandItemsEvent**
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_SWAP,

	/**
	 * This flag defines, that the canvas cannot be closed.
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_CLOSE,

	/**
	 * This flag defines, that the canvas cannot be opened.
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_OPEN,

	/**
	 * This flag defines, that the canvas update function does not get executed.
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_UPDATE,

	/**
	 * This flag defines, that a canvas update does not perform a canvas push
	 * (re-register in canvas & canvas-reaction{onOpen, onClose, onClicks} map)
	 * @author Fruxz
	 * @since 1.0
	 */
	NO_UPDATE_PUSHES;

}