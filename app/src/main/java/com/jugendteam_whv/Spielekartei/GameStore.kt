package com.jugendteam_whv.Spielekartei


import android.content.Context
import android.util.Log
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class GameStore(private val appContext: Context) {
    lateinit var gamesList: ArrayList<Game>

    /**
     * Loads the games from the XML file
     * Overwrites the Games Array
     */
    fun loadGames() {
        gamesList = ArrayList()
        var doc: Document? = null
        try {

            val inputStram = appContext.resources.openRawResource(R.raw.games)
            val dbFactory = DocumentBuilderFactory.newInstance()
            val dBuilder = dbFactory.newDocumentBuilder()
            val xmlInput = InputSource(StringReader(inputStram.bufferedReader().use { it.readText() }))
            doc = dBuilder.parse(xmlInput)

            Log.i("GameStore", "Games file loaded")
            Log.i("GameStore", "Name of the root: "+ doc.documentElement.tagName)


        } catch (e: Throwable) {
            Log.e("GameStore", "Error loading games", e)
        }

        val rootElement = doc?.documentElement
        val gameElements = rootElement?.getElementsByTagName("row")
        for (i in 0 until gameElements!!.length) {
            val gameElement = gameElements.item(i) as Element
            val name = (gameElement.getElementsByTagName("name").item(0) as Element).textContent ?: ""
            val category: Category = categoryStringToEnum( (gameElement.getElementsByTagName("category").item(0) as Element).textContent ?: "")
            val sizeMin: Int = (gameElement.getElementsByTagName("size_min").item(0) as Element).textContent.toInt() ?: 0
            val sizeMax: Int = (gameElement.getElementsByTagName("size_max").item(0) as Element).textContent.toInt() ?: 0
            val age: Int = (gameElement.getElementsByTagName("age").item(0) as Element).textContent.toInt() ?: 0
            val material: String = (gameElement.getElementsByTagName("material").item(0) as Element).textContent ?: ""
            val description: String = (gameElement.getElementsByTagName("description").item(0) as Element).textContent ?: ""

            val game = Game(name, category, sizeMin, sizeMax, age, material, description)
            gamesList.add(game)


            Log.i("GameStore", "Game Nr.: "+ i + " = "+ name)
        }


    }

    fun categoryStringToEnum(category: String): Category{
        when{
            category.equals("GROUP_ALLOCATION") -> return Category.GROUP_ALLOCATION
            category.equals("GET_TO_KNOW") -> return Category.GET_TO_KNOW
            category.equals("CIRCLE_GAME") -> return Category.CIRCLE_GAME
            category.equals("SINGING_GAME") -> return Category.SINGING_GAME
            category.equals("MOVEMENT_GAME") -> return Category.MOVEMENT_GAME
            category.equals("TERRAIN_GAME") -> return Category.TERRAIN_GAME
            category.equals("TRUST_GAME") -> return Category.TRUST_GAME
            category.equals("PRAYERS") -> return Category.PRAYERS
            category.equals("IMPETUS") -> return Category.IMPETUS
            category.equals("PUZZLE") -> return Category.PUZZLE
            else -> return Category.DEFAULT
        }
    }

}