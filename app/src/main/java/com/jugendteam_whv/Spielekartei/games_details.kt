package com.jugendteam_whv.Spielekartei

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class games_details : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var categoryFild: TextView
    private lateinit var sizeFild: TextView
    private lateinit var ageFild: TextView
    private lateinit var materialFild: TextView
    private lateinit var descriptionFild: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_games_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var gameStore: GameStore = GameStore.getInstance()
        var game: Game = gameStore.selectedGame!!
        Log.d("Games Details","Game details for "+game.name )
        toolbar = findViewById(R.id.toolbar)
        categoryFild = findViewById(R.id.category)
        sizeFild = findViewById(R.id.size)
        ageFild = findViewById(R.id.age)
        materialFild = findViewById(R.id.material)
        descriptionFild = findViewById(R.id.description)
        toolbar.setTitle(game.name)
        categoryFild.setText(game.category.toString())
        var groupSizeString: String = game.groupSizeMin.toString() + " - " + game.groupSizeMax.toString()
        sizeFild.setText(groupSizeString)
        ageFild.setText(game.age.toString())
        materialFild.setText(game.material)
        descriptionFild.setText(game.description)
        Log.d("Game details", "Game details loudet.")
    }
}