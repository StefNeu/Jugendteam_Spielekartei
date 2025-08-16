package com.jugendteam_whv.Spielekartei

enum class Category {
    GROUP_ALLOCATION, GET_TO_KNOW, CIRCLE_GAME, SINGING_GAME, MOVEMENT_GAME, TERRAIN_GAME, TRUST_GAME, PRAYERS, IMPETUS, PUZZLE, CARD_GAMES, DEFAULT;
    override fun toString(): String {
        return when (this) {
            GROUP_ALLOCATION -> "Gruppeneinteilung"
            GET_TO_KNOW -> "Kennenlernen"
            CIRCLE_GAME -> "Kreisspiel"
            SINGING_GAME -> "Singespiel"
            MOVEMENT_GAME -> "Bewegungsspiel"
            TERRAIN_GAME -> "Geländespiel"
            TRUST_GAME -> "Vertrauensspiel"
            PRAYERS -> "Gebete"
            IMPETUS -> "Impuls"
            PUZZLE -> "Rätsel"
            CARD_GAMES -> "Kartenspiele"
            DEFAULT -> "Standard"
        }
    }


}