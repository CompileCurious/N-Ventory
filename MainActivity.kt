package com.example.inventory

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InventoryAdapter
    private val inventoryList = mutableListOf<InventoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = InventoryAdapter(inventoryList, ::onEdit, ::onDelete)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            showEditDialog()
        }

        loadCSV()
    }

    private fun loadCSV() {
        inventoryList.clear()
        try {
            val file = File(filesDir, "inventory.csv")
            val input: InputStream = if (file.exists()) file.inputStream() else assets.open("inventory.csv")
            input.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    val cols = line.split(",")
                    if (cols.size >= 2) {
                        inventoryList.add(InventoryItem(cols[0].trim(), cols[1].trim().toIntOrNull() ?: 0))
                    }
                }
            }
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to load CSV: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveCSV() {
        try {
            val file = File(filesDir, "inventory.csv")
            file.printWriter().use { out ->
                inventoryList.forEach { item ->
                    out.println("${item.name},${item.quantity}")
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to save CSV: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun showEditDialog(item: InventoryItem? = null, position: Int = -1) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_item, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.editName)
        val qtyInput = dialogView.findViewById<EditText>(R.id.editQty)

        if (item != null) {
            nameInput.setText(item.name)
            qtyInput.setText(item.quantity.toString())
        }

        AlertDialog.Builder(this)
            .setTitle(if (item == null) "Add Item" else "Edit Item")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val name = nameInput.text.toString().trim()
                val qty = qtyInput.text.toString().toIntOrNull() ?: 0
                if (name.isNotEmpty()) {
                    if (item == null) {
                        inventoryList.add(InventoryItem(name, qty))
                    } else if (position >= 0) {
                        inventoryList[position] = InventoryItem(name, qty)
                    }
                    adapter.notifyDataSetChanged()
                    saveCSV()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun onEdit(position: Int) {
        showEditDialog(inventoryList[position], position)
    }

    private fun onDelete(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete Item")
            .setMessage("Are you sure?")
            .setPositiveButton("Delete") { _, _ ->
                inventoryList.removeAt(position)
                adapter.notifyDataSetChanged()
                saveCSV()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
