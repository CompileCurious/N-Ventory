package com.example.n_ventory

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddItemActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var quantityInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var saveButton: Button
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        // Initialize views
        nameInput = findViewById(R.id.nameInput)
        quantityInput = findViewById(R.id.quantityInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        saveButton = findViewById(R.id.saveButton)

        // Use a helper class for DB access (recommended)
        db = openOrCreateDatabase("inventory.db", MODE_PRIVATE, null)
        createTablesIfNeeded(db)

        saveButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val quantity = quantityInput.text.toString().toIntOrNull()
            val description = descriptionInput.text.toString().trim()

            if (name.isEmpty() || quantity == null) {
                Toast.makeText(this, "Please enter valid name and quantity", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val itemValues = ContentValues().apply {
                put("name", name)
                put("quantity", quantity)
                put("description", description)
            }

            val itemInserted = db.insert("items", null, itemValues)
            if (itemInserted != -1L) {
                val logValues = ContentValues().apply {
                    put("action", "ADD")
                    put("item_name", name)
                    put("quantity", quantity)
                    put("description", description)
                }
                db.insert("transaction_log", null, logValues)
                Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createTablesIfNeeded(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS items (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                quantity INTEGER NOT NULL,
                description TEXT
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE IF NOT EXISTS transaction_log (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                action TEXT NOT NULL,
                item_name TEXT NOT NULL,
                quantity INTEGER,
                description TEXT,
                timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
            )
        """.trimIndent())
    }
}
