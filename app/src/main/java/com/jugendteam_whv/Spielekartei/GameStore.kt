package com.jugendteam_whv.Spielekartei


import android.content.Context
import android.util.Log
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class GameStore private constructor() {
    companion object{
        @Volatile private var instance: GameStore? = null

        fun getInstance() =
            instance ?: synchronized(this) { instance ?: GameStore().also { instance = it } }
    }
    private lateinit var gamesList: ArrayList<Game>
    var filteredGameList = mutableListOf<Game>()
    var selectedGame: Game? = null
    var filterSelection: FilterSelection = FilterSelection()

    /**
     * Loads the games from the XML file
     * Overwrites the Games Array
     */
    fun loadGames(appContext: Context) {
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


            Log.i("GameStore", "Game Nr.: "+ i + " = "+ name +" loadet.")

            filteredGameList = gamesList
        }


    }

    fun categoryStringToEnum(category: String): Category{
        return when{
            category.equals("GROUP_ALLOCATION") -> Category.GROUP_ALLOCATION
            category.equals("GET_TO_KNOW") -> Category.GET_TO_KNOW
            category.equals("CIRCLE_GAME") -> Category.CIRCLE_GAME
            category.equals("SINGING_GAME") -> Category.SINGING_GAME
            category.equals("MOVEMENT_GAME") -> Category.MOVEMENT_GAME
            category.equals("TERRAIN_GAME") -> Category.TERRAIN_GAME
            category.equals("TRUST_GAME") -> Category.TRUST_GAME
            category.equals("PRAYERS") -> Category.PRAYERS
            category.equals("IMPETUS") -> Category.IMPETUS
            category.equals("PUZZLE") -> Category.PUZZLE
            category.equals("CARD_GAMES") -> Category.CARD_GAMES
            else -> Category.DEFAULT
        }
    }

    fun filterGameList() {
        filteredGameList = gamesList
        if (filterSelection.noMaterial) {
           filteredGameList = filteredGameList.filter { game -> game.material.equals("Kein Material nötig") } as ArrayList<Game>
            Log.d("GameStore", "filterdGameList is filtered to exclude Games with need of materials.")
        }
        if (filterSelection.ageFilter) {
            filteredGameList = filteredGameList.filter { game -> game.age <= filterSelection.age } as ArrayList<Game>
            Log.d("GameStore", "filterdGameList is filtered to exclude Games with a to big age.")
        }
        if (filterSelection.sizeFilter) {
            filteredGameList = filteredGameList.filter { game -> game.groupSizeMin <= filterSelection.size && game.groupSizeMax >= filterSelection.size } as ArrayList<Game>
            Log.d("GameStore", "filterdGameList is filtered to exclude Games with an wrong groupSize.")
        }
        Log.i("GameStore", "Number of items in filteredGameList: "+ filteredGameList.size)
    }


}