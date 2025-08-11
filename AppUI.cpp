#include "AddEditItemDialog.h"
#include "InventoryManager.h"
#include "TransactionLog.h"

void CNVentoryAppUi::LaunchAddItemDialogL() {
    CAddEditItemDialog* dlg = new (ELeave) CAddEditItemDialog();
    if (dlg->ExecuteLD(R_ADD_EDIT_ITEM_DIALOG)) {
        iInventoryManager->AddItem(dlg->iItem);
        iInventoryManager->SaveInventoryL();
        iTransactionLog->LogAction(_L("Add"), dlg->iItem);
    }
}

void CNVentoryAppUi::LaunchEditItemDialogL() {
    InventoryItem& item = iInventoryManager->GetSelectedItem();
    CAddEditItemDialog* dlg = new (ELeave) CAddEditItemDialog(item);
    if (dlg->ExecuteLD(R_ADD_EDIT_ITEM_DIALOG)) {
        iInventoryManager->UpdateItem(item, dlg->iItem);
        iInventoryManager->SaveInventoryL();
        iTransactionLog->LogAction(_L("Edit"), dlg->iItem);
    }
}
void CNVentoryAppUi::HandleCommandL(TInt aCommand) {
    switch (aCommand) {
        case ECmdAddItem: LaunchAddItemDialogL(); break;
        case ECmdEditItem: LaunchEditItemDialogL(); break;
        case ECmdDeleteItem: ConfirmAndDeleteItemL(); break;
        case ECmdSplitContainer: LaunchSplitDialogL(); break;
        case ECmdMergeContainers: LaunchMergeDialogL(); break;
        case ECmdReportExpired: GenerateExpiredReportL(); break;
        case ECmdReportNearExpiry: GenerateNearExpiryReportL(); break;
        case ECmdReportContainerSummary: GenerateContainerSummaryReportL(); break;
        case ECmdReportTransactionHistory: ShowTransactionHistoryViewL(); break;
        case ECmdExportLastReport: ExportLastReportCsvL(); break;
        case EAknCmdExit: Exit(); break;
    }
}
