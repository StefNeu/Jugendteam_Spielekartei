package com.jugendteam_whv.Spielekartei

data class FilterSelection(
    var noMaterial: Boolean = false,
    var ageFilter: Boolean = false,
    var siceFilter: Boolean = false
    ){
    fun resetFilter(){
        noMaterial = false
        ageFilter = false
        siceFilter = false
    }
}

