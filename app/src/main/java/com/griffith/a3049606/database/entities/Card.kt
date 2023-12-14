package com.griffith.a3049606.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val deckId: Int,
    val frontText: String,
    val backText: String
)