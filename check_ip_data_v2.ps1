$connString = "Server=10.33.1.16,1433;Database=rtdb;User Id=user;Password=user123;"
$conn = New-Object System.Data.SqlClient.SqlConnection
$conn.ConnectionString = $connString

try {
    $conn.Open()
    
    $cmd = $conn.CreateCommand()
    $ip = "10.113.53.16"
    
    # 1. Check if IP exists in hw_arp directly
    $cmd.CommandText = "SELECT count(*) FROM hw_arp WHERE ip LIKE '%" + $ip + "%'"
    $count = $cmd.ExecuteScalar()
    Write-Host "Count in hw_arp for like $ip : $count"
    
    # 2. Check exact match
    $cmd.CommandText = "SELECT count(*) FROM hw_arp WHERE ip = '" + $ip + "'"
    $countExact = $cmd.ExecuteScalar()
    Write-Host "Count in hw_arp for = $ip : $countExact"

    # 3. Check Join Query
    $cmd.CommandText = "SELECT a.ip, a.mac, c.swip, b.note FROM hw_arp a LEFT JOIN HW_IP b ON a.ip = b.ip LEFT JOIN hw_mac c ON a.mac = c.mac WHERE a.ip LIKE '%" + $ip + "%'"
    $adapter = New-Object System.Data.SqlClient.SqlDataAdapter $cmd
    $dataset = New-Object System.Data.DataSet
    $adapter.Fill($dataset)
    
    if ($dataset.Tables[0].Rows.Count -gt 0) {
        Write-Host "Join Query returned data:"
        foreach ($row in $dataset.Tables[0].Rows) {
            Write-Host "IP: $($row['ip']), MAC: $($row['mac']), SWIP: $($row['swip']), NOTE: $($row['note'])"
        }
    } else {
        Write-Host "Join Query returned NO data"
    }

    $conn.Close()
} catch {
    Write-Host "Error: $_"
}
