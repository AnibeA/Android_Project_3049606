package com.griffith.a3049606.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.griffith.a3049606.database.dao.DeckCardDao
import com.griffith.a3049606.database.entities.Deck
import com.griffith.a3049606.database.entities.Card

@Database(entities = [Deck::class, Card::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deckCardDao(): DeckCardDao
    //DAOs, define them here as abstract methods
}
