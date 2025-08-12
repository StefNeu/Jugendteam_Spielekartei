package com.jugendteam_whv.Spielekartei

data class FilterSelection(
    var noMaterial: Boolean = false,
    var ageFilter: Boolean = false,
    var sizeFilter: Boolean = false,
    var age: Int = 10,
    var size: Int = 10
    ){
    fun resetFilter(){
        noMaterial = false
        ageFilter = false
        sizeFilter = false
    }
}

