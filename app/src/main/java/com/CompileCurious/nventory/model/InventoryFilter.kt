package com.compilecurious.nventory.model

import java.time.LocalDate

data class InventoryFilter(
    val category: String? = null,
    val minQuantity: Int? = null,
    val maxQuantity: Int? = null,
    val dateAddedAfter: LocalDate? = null,
    val dateAddedBefore: LocalDate? = null
)
