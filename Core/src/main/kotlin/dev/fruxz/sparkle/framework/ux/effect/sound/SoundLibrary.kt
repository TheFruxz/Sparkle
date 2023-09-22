package dev.fruxz.sparkle.framework.ux.effect.sound

import dev.fruxz.sparkle.framework.util.ticks
import org.bukkit.Sound

enum class SoundLibrary(val sound: SoundEffect) {

    NOTIFICATION_FAIL(buildMelody {
        beatDelay = 3.ticks

        beat {
            sound(Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, pitch = 0, volume = .2)
            sound(Sound.BLOCK_NOTE_BLOCK_BASS)
        }
        beat(Sound.BLOCK_NOTE_BLOCK_BASS, pitch = 0)

    }),

    NOTIFICATION_ERROR(buildMelody {
        repetitionDelay = 3.ticks
        amount = 3

        beat {
            sound(Sound.BLOCK_NOTE_BLOCK_BASS, pitch = 0)
            sound(Sound.BLOCK_NOTE_BLOCK_BIT, pitch = 0)
        }

    }),

    NOTIFICATION_PROCESS(buildMelody {
        beatDelay = 3.ticks

        beat {
            sound(Sound.UI_TOAST_OUT, pitch = 2)
            sound(Sound.BLOCK_NOTE_BLOCK_PLING)
        }
        beat(Sound.BLOCK_NOTE_BLOCK_PLING, pitch = 2)

    }),

    NOTIFICATION_GENERAL(buildMelody {

        beat(Sound.BLOCK_NOTE_BLOCK_HAT)

    }),

    NOTIFICATION_LEVEL(buildMelody {
        beatDelay = 3.ticks

        beat {
            sound(Sound.UI_TOAST_CHALLENGE_COMPLETE, pitch = 2, volume = .5)
            sound(Sound.ENTITY_PLAYER_LEVELUP)
        }
        beat(Sound.ENTITY_PLAYER_LEVELUP, pitch = 2)

    }),

    NOTIFICATION_WARNING(buildMelody {
        repetitionDelay = 3.ticks
        amount = 3

        beat {
            sound(Sound.BLOCK_NOTE_BLOCK_BASS, pitch = 0)
            sound(Sound.BLOCK_NOTE_BLOCK_BASS, pitch = 2)
        }

    }),

    NOTIFICATION_ATTENTION(buildMelody {
        repetitionDelay = 1.ticks
        amount = 30

        beat(Sound.BLOCK_NOTE_BLOCK_PLING, pitch = 2)

    }),

    NOTIFICATION_PAYMENT(buildMelody {
        beatDelay = 3.ticks

        beat {
            sound(Sound.BLOCK_NOTE_BLOCK_PLING)
            sound(Sound.ITEM_ARMOR_EQUIP_CHAIN)
        }
        beat(Sound.BLOCK_NOTE_BLOCK_PLING, pitch = 2)

    }),

    NOTIFICATION_APPLIED(buildMelody {
        beatDelay = 2.ticks

        beat {
            sound(Sound.BLOCK_NOTE_BLOCK_PLING, pitch = 1)
        }

        beat {
            sound(Sound.BLOCK_NOTE_BLOCK_PLING, pitch = 2)
        }

    })

}