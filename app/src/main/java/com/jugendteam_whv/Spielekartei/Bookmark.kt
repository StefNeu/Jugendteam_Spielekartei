package com.jugendteam_whv.Spielekartei

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class Bookmark(private val context: Context) {

    private val file = File(context.filesDir, "bookmark.json")
    private val gson = Gson()
    private val type = object : TypeToken<List<String>>() {}.type

    fun load(): MutableList<String> {
        if (!file.exists()) return mutableListOf()
        val text = file.readText()
        if (text.isBlank()) return mutableListOf()
        return gson.fromJson<List<String>>(text, type).toMutableList()
    }
    fun save(list: List<String>) {
        file.writeText(gson.toJson(list))
    }
    fun add(game: String) {
        val list = load()
        if (!list.contains(game)) {
            list.add(game)
            save(list)
        }
    }
    fun remove(game: String) {
        val list = load()
        val changed = list.removeAll { it == game }
        if (changed) save(list)
    }
    fun getBookmarkList(): List<String> {
        return load()
    }
    fun contains(gameName: String): Boolean {
        val list = load()
        return list.contains(gameName)
    }
}