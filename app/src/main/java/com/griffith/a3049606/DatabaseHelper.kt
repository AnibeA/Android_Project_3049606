
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

// DatabaseHelper class for managing database creation and version management

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "flipcard_database.db"
        const val DATABASE_VERSION = 1

        // SQL statement to create the "decks" table

        private const val SQL_CREATE_DECKS = """
            CREATE TABLE decks (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT
            );
        """

        // SQL statement to create the "cards" table

        private const val SQL_CREATE_CARDS = """
            CREATE TABLE cards (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                deckId INTEGER,
                frontText TEXT,
                backText TEXT,
                FOREIGN KEY(deckId) REFERENCES decks(id)
            );
        """
    }

    // Called when the database is created for the first time

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_DECKS)
        db.execSQL(SQL_CREATE_CARDS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade if needed
    }

    // Function to insert a new deck
    fun insertDeck(name: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("name", name)
        }
        val result = db.insert("decks", null, contentValues)
        Log.d("DatabaseHelper", "insertDeck: Inserted deck with ID $result")
        return result
    }

    // Function to insert a new card
    fun insertCard(deckId: Long, frontText: String, backText: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("deckId", deckId)
            put("frontText", frontText)
            put("backText", backText)
        }
        db.insert("cards", null, contentValues)
    }

    fun getAllDecks(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM decks", null)
    }

    fun getCardsByDeckId(deckId: Long): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM cards WHERE deckId = ?", arrayOf(deckId.toString()))
    }
}

