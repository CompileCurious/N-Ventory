struct InventoryItem {
    TBuf<16> category;
    TBuf<16> subcategory; // New: Subcategory field
    TBuf<16> parentContainer;
    TBuf<16> container;
    TBuf<32> name;
    TInt quantity;
    TUint8 expiryMonth;
    TUint8 expiryYear;
    TBool hasExpiry;

    TBool IsExpired(const TDateTime& today) const {
        if (!hasExpiry) return EFalse;
        TInt currentMM = today.Month() + 1;
        TInt currentYY = today.Year() % 100;
        return (expiryYear < currentYY) ||
               (expiryYear == currentYY && expiryMonth < currentMM);
    }

    TBool IsNearExpiry(const TDateTime& today) const {
        if (!hasExpiry) return EFalse;
        TInt currentMM = today.Month() + 1;
        TInt currentYY = today.Year() % 100;
        TInt deltaYY = expiryYear - currentYY;
        TInt deltaMM = expiryMonth - currentMM + (deltaYY * 12);
        return (deltaMM >= 0 && deltaMM <= 3);
    }
};
