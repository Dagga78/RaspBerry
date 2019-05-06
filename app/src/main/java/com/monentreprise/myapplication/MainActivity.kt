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
import kotlin.random.Random
import java.nio.file.Files.size
import java.nio.file.Files.size


class MainActivity : Activity() {

    val led = Led()
    val buttons = HatButton()
    val piezo = Piezo()
    val temperatureSensor = TemperatureSensor()
    val topDisplay = TopDisplay()
    var colorgive = IntArray(7)
    var colorhard = IntArray(7)
    var colorhard2 = IntArray(7)
    lateinit var colorneeded: IntArray
    var index: Int = 6
    var finishgame: Boolean = false
    var DificultChoice: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val manager = PeripheralManager.getInstance()
        Log.d("test", "Available GPIO: " + manager.gpioList)

        runSafeIO {

            //          --------------------------------------------------
//                       Instanciation des boutons
//          --------------------------------------------------
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


            choiceDifficult()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//          --------------------------------------------------
//                          Jeux Lancée
//          --------------------------------------------------
        if (!finishgame && DificultChoice) {
            if (keyCode == KeyEvent.KEYCODE_A) {
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
        }
//          --------------------------------------------------
//                          Choix difficulté
//          --------------------------------------------------
        else if (!finishgame && !DificultChoice) {

            if (keyCode == KeyEvent.KEYCODE_A) {
                led.turn(Led.ID.RED, false)
            }
            if (keyCode == KeyEvent.KEYCODE_B) {
                led.turn(Led.ID.GREEN, false)
            }
            if (keyCode == KeyEvent.KEYCODE_C) {
                led.turn(Led.ID.BLUE, false)
            }

        }

//          --------------------------------------------------
//                          Jeux Terminé
//          --------------------------------------------------
        else {
            if (keyCode == KeyEvent.KEYCODE_A || keyCode == KeyEvent.KEYCODE_B || keyCode == KeyEvent.KEYCODE_C) {
                choiceDifficult()
            }
        }


        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
//          --------------------------------------------------
//                          Jeux Lancée
//          --------------------------------------------------
        if (!finishgame && DificultChoice) {
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
                finishgame = true
            }
            if (index == 0 && !finishgame) {
                topDisplay.displayMessage("WIN")
                led.UpdateLed(colorneeded)
                piezo.win()
                finishgame = true
            }
            index--
        }
//          --------------------------------------------------
//                          Choix difficulté
//          --------------------------------------------------
        else if (!finishgame && !DificultChoice) {

            if (keyCode == KeyEvent.KEYCODE_A) {
                led.turn(Led.ID.RED, true)
                DificultChoice = true
                newGameHard()
            }

            if (keyCode == KeyEvent.KEYCODE_B) {
                led.turn(Led.ID.GREEN, true)

            }
            if (keyCode == KeyEvent.KEYCODE_C) {
                led.turn(Led.ID.BLUE, true)
                DificultChoice = true
                newGameEasy()
            }
        }
//          --------------------------------------------------
//                          Jeux Terminé
//          --------------------------------------------------
        else {
            if (keyCode == KeyEvent.KEYCODE_A || keyCode == KeyEvent.KEYCODE_B || keyCode == KeyEvent.KEYCODE_C) {
                finishgame = false
            }
        }
        return super.onKeyUp(keyCode, event)
    }

    fun newGameEasy() {
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
                        index = 6
                        colorgive = intArrayOf(
                            Color.BLACK,
                            Color.BLACK,
                            Color.BLACK,
                            Color.BLACK,
                            Color.BLACK,
                            Color.BLACK,
                            Color.BLACK
                        )
                    }, 300)
                }, 300)
            }, 300)
        }, 300)
    }

    fun choiceDifficult() {
        topDisplay.displayMessage("H  E")
        DificultChoice = false
        index = 6
    }


    fun newGameHard() {
        topDisplay.displayMessage("PLAY")
        led.turn(Led.ID.ALL, true)
        led.launchHard()

        val max = 7
        val indices = ArrayList<Int>(max)
        for (c in 0 until max) {
            indices.add(c)
        }

        for (c in 0 until max) {
            colorhard2 = intArrayOf(
                Color.BLACK,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK
            )
            val arrIndex = Random.nextInt(indices.size)
            topDisplay.displayMessage("   " + indices.size)
            val randomIndex = indices[arrIndex]
            indices.remove(randomIndex)
            colorhard2[randomIndex] = led.randomColor()
            colorhard[randomIndex] = colorhard2[randomIndex]
            led.UpdateLed(colorhard2)
            Thread.sleep(700)
        }
        topDisplay.displayMessage(" GO ")
        colorneeded = colorhard
        led.Allblack()
        colorgive = intArrayOf(
            Color.BLACK,
            Color.BLACK,
            Color.BLACK,
            Color.BLACK,
            Color.BLACK,
            Color.BLACK,
            Color.BLACK
        )
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
