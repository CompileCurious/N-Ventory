void CTransactionLog::LogAction(const TDesC& action, const InventoryItem& item) {
    TransactionEntry entry;
    entry.timestamp.HomeTime();
    entry.action.Copy(action);
    entry.item = item;

    if (iEntries.Count() >= 100) iEntries.Remove(0);
    iEntries.Append(entry);
}
