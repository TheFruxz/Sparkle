package de.moltenKt.unfold

interface MoltenContext<T> {

	companion object {

		@JvmStatic
		fun <X> contextOf() = object : MoltenContext<X> { }

	}

}