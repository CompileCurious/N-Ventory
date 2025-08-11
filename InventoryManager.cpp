// CSV Reading: update your function that loads inventory items
iItems.Reset();
RFs fs; fs.Connect(); CleanupClosePushL(fs);
RFile file; if (file.Open(fs, KInventoryPath, EFileRead) != KErrNone) {
    CleanupStack::PopAndDestroy(); return;
}
CleanupClosePushL(file);

TBuf8<512> buffer; file.Read(buffer);
TLex8 lex(buffer);
while (!lex.Eos()) {
    TPtrC8 line = lex.NextToken();
    if (line.Find(_L8("Category")) == 0) continue; // Skip header

    InventoryItem item;
    TLex8 lineLex(line); TPtrC8 token;

    token.Set(lineLex.NextToken()); item.category.Copy(token);
    token.Set(lineLex.NextToken()); item.subcategory.Copy(token); // NEW: Subcategory
    token.Set(lineLex.NextToken()); item.parentContainer.Copy(token);
    token.Set(lineLex.NextToken()); item.container.Copy(token);
    token.Set(lineLex.NextToken()); item.name.Copy(token);
    token.Set(lineLex.NextToken()); TLex8(token).Val(item.quantity);
    token.Set(lineLex.NextToken());
    if (token.Length() == 4) {
        TLex8 expiryLex(token); TInt mm, yy;
        expiryLex.Val(mm); expiryLex.Skip(2); expiryLex.Val(yy);
        item.expiryMonth = mm; item.expiryYear = yy; item.hasExpiry = ETrue;
    } else item.hasExpiry = EFalse;

    iItems.Append(item);
}

CleanupStack::PopAndDestroy(2); // file, fs

// CSV Writing: when saving inventory
_LIT8(KCsvHeader, "Category,Subcategory,ParentContainer,Container,Name,Quantity,Expiry\r\n");
file.Write(KCsvHeader);

_LIT8(KCsvFormat, "%S,%S,%S,%S,%S,%d,%02d%02d\r\n");
for (TInt i = 0; i < iItems.Count(); ++i) {
    const InventoryItem& item = iItems[i];
    TBuf8<128> line;
    if (item.hasExpiry) {
        line.Format(KCsvFormat,
            &item.category,
            &item.subcategory, // NEW: Save subcategory
            &item.parentContainer,
            &item.container,
            &item.name,
            item.quantity,
            item.expiryMonth,
            item.expiryYear
        );
    } else {
        line.Format(KCsvFormat,
            &item.category,
            &item.subcategory, // NEW: Save subcategory
            &item.parentContainer,
            &item.container,
            &item.name,
            item.quantity,
            0, 0 // No expiry
        );
    }
    file.Write(line);
}
