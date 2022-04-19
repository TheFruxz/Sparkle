package de.moltenKt.unfold

public interface MoltenContext<T> {

	public companion object {

		@JvmStatic
		public fun <X> contextOf(): MoltenContext<X> = object : MoltenContext<X> { }

	}

}