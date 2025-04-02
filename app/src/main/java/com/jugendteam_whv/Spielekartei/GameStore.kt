package com.jugendteam_whv.Spielekartei


import org.xml.sax.InputSource
import java.io.File
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class GameStore {
    val file = File("raw/games.xml")
    private lateinit var games: ArrayList<Game>

    /**
     * Loads the games from the XML file
     * Overwrites the Games Array
     */
    fun loadGames() {
        games = ArrayList()
        try {
            val dbFactory = DocumentBuilderFactory.newInstance()
            val dBuilder = dbFactory.newDocumentBuilder()
            val xmlInput = InputSource(StringReader(file.readText()))
            val doc = dBuilder.parse(xmlInput)

        } catch (e: Throwable) {
            println(e)
        }

    }

}