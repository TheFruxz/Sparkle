package de.jet.unfold

interface MoltenContext<T> {

	companion object {

		fun <X> contextOf() = object : MoltenContext<X> { }

	}

}