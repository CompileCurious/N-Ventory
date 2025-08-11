package com.example.inventory

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InventoryAdapter
    private val inventoryList = mutableListOf<InventoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        adapter = InventoryAdapter(inventoryList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        loadCSV()

        // TODO: Add FAB button for adding, handle editing/deleting in adapter
    }

    private fun loadCSV() {
        try {
            val input = assets.open("inventory.csv")
            input.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val cols = line.split(",")
                    if (cols.size >= 2) {
                        inventoryList.add(
                            InventoryItem(
                                name = cols[0].trim(),
                                quantity = cols[1].trim().toIntOrNull() ?: 0
                            )
                        )
                    }
                }
            }
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to load CSV: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
