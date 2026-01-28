$connString = "Provider=SQLOLEDB;Data Source=10.33.1.16;Initial Catalog=rtdb;User ID=user;Password=user123;"
$conn = New-Object System.Data.OleDb.OleDbConnection($connString)

function Get-TableInfo ($tableName) {
    $cmd = $conn.CreateCommand()
    $cmd.CommandText = "SELECT TOP 1 * FROM $tableName"
    try {
        $adapter = New-Object System.Data.OleDb.OleDbDataAdapter($cmd)
        $dt = New-Object System.Data.DataTable
        $adapter.Fill($dt) | Out-Null
        
        Write-Host "Table: $tableName" -ForegroundColor Cyan
        if ($dt.Columns.Count -gt 0) {
            foreach ($col in $dt.Columns) {
                Write-Host "  - $($col.ColumnName) ($($col.DataType.Name))"
            }
        } else {
            Write-Host "  (Empty or No Columns)"
        }
        Write-Host ""
    } catch {
        Write-Host "Error querying $tableName : $($_.Exception.Message)" -ForegroundColor Red
    }
}

try {
    $conn.Open()
    Get-TableInfo "HW_IP"
    Get-TableInfo "hw_arp"
    Get-TableInfo "hw_mac"
    Get-TableInfo "hw_swcfg"
} catch {
    Write-Host "Connection Failed: $($_.Exception.Message)" -ForegroundColor Red
} finally {
    if ($conn.State -eq 'Open') { $conn.Close() }
}
