import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "flipcard_database.db"
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_DECKS = """
            CREATE TABLE decks (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT
            );
        """

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

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_DECKS)
        db.execSQL(SQL_CREATE_CARDS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade if needed
    }

    // Methods to insert data, retrieve decks, and retrieve cards will go here
}
