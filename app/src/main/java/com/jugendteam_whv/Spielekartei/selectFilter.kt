package com.jugendteam_whv.Spielekartei

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class selectFilter : AppCompatActivity() {
    private lateinit var materialsSwitch: Switch
    private lateinit var ageSwitch: Switch
    private lateinit var siceSwitch: Switch
    private lateinit var resetButton: Button
    var gameStore: GameStore = GameStore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_select_filter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findWidgets()
        setValuesFromGameStore()
        setListener()


    }

    private fun findWidgets(){
        materialsSwitch = findViewById(R.id.switch_material)
        resetButton = findViewById(R.id.filterReset)
        ageSwitch = findViewById(R.id.switch_age)
        siceSwitch = findViewById(R.id.switch_sice)
    }

    private fun setValuesFromGameStore() {
        materialsSwitch.isChecked = gameStore.filterSelection.noMaterial

    }

    private fun setListener() {
        materialsSwitch.setOnClickListener {
            gameStore.filterSelection.noMaterial = materialsSwitch.isChecked
            Log.d("Filter selection", "MaterialsSwitch is "+materialsSwitch.isChecked.toString())
        }

        resetButton.setOnClickListener {
            Log.d("Filter selection", "Filter are reset")
            gameStore.filterSelection.resetFilter()
            setValuesFromGameStore()
        }
    }


    
}