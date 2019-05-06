package com.monentreprise.myapplication.rainbow

import android.os.Handler
import com.google.android.things.contrib.driver.pwmspeaker.Speaker
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat

class Piezo {

    enum class Note(val frequency: Double) {
        E5(659.255), DO(659.255),
        D5(587.33), RE(587.33),
        C5(523.251), MI(523.251),
        B4(493.883), FA(493.883),
        A4(440.0), SOL(440.0),
        G4(391.995), LA(391.995),
        F4(349.228), SI(349.228),
        E4(329.628), DO2(329.628),
        NOTE_B0(31.00), NOTE_CS1(35.00),
        NOTE_D1(37.00), NOTE_DS1(39.00),
        NOTE_E1(41.00), NOTE_F1(44.00),
        NOTE_FS1(46.00), NOTE_G1(49.00),
        NOTE_GS1(52.00), NOTE_A1(55.00),
        NOTE_AS1(58.00), NOTE_B1(62.00),
        NOTE_C2(65.00), NOTE_CS2(69.00),
        NOTE_D2(73.00), NOTE_DS2(78.00),
        NOTE_E2(82.00), NOTE_F2(87.00),
        NOTE_FS2(93.00), NOTE_G2(98.00),
        NOTE_GS2(104.00), NOTE_A2(110.00),
        NOTE_AS2(117.00), NOTE_B2(123.00),
        NOTE_C3(131.00), NOTE_CS3(139.00),
        NOTE_D3(147.00), NOTE_DS3(156.00),
        NOTE_E3(165.00), NOTE_F3(175.00),
        NOTE_FS3(185.00), NOTE_G3(196.00),
        NOTE_GS3(208.00), NOTE_A3(220.00),
        NOTE_AS3(233.00), NOTE_B3(247.00),
        NOTE_C4(262.00), NOTE_CS4(277.00),
        NOTE_D4(294.00), NOTE_DS4(311.00),
        NOTE_E4(330.00), NOTE_F4(349.00),
        NOTE_FS4(370.00), NOTE_G4(392.00),
        NOTE_GS4(415.00), NOTE_A4(440.00),
        NOTE_AS(455.00), NOTE_AS4(466.00),
        NOTE_B4(494.00), NOTE_C5(523.00),
        NOTE_CS5(554.00), NOTE_D5(587.00),
        NOTE_DS5(622.00), NOTE_E5(659.00),
        NOTE_F5(698.00), NOTE_FS5(740.00),
        NOTE_G5(784.00), NOTE_GSH(830.00),
        NOTE_GS5(831.00), NOTE_A5(880.00),
        NOTE_AS5(932.00), NOTE_B5(988.00),
        NOTE_C6(1047.00), NOTE_CS6(1109.00),
        NOTE_D6(1175.00), NOTE_DS6(1245.00),
        NOTE_E6(1319.00), NOTE_F6(1397.00),
        NOTE_FS6(1480.00), NOTE_G6(1568.00),
        NOTE_GS6(1661.00), NOTE_A6(1760.00),
        NOTE_AS6(1865.00), NOTE_B6(1976.00),
        NOTE_C7(2093.00), NOTE_CS7(2217.00),
        NOTE_D7(2349.00), NOTE_DS7(2489.00),
        NOTE_E7(2637.00), NOTE_F7(2794.00),
        NOTE_FS7(2960.00), NOTE_G7(3136.00),
        NOTE_GS7(3322.00), NOTE_A7(3520.00),
        NOTE_AS7(3729.00), NOTE_B7(3951.00),
        NOTE_C8(4186.00), NOTE_CS8(4435.00),
        NOTE_D8(4699.00), NOTE_DS8(4978.00),
    }

    private val noteHandler: Handler = Handler()
    private var durationMs: Long = 1000
    private var piezo: Speaker? = null

    //Play note until stop is called
    fun play(note: Note) {
        if (piezo == null) {
            piezo = RainbowHat.openPiezo()
        }

        piezo?.let {
            it.play(note.frequency)
        }
    }
    
    

    fun win() {
        playmusic(Piezo.Note.NOTE_B5, 107)
        delay(214)
        playmusic(Piezo.Note.NOTE_B5, 107)
        playmusic(Piezo.Note.NOTE_B5, 107)
        playmusic(Piezo.Note.NOTE_B5, 856)
        delay(107)
        playmusic(Piezo.Note.NOTE_C6, 107)
        delay(53)
        playmusic(Piezo.Note.NOTE_B5, 107)
        delay(53)
        playmusic(Piezo.Note.NOTE_C6, 107)
        delay(53)
        playmusic(Piezo.Note.NOTE_B5, 107)
        delay(53)
        playmusic(Piezo.Note.NOTE_C6, 107)
        delay(53)
        playmusic(Piezo.Note.NOTE_B5, 107)
        delay(214)
        playmusic(Piezo.Note.NOTE_B5, 107)
        playmusic(Piezo.Note.NOTE_B5, 107)
        playmusic(Piezo.Note.NOTE_B5, 107)
        delay(53)
        playmusic(Piezo.Note.NOTE_B5, 107)
        delay(53)
        playmusic(Piezo.Note.NOTE_B5, 107)
        delay(53)
        playmusic(Piezo.Note.NOTE_B5, 107)
        delay(53)
        playmusic(Piezo.Note.NOTE_B5, 107)
        delay(53)
        playmusic(Piezo.Note.NOTE_B5, 107)
        delay(53)
        playmusic(Piezo.Note.NOTE_B5, 107)
        delay(53)
        playmusic(Piezo.Note.NOTE_GS5, 107)
        delay(53)
        playmusic(Piezo.Note.NOTE_A5, 107)
        delay(53)
        playmusic(Piezo.Note.NOTE_B5, 107)
        delay(214)
        playmusic(Piezo.Note.NOTE_B5, 107)
        playmusic(Piezo.Note.NOTE_B5, 107)
        playmusic(Piezo.Note.NOTE_B5, 856)
    }

    fun lose() {
        playmusic(Note.NOTE_A5, 1000)
        delay(100)
        playmusic(Note.NOTE_A4, 1000)
        delay(100)
        playmusic(Note.NOTE_A3, 1000)
        delay(100)
        playmusic(Note.NOTE_A2, 2000)
        delay(100)
    }

    fun delay(durationMs: Long) {
        this.durationMs = durationMs
        piezo?.stop()
        Thread.sleep(durationMs)
    }

    /**
     * Play for the given duration
     */
    fun play(note: Note, durationMs: Long) {
        this.durationMs = durationMs

        play(note)
        noteHandler.postDelayed(noteRunnable, durationMs)
    }
    
    fun playmusic(note: Note, durationMs: Long) {
        this.durationMs = durationMs

        play(note)
        noteHandler.postDelayed(noteRunnable, durationMs)
        Thread.sleep(durationMs)
    }

    fun stop() {
        piezo?.stop()
    }

    fun clean() {
        noteHandler.removeCallbacks(noteRunnable)
        piezo?.stop()
        piezo?.close()
    }

    private val noteRunnable = Runnable { stop() }
}