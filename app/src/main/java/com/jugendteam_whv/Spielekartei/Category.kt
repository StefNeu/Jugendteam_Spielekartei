package com.jugendteam_whv.Spielekartei

import android.content.Context

enum class Category {
    ALL ,GROUP_ALLOCATION, GET_TO_KNOW, CIRCLE_GAME, SINGING_GAME, MOVEMENT_GAME, TERRAIN_GAME, TRUST_GAME, PRAYERS, IMPETUS, PUZZLE, CARD_GAMES, DEFAULT;
    override fun toString(): String {
        return when (this) {
            ALL -> "Alle"
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

    fun getDisplayName(context: Context) : String {
        return when (this) {
            ALL -> context.getString(R.string.category_all)
            GROUP_ALLOCATION -> context.getString(R.string.category_group_allocation)
            GET_TO_KNOW -> context.getString(R.string.category_get_to_know)
            CIRCLE_GAME -> context.getString(R.string.category_circle_game)
            SINGING_GAME -> context.getString(R.string.category_singing_game)
            MOVEMENT_GAME -> context.getString(R.string.category_movement_game)
            TERRAIN_GAME -> context.getString(R.string.category_terain_game)
            TRUST_GAME -> context.getString(R.string.category_trust_game)
            PRAYERS -> context.getString(R.string.category_prayers)
            IMPETUS -> context.getString(R.string.category_impetus)
            PUZZLE -> context.getString(R.string.category_puzzle)
            CARD_GAMES -> context.getString(R.string.category_card_games)
            DEFAULT -> context.getString(R.string.category_default)
        }
    }


}