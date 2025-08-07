void CNVentoryAppUi::SplitItemL(const InventoryItem& original, TInt moveQty, const TDesC& newContainer, const TDesC& newParent) {
    if (moveQty <= 0 || moveQty >= original.quantity) return;

    InventoryItem updated = original;
    updated.quantity -= moveQty;

    InventoryItem moved = original;
    moved.quantity = moveQty;
    moved.container.Copy(newContainer);
    moved.parentContainer.Copy(newParent);

    iInventoryManager->UpdateItem(originalIndex, updated);
    iInventoryManager->AddItem(moved);
    iInventoryManager->SaveInventoryL();
    iTransactionLog->LogAction(_L("Split"), moved);
}
