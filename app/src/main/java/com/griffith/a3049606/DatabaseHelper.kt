
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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

}