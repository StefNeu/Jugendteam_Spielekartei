package com.jugendteam_whv.Spielekartei

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.jugendteam_whv.Spielekartei.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var gameListView: ListView
    private lateinit var filterButton: ImageButton
    private lateinit var gameList: ArrayList<Game>
    private lateinit var gameListAdapter: ArrayAdapter<Game>
    private lateinit var gameStore: GameStore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val appContext = applicationContext
        gameStore = GameStore(appContext)
        try {
            gameStore.loadGames()
        } catch (e: Throwable) {
            Log.e("MAIN","Error in GameStore.lodingGames", e)
        }

        gameListView = findViewById(R.id.gameList)
        gameList = gameStore.gamesList
        gameListAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, gameList)
        gameListView.adapter = gameListAdapter

        gameListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
    val selectedGame = gameList[position]
    Log.d("MainActivity", "Spiel ausgewählt: ${selectedGame.name}")
}

    }


}