package com.example.n_ventory.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

object BackupUtils {

    fun backupDatabase(context: Context) {
        try {
            val dbFile = context.getDatabasePath("inventory.db")
            val documentsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

            if (!documentsDir.exists()) {
                documentsDir.mkdirs()
            }

            val timestamp = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())
            val backupFile = File(documentsDir, "inventory_backup_$timestamp.db")

            FileInputStream(dbFile).use { input ->
                FileOutputStream(backupFile).use { output ->
                    input.copyTo(output)
                }
            }

            Log.d("BackupUtils", "Database backed up to: ${backupFile.absolutePath}")
        } catch (e: Exception) {
            Log.e("BackupUtils", "Backup failed: ${e.message}")
        }
    }
}
