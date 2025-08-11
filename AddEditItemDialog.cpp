#include "AddEditItemDialog.h"

// Replace these with your actual dialog control IDs
#define EDlgFieldCategory 1
#define EDlgFieldSubcategory 2
#define EDlgFieldParentContainer 3
#define EDlgFieldContainer 4
#define EDlgFieldName 5
#define EDlgFieldQuantity 6
// ... expiry fields as needed

CAddEditItemDialog::CAddEditItemDialog() {}

CAddEditItemDialog::CAddEditItemDialog(const InventoryItem& existing)
    : iItem(existing) {}

void CAddEditItemDialog::PreLayoutDynInitL() {
    SetEdwinTextL(EDlgFieldCategory, iItem.category);
    SetEdwinTextL(EDlgFieldSubcategory, iItem.subcategory); // NEW
    SetEdwinTextL(EDlgFieldParentContainer, iItem.parentContainer);
    SetEdwinTextL(EDlgFieldContainer, iItem.container);
    SetEdwinTextL(EDlgFieldName, iItem.name);
    SetEdwinValueL(EDlgFieldQuantity, iItem.quantity);
    // ... set expiry fields if you have them ...
}

TBool CAddEditItemDialog::OkToExitL(TInt aButtonId) {
    if (aButtonId == EAknSoftkeyOk) {
        GetEdwinText(EDlgFieldCategory, iItem.category);
        GetEdwinText(EDlgFieldSubcategory, iItem.subcategory); // NEW
        GetEdwinText(EDlgFieldParentContainer, iItem.parentContainer);
        GetEdwinText(EDlgFieldContainer, iItem.container);
        GetEdwinText(EDlgFieldName, iItem.name);
        iItem.quantity = GetEdwinValue(EDlgFieldQuantity);
        // ... get expiry fields if you have them ...
        return ETrue;
    }
    return EFalse;
}
