package com.griffith.a3049606.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.griffith.a3049606.database.dao.DeckCardDao
import com.griffith.a3049606.database.entities.Deck
import com.griffith.a3049606.database.entities.Card

// Define the database class that extends RoomDatabase.
//  the class is a Room Database with a table  of Deck and Card.
@Database(entities = [Deck::class, Card::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // Define an abstract function that returns the DAO.
    abstract fun deckCardDao(): DeckCardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // getDatabase returns the singleton. It'll create the database the first time it's accessed,
        // using Room's database builder to create a RoomDatabase object in the application context from the AppDatabase class.
        fun getDatabase(context: Context): AppDatabase {
            // If the INSTANCE is not null, then return it; if it is, then create the database.
            return INSTANCE ?: synchronized(this) {
                // Create the database here.
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Flipcard_Database" // The name of the database.
                ).build()
                INSTANCE = instance
                instance // Return the newly created database
            }
        }
    }
}
