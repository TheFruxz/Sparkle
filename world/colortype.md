# 🌈 ColorType

<figure><img src="../.gitbook/assets/DyeableWool.jpg" alt=""><figcaption><p>The available colors in ColorType</p></figcaption></figure>

ColorTypes are the available colors, which are represented in colored blocks, like beds, concrete, wool, and stained glass.

The ColorType can be used, to easily get the different materials of the current color, like `dye`, `shulker`, `stainedGlass`, `bed`, etc. For simply everything exists and property, which returns the current color applied on the material. Like you have `ColorType.RED` and use the `.bed` property on the color type, you get a `Material.RED_BED` returned!

A ColorType also can be used, to get the dyeColor, Bukkits ChatColor, dyedChatColor, a normal Java color, BarColor (for Boss-Bars), an adventure color, and a TextColor, matching best to the current color, but these things may not be 100% accurate.

Due to these capabilities, a ColorType is also an RGBLike object, so it can be used as a real color in some places, most regarding the Adventure API & Stacked.

