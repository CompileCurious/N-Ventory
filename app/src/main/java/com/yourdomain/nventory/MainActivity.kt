package com.example.n_ventory

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var db: SQLiteDatabase
    private lateinit var itemList: ListView
    private lateinit var items: ArrayList<String>
    private lateinit var itemIds: ArrayList<Int>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemList = findViewById(R.id.itemList)
        val addButton: Button = findViewById(R.id.addButton)
        val logButton: Button = findViewById(R.id.logButton)

        try {
            db = openOrCreateDatabase("inventory.db", MODE_PRIVATE, null)

            db.execSQL("""
                CREATE TABLE IF NOT EXISTS items (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT,
                    quantity INTEGER,
                    description TEXT
                )
            """.trimIndent())

            db.execSQL("""
                CREATE TABLE IF NOT EXISTS transaction_log (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    action TEXT,
                    item_name TEXT,
                    quantity INTEGER,
                    description TEXT,
                    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
                )
            """.trimIndent())
        } catch (e: Exception) {
            Log.e("MainActivity", "Database setup failed: ${e.message}")
        }

        loadItems()

        addButton.setOnClickListener {
            startActivity(Intent(this, AddItemActivity::class.java))
        }

        logButton.setOnClickListener {
            startActivity(Intent(this, TransactionLogActivity::class.java))
        }

        itemList.setOnItemLongClickListener { _, _, position, _ ->
            val itemId = itemIds[position]
            val itemName = items[position].substringBefore(" - ")

            // Show confirmation dialog
            AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete \"$itemName\" from inventory?")
                .setPositiveButton("Delete") { _, _ ->
                    deleteItem(itemId, itemName)
                }
                .setNegativeButton("Cancel", null)
                .show()

            true
        }
    }

    override fun onResume() {
        super.onResume()
        loadItems()
    }

    private fun loadItems() {
        items = ArrayList()
        itemIds = ArrayList()

        try {
            val cursor = db.rawQuery("SELECT * FROM items", null)
            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val quantity = cursor.getInt(2)
                val description = cursor.getString(3)
                items.add("$name - Qty: $quantity")
                itemIds.add(id)
            }
            cursor.close()

            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
            itemList.adapter = adapter
        } catch (e: Exception) {
            Log.e("MainActivity", "Failed to load items: ${e.message}")
        }
    }

    private fun deleteItem(itemId: Int, itemName: String) {
        try {
            val cursor = db.rawQuery(
                "SELECT quantity, description FROM items WHERE id = ?",
                arrayOf(itemId.toString())
            )

            var quantity = 0
            var description = ""

            if (cursor.moveToFirst()) {
                quantity = cursor.getInt(0)
                description = cursor.getString(1)
            }
            cursor.close()

            db.delete("items", "id = ?", arrayOf(itemId.toString()))

            val logValues = ContentValues().apply {
                put("action", "DELETE")
                put("item_name", itemName)
                put("quantity", quantity)
                put("description", description)
            }
            db.insert("transaction_log", null, logValues)

            loadItems()
        } catch (e: Exception) {
            Log.e("MainActivity", "Failed to delete item: ${e.message}")
        }
    }
}
