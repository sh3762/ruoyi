$connString = "Server=10.33.1.16,1433;Database=rtdb;User Id=user;Password=user123;"
$conn = New-Object System.Data.SqlClient.SqlConnection
$conn.ConnectionString = $connString

try {
    $conn.Open()
    $cmd = $conn.CreateCommand()
    
    # Check exact match
    $cmd.CommandText = "SELECT count(*) FROM hw_arp WHERE ip LIKE '%10.113.53.16%'"
    $count = $cmd.ExecuteScalar()
    Write-Host "Count: $count"

    if ($count -gt 0) {
        $cmd.CommandText = "SELECT TOP 5 ip FROM hw_arp WHERE ip LIKE '%10.113.53.16%'"
        $reader = $cmd.ExecuteReader()
        while ($reader.Read()) {
            $val = $reader["ip"]
            Write-Host "Found IP: '$val'"
        }
        $reader.Close()
    }
    
    $conn.Close()
} catch {
    Write-Host "Error: $_"
}
