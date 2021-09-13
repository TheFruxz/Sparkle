package de.jet.minecraft.app

import de.jet.library.extension.data.jetPath
import de.jet.minecraft.app.JetData.File.BRAIN
import de.jet.minecraft.app.JetData.File.CONFIG
import de.jet.minecraft.tool.data.DataTransformer
import de.jet.minecraft.tool.data.JetFile
import de.jet.minecraft.tool.data.Preference

object JetData {

	val debugMode = Preference(
		file = CONFIG,
		useCache = true,
		path = jetPath("debugMode"),
		default = false,
	)

	val systemPrefix = Preference(
		file = CONFIG,
		useCache = true,
		path = jetPath("prefix"),
		default = "§6JET §8» ",
	).transformer(DataTransformer.simpleColorCode())

	val systemLanguage = Preference(
		file = CONFIG,
		path = jetPath("language"),
		default = "en_general",
	)

	val autoStartComponents = Preference(
		file = CONFIG,
		path = jetPath("autoStartComponents"),
		default = setOf<String>(),
	).transformer(DataTransformer.setCollection())

	val touchedComponents = Preference(
		file = BRAIN,
		path = jetPath("touchedComponents"),
		default = setOf<String>()
	).transformer(DataTransformer.setCollection())

	val profileDataFirst = Preference(
		file = CONFIG,
		path = jetPath("profileDataFirst"),
		default = "ewogICJ0aW1lc3RhbXAiIDogMTYyOTU1NTk1OTQ5OSwKICAicHJvZmlsZUlkIiA6ICIwNGI5YTZkMTcyMGE0YThlOTc3OGU3NjUwNmRkYmFjNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJ4UGh5cm9GaWdodCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8zNzEyMjQyYWZhMzgxMDNjMzVhZjRjN2NmZTdjNzQxYzEyOWRkN2Q5ZTg2MjUyZmNiMDQxYjBmODMwNzA4ZjZiIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0="
	)

	val profileDataSecond = Preference(
		file = CONFIG,
		path = jetPath("profileDataSecond"),
		default = "EyQ2RAc5F4J6IIzjPc0s1OhVOEtw3aRMPP3lurYdJ7Hyn0AiDtJG5AmFMzErKZAUyIIoedftWEdDSP0h9+WRJ9EZ86i8imCgG8M/3tKNqhs3MRrEk75mehOa8fXGQh5GF3TSW7r1WRA/CpP9BCyRaLq+Ijr359jq+fL7gDygmTbwgte2FEr6WHHiH+9YH6ShtdVJyJ/6LcsuI2BJbheK2CWkG2GECvWj8Vg87kyLdpCsQpCQHWZh62eGskDVazG/gZynm9DP3o0u7G3p4d2uO6A/NRUQG1/7Y3RcZJi6Y+QY804SPTekutxmgh4HKYpg+kThRogx7TyuuSBqvEPYaaUatoDf1E4VdUyJIuJ4hodg2r+gqeznFGdVOrNxrwbYoWasg1X7E9YV0OvKTHHTLJRqQFxb6rUPMiTsnh3JfniGXCmZh2vSJ3QsfbOZNAN73aKIGB+jNZHDrjgkTZoMnLosfelonV/YS0skM7mHEdoC8z+WqvKUb7pUnZ2s9yfsC7ELBg4OE11uZ9tnyTRRMpmDtDIwajZ3qniwFYWel2rY8UUDMk0qCtRQa7aPSsgUfyoDUQyV1tWDcrBwAjiySd/wGLOBzmboHoQfYvf2cly9hQydPDThFo6WGstGLKSjxxHtuOwy4+mHy/C5VOkFtMAeconpz702ta3yoTEWZfI="
	)

	object File {

		val CONFIG = JetFile.rootFile("system-config")
		val BRAIN = JetFile.rootFile("system-memory")

	}

}