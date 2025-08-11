package com.example.n_ventory

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity

class TransactionLogActivity : AppCompatActivity() {

    private lateinit var logList: ListView
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_log)

        logList = findViewById(R.id.logList)
        db = openOrCreateDatabase("inventory.db", MODE_PRIVATE, null)

        val cursor = db.rawQuery(
            "SELECT id AS _id, action, item_name, timestamp FROM transaction_log ORDER BY timestamp DESC",
            null
        )

        val adapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_2,
            cursor,
            arrayOf("action", "timestamp"),
            intArrayOf(android.R.id.text1, android.R.id.text2),
            0
        )
        logList.adapter = adapter
    }
}
