package com.jugendteam_whv.Spielekartei

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.jugendteam_whv.Spielekartei.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var gameListView: ListView
    private lateinit var filterButton: ImageButton
    private lateinit var randomGameButton : ImageButton
    private lateinit var gameListAdapter: CustomGameAdapter
    private lateinit var gameStore: GameStore
    private lateinit var searchbar: SearchView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val appContext = applicationContext
        gameStore = GameStore.getInstance()
        try {
            gameStore.loadGames(appContext)
        } catch (e: Exception) {
            Log.e("MainActivity","Error in GameStore.loadingGames", e)
        }
        searchbar = findViewById(R.id.searchbar)
        filterButton = findViewById(R.id.filterButton)
        gameListView = findViewById(R.id.gameList)
        randomGameButton = findViewById(R.id.randomGameButton)
        
        try {
            val gamesForAdapter = ArrayList(gameStore.filteredGameList.map { it as Game? })
            gameListAdapter = CustomGameAdapter(this, gamesForAdapter)
        } catch (e: Exception) {
            Log.e("MainActivity", "Error creating CustomGameAdapter in onCreate", e)
            gameListAdapter = CustomGameAdapter(this, ArrayList())
        }

        gameListView.adapter = gameListAdapter


        gameListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            try {
                gameStore.selectedGame = gameListAdapter.getItem(position)
                Log.d("MainActivity", "Game selected: ${gameStore.selectedGame?.name}")
                val intent = Intent(this, GamesDetails::class.java)
                startActivity(intent)
            } catch (e: java.lang.IndexOutOfBoundsException) {
                Log.e("MainActivity", "Error selecting a game.", e)
            }
        }

        searchbar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                gameListAdapter.filter.filter(p0)
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                gameListAdapter.filter.filter(p0)
                return false
            }
        })

        filterButton.setOnClickListener {
            Log.d("MainActivity", "Click on filterButton")
            val intent = Intent(this, SelectFilter::class.java)
            startActivity(intent)
        }

        randomGameButton.setOnClickListener {
            Log.d("MainActivity", "Click on the Random Game Button.")
            try {
                if (gameListAdapter.count > 0) {
                    gameStore.selectedGame =
                        gameListAdapter.getItem((0 until gameListAdapter.count).random())
                    val intent = Intent(this, GamesDetails::class.java)
                    startActivity(intent)
                } else {
                    Log.w("MainActivity", "Random game selection attempted on empty or uninitialized adapter.")
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error in random choosing a game.", e)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        gameStore.filterGameList()
        try {
            val gamesForAdapter = ArrayList(gameStore.filteredGameList.map { it as Game? })
            gameListAdapter = CustomGameAdapter(this, gamesForAdapter)
        } catch (e: Exception) {
            Log.e("MainActivity", "Error creating CustomGameAdapter in onResume", e)
            gameListAdapter = CustomGameAdapter(this, ArrayList())
        }

        gameListAdapter.notifyDataSetChanged()
        gameListView.adapter = gameListAdapter
    }
}