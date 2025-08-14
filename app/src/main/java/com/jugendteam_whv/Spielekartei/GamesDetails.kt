package com.jugendteam_whv.Spielekartei

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GamesDetails : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var categoryFild: TextView
    private lateinit var sizeFild: TextView
    private lateinit var ageFild: TextView
    private lateinit var materialFild: TextView
    private lateinit var descriptionFild: TextView

    private lateinit var category_ : TextView
    private lateinit var size_ : TextView
    private lateinit var age_ : TextView
    private lateinit var material_ : TextView

    private lateinit var shareButton : ImageButton
    private var gameStore: GameStore = GameStore.getInstance()
    private var game: Game = gameStore.selectedGame!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_games_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("Games Details","Game details for "+game.name )
        initializeVariablesForUi()
        setText()
        Log.d("Game details", "Game details loudet.")
        setOnClick()
        when{
            game.category == Category.PRAYERS -> prayersUiChanges()
            else -> null
        }

    }

    fun initializeVariablesForUi() {
        toolbar = findViewById(R.id.toolbar)
        categoryFild = findViewById(R.id.category)
        sizeFild = findViewById(R.id.size)
        ageFild = findViewById(R.id.age)
        materialFild = findViewById(R.id.material)
        descriptionFild = findViewById(R.id.description)
        category_ = findViewById(R.id.category_)
        size_ = findViewById(R.id.size_)
        age_ = findViewById(R.id.age_)
        material_ = findViewById(R.id.material_)
        shareButton = findViewById(R.id.share_button)
    }

    fun setText() {
        toolbar.setTitle(game.name)
        categoryFild.setText(game.category.toString())
        var groupSizeString: String = game.groupSizeMin.toString() + " - " + game.groupSizeMax.toString()
        sizeFild.setText(groupSizeString)
        ageFild.setText(game.age.toString())
        materialFild.setText(game.material)
        descriptionFild.setText(game.description)
    }

    fun prayersUiChanges() {
        size_.visibility = View.GONE
        sizeFild.visibility = View.GONE
        age_.visibility = View.GONE
        ageFild.visibility = View.GONE
        material_.visibility = View.GONE
        materialFild.visibility = View.GONE
    }

    fun setOnClick() {
        shareButton.setOnClickListener {
            Log.d("Games Details", "Share Button pressed.")
            val intent : Intent = Intent()
            var shareText : String = ""
            when{
                game.category == Category.PRAYERS -> shareText  = game.description
                else -> shareText  = game.name + "\n \n"+
                    getString(R.string.category_string) + ": " + game.category + "\n"+
                    getString(R.string.age_string) + ": " + game.age.toString() +  "\n"+
                    getString(R.string.size_string) + ": " + game.groupSizeMin.toString() + " - "  + game.groupSizeMin.toString() + "\n\n"+
                    getString(R.string.material_string) + ":\n" + game.material + "\n\n" +
                    getString(R.string.description_string) + ":\n" + game.description
            }

            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, shareText)
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, R.string.share.toString()))


        }
    }
}