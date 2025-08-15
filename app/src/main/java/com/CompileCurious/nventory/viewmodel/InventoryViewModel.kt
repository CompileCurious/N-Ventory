fun getFilteredItems(filter: InventoryFilter): List<InventoryItem> {
    return inventoryList.filter { item ->
        (filter.category == null || item.category == filter.category) &&
        (filter.minQuantity == null || item.quantity >= filter.minQuantity) &&
        (filter.maxQuantity == null || item.quantity <= filter.maxQuantity) &&
        (filter.dateAddedAfter == null || item.dateAdded.isAfter(filter.dateAddedAfter)) &&
        (filter.dateAddedBefore == null || item.dateAdded.isBefore(filter.dateAddedBefore))
    }
}

fun exportToCSV(context: Context, filteredItems: List<InventoryItem>): File {
    val fileName = "inventory_report_${System.currentTimeMillis()}.csv"
    val file = File(context.getExternalFilesDir(null), fileName)

    file.printWriter().use { out ->
        out.println("Name,Category,Quantity,Date Added")
        filteredItems.forEach { item ->
            out.println("${item.name},${item.category},${item.quantity},${item.dateAdded}")
        }
    }

    return file
}
