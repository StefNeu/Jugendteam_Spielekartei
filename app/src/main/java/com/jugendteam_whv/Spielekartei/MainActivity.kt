package com.jugendteam_whv.Spielekartei

import android.os.Bundle
import android.util.Log
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
    private lateinit var gameAdaptzer: ArrayAdapter<Game>
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

        game

    }


}