package com.jugendteam_whv.Spielekartei

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.enums.enumEntries


class selectFilter : AppCompatActivity() {
    private lateinit var materialsSwitch: Switch
    private lateinit var ageSwitch: Switch
    private lateinit var siceSwitch: Switch
    private lateinit var ageSeekBar: SeekBar
    private lateinit var sizeSeekBar: SeekBar
    private lateinit var ageNumber: TextView
    private lateinit var sizeNumber: TextView
    private lateinit var resetButton: Button
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var backButton : ImageButton
    private lateinit var categorySelecter : Spinner
    private var categoryList : MutableList<String> = Category.values().map { it.toString() } as MutableList<String>


    var gameStore: GameStore = GameStore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        categoryList.add(0,"Alle ")
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


        var categoryAdapter : ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryList)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySelecter.adapter = categoryAdapter
    }


    private fun findWidgets(){
        materialsSwitch = findViewById(R.id.switch_material)
        resetButton = findViewById(R.id.filterReset)
        ageSwitch = findViewById(R.id.switch_age)
        siceSwitch = findViewById(R.id.switch_sice)
        ageSeekBar = findViewById(R.id.seekBarAge)
        sizeSeekBar = findViewById(R.id.seekBarSize)
        ageNumber = findViewById(R.id.numerAge)
        sizeNumber = findViewById(R.id.numberSize)
        toolbar = findViewById(R.id.toolbarFilter)
        backButton = findViewById(R.id.back_button)
        categorySelecter = findViewById(R.id.categpry_selecter)
    }

    private fun setValuesFromGameStore() {
        materialsSwitch.isChecked = gameStore.filterSelection.noMaterial
        ageSwitch.isChecked = gameStore.filterSelection.ageFilter
        siceSwitch.isChecked = gameStore.filterSelection.sizeFilter
        ageSeekBar.progress = gameStore.filterSelection.age
        sizeSeekBar.progress = gameStore.filterSelection.size
        ageNumber.text = gameStore.filterSelection.age.toString()
        sizeNumber.text = gameStore.filterSelection.size.toString()

    }

    private fun setListener() {
        materialsSwitch.setOnClickListener {
            gameStore.filterSelection.noMaterial = materialsSwitch.isChecked
            Log.d("Filter selection", "MaterialsSwitch is "+materialsSwitch.isChecked.toString())
        }

        ageSwitch.setOnClickListener {
            gameStore.filterSelection.ageFilter = ageSwitch.isChecked
            Log.d("Filter selection", "MaterialsSwitch is "+ageSwitch.isChecked.toString())
        }

        siceSwitch.setOnClickListener {
            gameStore.filterSelection.sizeFilter = siceSwitch.isChecked
            Log.d("Filter selection", "MaterialsSwitch is "+ siceSwitch.isChecked.toString())
        }

        resetButton.setOnClickListener {
            Log.d("Filter selection", "Filter are reset")
            gameStore.filterSelection.resetFilter()
            setValuesFromGameStore()
        }

        ageSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                p0: SeekBar?,
                p1: Int,
                p2: Boolean
            ) {
                ageNumber.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                gameStore.filterSelection.age = p0?.progress!!
                ageNumber.text = gameStore.filterSelection.age.toString()
            }

        })
        sizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                p0: SeekBar?,
                p1: Int,
                p2: Boolean
            ) {
                sizeNumber.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                gameStore.filterSelection.size = p0?.progress!!
                sizeNumber.text = gameStore.filterSelection.size.toString()
            }

        })

        backButton.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    
}