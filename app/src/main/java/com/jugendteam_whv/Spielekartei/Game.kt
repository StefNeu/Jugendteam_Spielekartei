package com.jugendteam_whv.Spielekartei

data class Game(var name: String, var category: Category, var groupSizeMin: Int,var groupSizeMax: Int, val age: Int, var material: String, var description: String
) {
    override fun toString(): String {
        return name
    }

}