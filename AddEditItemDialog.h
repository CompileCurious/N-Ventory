#ifndef ADD_EDIT_ITEM_DIALOG_H
#define ADD_EDIT_ITEM_DIALOG_H

#include <aknform.h>
#include "Inventory item.h"

class CAddEditItemDialog : public CAknDialog {
public:
    InventoryItem iItem;
    // Constructor for Add
    CAddEditItemDialog();
    // Constructor for Edit
    CAddEditItemDialog(const InventoryItem& existing);
    TBool OkToExitL(TInt aButtonId);
protected:
    void PreLayoutDynInitL();
};

#endif // ADD_EDIT_ITEM_DIALOG_H
