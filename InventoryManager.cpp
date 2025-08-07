void CInventoryManager::LoadInventoryL() {
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
        if (line.Find(_L8("Category")) == 0) continue;

        InventoryItem item;
        TLex8 lineLex(line); TPtrC8 token;

        token.Set(lineLex.NextToken()); item.category.Copy(token);
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
}
