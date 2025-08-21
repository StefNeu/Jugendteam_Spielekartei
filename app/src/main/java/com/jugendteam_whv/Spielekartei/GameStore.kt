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

            val inputStream = appContext.resources.openRawResource(R.raw.games)
            val dbFactory = DocumentBuilderFactory.newInstance()
            val dBuilder = dbFactory.newDocumentBuilder()
            val xmlInput = InputSource(StringReader(inputStream.bufferedReader().use { it.readText() }))
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
            val sizeMin: Int = (gameElement.getElementsByTagName("size_min").item(0) as Element).textContent.toInt()
            val sizeMax: Int = (gameElement.getElementsByTagName("size_max").item(0) as Element).textContent.toInt()
            val age: Int = (gameElement.getElementsByTagName("age").item(0) as Element).textContent.toInt()
            val material: String = (gameElement.getElementsByTagName("material").item(0) as Element).textContent ?: ""
            val description: String = (gameElement.getElementsByTagName("description").item(0) as Element).textContent ?: ""

            val game = Game(name, category, sizeMin, sizeMax, age, material, description)
            gamesList.add(game)


            Log.i("GameStore", "Game Nr.: $i = $name loadet.")

            filteredGameList = gamesList
        }


    }

    fun categoryStringToEnum(category: String): Category{
        return when (category) {
            "GROUP_ALLOCATION" -> Category.GROUP_ALLOCATION
            "GET_TO_KNOW" -> Category.GET_TO_KNOW
            "CIRCLE_GAME" -> Category.CIRCLE_GAME
            "SINGING_GAME" -> Category.SINGING_GAME
            "MOVEMENT_GAME" -> Category.MOVEMENT_GAME
            "TERRAIN_GAME" -> Category.TERRAIN_GAME
            "TRUST_GAME" -> Category.TRUST_GAME
            "PRAYERS" -> Category.PRAYERS
            "IMPETUS" -> Category.IMPETUS
            "PUZZLE" -> Category.PUZZLE
            "CARD_GAMES" -> Category.CARD_GAMES
            else -> Category.DEFAULT
        }
    }

    fun filterGameList() {
        filteredGameList = gamesList
        if (filterSelection.noMaterial) {
           filteredGameList = filteredGameList.filter { game -> game.material == "Kein Material nötig" } as ArrayList<Game>
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
        if (filterSelection.category != Category.ALL) {
            filteredGameList = filteredGameList.filter { game -> game.category == filterSelection.category } as ArrayList<Game>
        }
        Log.i("GameStore", "Number of items in filteredGameList: "+ filteredGameList.size)
    }


}