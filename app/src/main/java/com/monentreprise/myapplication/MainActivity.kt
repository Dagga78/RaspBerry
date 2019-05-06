package com.monentreprise.myapplication

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import java.io.IOException
import android.util.Log
import android.view.KeyEvent
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat

import com.google.android.things.pio.PeripheralManager
import com.monentreprise.myapplication.rainbow.*
import kotlin.math.log
import android.content.Intent



class MainActivity : Activity() {

    val led = Led()
    val buttons = HatButton()
    val piezo = Piezo()
    val temperatureSensor = TemperatureSensor()
    val topDisplay = TopDisplay()
    var colorgive = IntArray(7)
    lateinit var colorneeded: IntArray
    var index: Int = 6

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //Log.plant(DebugTree())
        val manager = PeripheralManager.getInstance()
        Log.d("test", "Available GPIO: " + manager.gpioList)

        runSafeIO {

            val buttonDriverA = RainbowHat.createButtonAInputDriver(
                KeyEvent.KEYCODE_A      // keyCode to send
            )
            val buttonDriverB = RainbowHat.createButtonBInputDriver(
                KeyEvent.KEYCODE_B      // keyCode to send
            )
            val buttonDriverC = RainbowHat.createButtonCInputDriver(
                KeyEvent.KEYCODE_C      // keyCode to send
            )
            buttonDriverA.register()
            buttonDriverB.register()
            buttonDriverC.register()


            topDisplay.displayMessage("PLAY")
            led.turn(Led.ID.ALL, true)
            led.launch(Led.ID.STRIP)
            colorneeded = led.stripColor

            Handler().postDelayed({
                topDisplay.displayMessage("   3")
                Handler().postDelayed({
                    topDisplay.displayMessage("   2")
                    Handler().postDelayed({
                        topDisplay.displayMessage("   1")
                        Handler().postDelayed({
                            topDisplay.displayMessage("   ")
                            led.Allblack()
                        }, 300)
                    }, 300)
                }, 300)
            }, 300)


        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == KeyEvent.KEYCODE_A) {
            if (index < 0) {
            }
            led.turn(Led.ID.RED, false)
            colorgive[index] = Color.RED
            led.UpdateLed(colorgive)
        }
        if (keyCode == KeyEvent.KEYCODE_B) {
            led.turn(Led.ID.GREEN, false)
            colorgive[index] = Color.GREEN
            led.UpdateLed(colorgive)
        }
        if (keyCode == KeyEvent.KEYCODE_C) {
            led.turn(Led.ID.BLUE, false)
            colorgive[index] = Color.BLUE
            led.UpdateLed(colorgive)
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_A) {
            if (index < 7) {
                led.turn(Led.ID.RED, true)
            }
        }
        if (keyCode == KeyEvent.KEYCODE_B) {
            if (index < 7) {
                led.turn(Led.ID.GREEN, true)
            }
        }
        if (keyCode == KeyEvent.KEYCODE_C) {
            if (index < 7) {
                led.turn(Led.ID.BLUE, true)
            }
        }
        if (colorgive[index] != colorneeded[index]) {
            topDisplay.displayMessage("LOSE")
            piezo.lose()
            led.UpdateLed(colorneeded)
        }
        index--
        if (index < 0) {
            topDisplay.displayMessage("WIN")
            led.blink(Led.ID.STRIP, 5000)
            piezo.win()
        }
        return super.onKeyUp(keyCode, event)
    }


    override fun onStop() {
        super.onStop()
        led.clean()
        buttons.clean()
        piezo.clean()
        temperatureSensor.clean()
        topDisplay.clean()
    }


    private fun runSafeIO(ioOperation: () -> Unit) {
        try {
            ioOperation()
        } catch (error: IOException) {
            Log.e(error.toString(), "IO Error")
        }
    }


}
