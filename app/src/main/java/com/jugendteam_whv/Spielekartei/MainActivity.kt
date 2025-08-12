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
    private lateinit var gameList: ArrayList<Game>
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
        } catch (e: Throwable) {
            Log.e("MainActivity","Error in GameStore.lodingGames", e)
        }

        gameListView = findViewById(R.id.gameList)
        gameList = gameStore.gamesList
        gameListAdapter = CustomGameAdapter(this, gameList as ArrayList<Game?>?)
        gameListView.adapter = gameListAdapter

        searchbar = findViewById(R.id.searchbar)
        filterButton = findViewById(R.id.filterButton)

        gameListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedGame = gameList[position]
            gameStore.selectedGame = gameStore.gamesList.get(position)
            Log.d("MainActivity", "Spiel ausgewählt: ${selectedGame.name}")
            val intent: Intent = Intent(this, games_details::class.java)
            startActivity(intent)

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
            val intent: Intent = Intent(this, selectFilter::class.java)
            startActivity(intent)
        }
    }


}