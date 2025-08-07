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
