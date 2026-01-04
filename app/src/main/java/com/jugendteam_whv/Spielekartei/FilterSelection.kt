package com.jugendteam_whv.Spielekartei

data class FilterSelection(
    var noMaterial: Boolean = false,
    var ageFilter: Boolean = false,
    var sizeFilter: Boolean = false,
    var age: Int = 10,
    var size: Int = 10,
    var category: Category = Category.ALL,
    var bookmark: Boolean = false
    ){
    fun resetFilter(){
        noMaterial = false
        ageFilter = false
        sizeFilter = false
        age = 10
        size = 10
        category = Category.ALL
        bookmark = false
    }
}

