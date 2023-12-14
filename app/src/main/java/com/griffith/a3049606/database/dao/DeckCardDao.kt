package com.griffith.a3049606.database.dao

import androidx.room.*
import com.griffith.a3049606.database.entities.Deck
import com.griffith.a3049606.database.entities.Card
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckCardDao {
    // Insert a new deck
    @Insert
    suspend fun insertDeck(deck: Deck): Long

    // Insert a new card
    @Insert
    suspend fun insertCard(card: Card)

    // Get all decks
    @Query("SELECT * FROM Deck")
    fun getAllDecks(): Flow<List<Deck>>

    // Get cards for a specific deck
    @Query("SELECT * FROM Card WHERE deckId = :deckId")
    fun getCardsForDeck(deckId: Int): Flow<List<Card>>

    // Additional methods for updating and deleting can be defined here
}
