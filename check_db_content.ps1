$connString = "Server=10.33.1.16,1433;Database=rtdb;User Id=user;Password=user123;"
$conn = New-Object System.Data.SqlClient.SqlConnection
$conn.ConnectionString = $connString
try {
    Write-Host "Connecting..."
    $conn.Open()
    Write-Host "Connection Successful"
    
    $cmd = $conn.CreateCommand()
    $cmd.CommandText = "SELECT count(*) FROM hw_arp"
    try {
        $count = $cmd.ExecuteScalar()
        Write-Host "hw_arp count: $count"
    } catch {
        Write-Host "Error querying hw_arp: $_"
    }

    $cmd.CommandText = "SELECT count(*) FROM HW_IP"
    try {
        $count = $cmd.ExecuteScalar()
        Write-Host "HW_IP count: $count"
    } catch {
        Write-Host "Error querying HW_IP: $_"
    }
    
    $conn.Close()
} catch {
    Write-Host "Connection Error: $_"
}
