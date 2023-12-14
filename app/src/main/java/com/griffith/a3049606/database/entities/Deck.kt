package com.griffith.a3049606.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Deck(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)