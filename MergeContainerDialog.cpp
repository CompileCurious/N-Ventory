void CNVentoryAppUi::MergeContainersL(const TDesC& sourceA, const TDesC& sourceB, const TDesC& target, const TDesC& newParent) {
    RArray<InventoryItem> merged;
    for (TInt i = 0; i < items.Count(); ++i) {
        InventoryItem item = items[i];
        if (item.container.CompareF(sourceA) == 0 || item.container.CompareF(sourceB) == 0) {
            TBool found = EFalse;
            for (TInt j = 0; j < merged.Count(); ++j) {
                if (merged[j].name.CompareF(item.name) == 0) {
                    merged[j].quantity += item.quantity;
                    found = ETrue; break;
                }
            }
            if (!found) {
                item.container.Copy(target);
                item.parentContainer.Copy(newParent);
                merged.AppendL(item);
            }
            iInventoryManager->DeleteItem(i); i--;
        }
    }
    for (TInt i = 0; i < merged.Count(); ++i) {
        iInventoryManager->AddItem(merged[i]);
        iTransactionLog->LogAction(_L("Merge"), merged[i]);
    }
    iInventoryManager->SaveInventoryL();
}
