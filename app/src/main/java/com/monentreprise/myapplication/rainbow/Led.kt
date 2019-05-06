package com.monentreprise.myapplication.rainbow

import android.graphics.Color
import android.os.Handler
import com.google.android.things.contrib.driver.apa102.Apa102
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio
import kotlin.random.Random

class Led {

    enum class ID {
        ALL,
        RED,
        GREEN,
        BLUE,
        STRIP
    }

    private val blinkHandler: Handler = Handler()
    private var redLED: Gpio? = null
    private var greenLED: Gpio? = null
    private var blueLED: Gpio? = null
    private var stripLED: Apa102? = null
    private var durationMs: Long = 1000
    var stripColors: IntArray = intArrayOf(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK)
    lateinit var stripColor: IntArray
    private val stripLedOff: IntArray = intArrayOf(-1, -1, -1, -1, -1, -1, -1)

    fun blink(ID: ID = Led.ID.ALL, durationMs: Long = 1000) {
        this.durationMs = durationMs
        open(ID)
        blinkHandler.postDelayed(blinkRunnable, durationMs)
    }

    fun UpdateLed(lesLed : IntArray){
        stripLED?.write(lesLed)
    }

    fun Allblack(ID: ID = Led.ID.STRIP){
        stripLED?.write(stripColors)
    }

    fun launch(ID: ID = Led.ID.STRIP){

        setbrihtness(1)
        stripColor = intArrayOf(randomColor(), randomColor(), randomColor(), randomColor(), randomColor(), randomColor(), randomColor())
        stripLED?.write(stripColor)
    }

    fun setbrihtness(int: Int) {
        stripLED?.brightness = int
    }

    fun turn(ID: ID = Led.ID.ALL, on: Boolean) {
        open(ID)
        when (ID) {
            Led.ID.ALL -> {
                redLED?.value = on
                greenLED?.value = on
                blueLED?.value = on
                if (on) {
                    stripLED?.write(stripColors)
                } else {
                    stripLED?.write(stripLedOff)
                }
            }
            Led.ID.RED -> redLED?.value = on
            Led.ID.GREEN -> greenLED?.value = on
            Led.ID.BLUE -> blueLED?.value = on
        }
    }

    fun clean() {
        blinkHandler.removeCallbacks(blinkRunnable)
        turn(ID.ALL, false)
        redLED?.close()
        greenLED?.close()
        blueLED?.close()
        stripLED?.close()
    }

    private fun open(ID: ID = Led.ID.ALL) {
        when (ID) {
            Led.ID.ALL -> {
                if (redLED == null) {
                    redLED = RainbowHat.openLedRed()
                }
                if (greenLED == null) {
                    greenLED = RainbowHat.openLedGreen()
                }
                if (blueLED == null) {
                    blueLED = RainbowHat.openLedBlue()
                }
                if (stripLED == null) {
                    stripLED = RainbowHat.openLedStrip()
                }
            }

            Led.ID.RED -> {
                if (redLED == null) {
                    redLED = RainbowHat.openLedRed()
                }
            }

            Led.ID.GREEN -> {
                if (greenLED == null) {
                    greenLED = RainbowHat.openLedGreen()
                }
            }

            Led.ID.BLUE -> {
                if (blueLED == null) {
                    blueLED = RainbowHat.openLedBlue()
                }
            }

            Led.ID.STRIP -> {
                if (stripLED == null) {
                    stripLED = RainbowHat.openLedStrip()
                }
            }

        }
    }

    fun randomColor() : Int {
        val intRandom = Random.nextInt(0, 3)
        when (intRandom) {
            0 -> return Color.RED
            1 -> return Color.BLUE
            2 -> return Color.GREEN
        }
        return Color.WHITE
    }

    private val blinkRunnable = object : Runnable {
        override fun run() {
            redLED?.also {
                it.value = !it.value
            }

            greenLED?.also {
                it.value = !it.value
            }

            blueLED?.also {
                it.value = !it.value
            }

            stripLED?.also {
                stripColor = intArrayOf(stripColors.last()) + stripColors.dropLast(1)
                it.write(stripColor)
            }

            // reschedule the update
            blinkHandler.postDelayed(this, durationMs)
        }
    }
}
