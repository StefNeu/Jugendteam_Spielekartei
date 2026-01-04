package com.jugendteam_whv.Spielekartei

import android.content.Context
import android.util.Log
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class GameStore private constructor() {
    companion object {
        @Volatile private var instance: GameStore? = null

        fun getInstance() =
            instance ?: synchronized(this) { instance ?: GameStore().also { instance = it } }
    }
    private var gamesList: ArrayList<Game> = ArrayList() // Explizit ArrayList
    var filteredGameList: MutableList<Game> = mutableListOf<Game>()
    var selectedGame: Game? = null
    var filterSelection: FilterSelection = FilterSelection()

    lateinit var bookamrkStore: Bookmark

    fun loadGames(appContext: Context) {
        gamesList.clear() // Vorherige Spiele löschen, falls vorhanden
        var doc: Document? = null
        try {
            val inputStream = appContext.resources.openRawResource(R.raw.games)
            val dbFactory = DocumentBuilderFactory.newInstance()
            val dBuilder = dbFactory.newDocumentBuilder()
            val xmlInput = InputSource(StringReader(inputStream.bufferedReader().use { it.readText() }))
            doc = dBuilder.parse(xmlInput)
            Log.i("GameStore", "Games file loaded")
        } catch (e: Throwable) {
            Log.e("GameStore", "Error loading games XML", e)
            filteredGameList.clear()
            return
        }

        bookamrkStore = Bookmark(context = appContext)

        val rootElement = doc?.documentElement
        val gameElements = rootElement?.getElementsByTagName("row")
        if (gameElements != null) {
            for (i in 0 until gameElements.length) {
                val gameElement = gameElements.item(i) as Element
                val name = (gameElement.getElementsByTagName("name").item(0) as Element).textContent ?: ""
                val category: Category = categoryStringToEnum((gameElement.getElementsByTagName("category").item(0) as Element).textContent ?: "")
                // Verwende toIntOrNull für sicherere Konvertierung
                val sizeMin: Int = (gameElement.getElementsByTagName("size_min").item(0) as Element).textContent.toIntOrNull() ?: 0
                val sizeMax: Int = (gameElement.getElementsByTagName("size_max").item(0) as Element).textContent.toIntOrNull() ?: Int.MAX_VALUE
                val age: Int = (gameElement.getElementsByTagName("age").item(0) as Element).textContent.toIntOrNull() ?: 0
                val material: String = (gameElement.getElementsByTagName("material").item(0) as Element).textContent ?: ""
                val description: String = (gameElement.getElementsByTagName("description").item(0) as Element).textContent ?: ""

                val game = Game(name, category, sizeMin, sizeMax, age, material, description)
                gamesList.add(game)
            }
        }
        // Initialisiere filteredGameList mit allen Spielen nach dem Laden
        filteredGameList = gamesList.toMutableList()
        Log.i("GameStore", "${gamesList.size} games loaded. filteredGameList initialized with ${filteredGameList.size} games.")
    }

    fun categoryStringToEnum(category: String): Category {
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
        // Starte die Filterung immer von einer (schreibgeschützten) Kopie der ursprünglichen gamesList
        var currentFilteredList: List<Game> = gamesList.toList()
        if (filterSelection.bookmark) {
            currentFilteredList = currentFilteredList.filter { game -> bookamrkStore.contains(game.name) }
            Log.d("GameStore", "Filtered for bookmark. Count: ${currentFilteredList.size}")
        }
        if (filterSelection.noMaterial) {
            currentFilteredList = currentFilteredList.filter { game -> game.material == "Kein Material nötig" }
            Log.d("GameStore", "Filtered for no material. Count: ${currentFilteredList.size}")
        }
        if (filterSelection.ageFilter) {
            currentFilteredList = currentFilteredList.filter { game -> game.age <= filterSelection.age }
            Log.d("GameStore", "Filtered for age <= ${filterSelection.age}. Count: ${currentFilteredList.size}")
        }
        if (filterSelection.sizeFilter) {
            currentFilteredList = currentFilteredList.filter { game -> game.groupSizeMin <= filterSelection.size && game.groupSizeMax >= filterSelection.size }
            Log.d("GameStore", "Filtered for size around ${filterSelection.size}. Count: ${currentFilteredList.size}")
        }
        if (filterSelection.category != Category.ALL) { // Annahme: Category.ALL existiert
            currentFilteredList = currentFilteredList.filter { game -> game.category == filterSelection.category }
            Log.d("GameStore", "Filtered for category ${filterSelection.category}. Count: ${currentFilteredList.size}")
        }
        
        // Aktualisiere die Haupt-filteredGameList mit dem Ergebnis
        filteredGameList = currentFilteredList.toMutableList()
        Log.i("GameStore", "Final number of items in filteredGameList: ${filteredGameList.size}")

    }
}