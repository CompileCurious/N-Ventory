package com.example.n_ventory

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        nameInput = findViewById(R.id.nameInput)
        quantityInput = findViewById(R.id.quantityInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        saveButton = findViewById(R.id.saveButton)

        db = openOrCreateDatabase("inventory.db", MODE_PRIVATE, null)

        saveButton.setOnClickListener {
            val name = nameInput.text.toString()
            val quantity = quantityInput.text.toString().toIntOrNull() ?: 0
            val description = descriptionInput.text.toString()

            val values = ContentValues().apply {
                put("name", name)
                put("quantity", quantity)
                put("description", description)
            }
            db.insert("items", null, values)

            val logValues = ContentValues().apply {
                put("action", "ADD")
                put("item_name", name)
                put("quantity", quantity)
                put("description", description)
            }
            db.insert("transaction_log", null, logValues)

            finish()
        }
    }
}
