package com.jugendteam_whv.Spielekartei

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class GamesDatabase {
    private var context: Context
    private var db: SQLiteDatabase

    constructor(context: Context) {
        this.context = context
        db = SQLiteDatabase.openOrCreateDatabase(context.getDatabasePath("games_database.sqlite").path, null)
        Log.d("GamesDatabase", "Die Datenbank wurde geladen.")
    }

    public fun getAllGames() {
        val sqlStatmment = "SELECT * FROM games"
        val cursor = db.rawQuery(sqlStatmment, null)
    }


}
