$connString = "Server=10.33.1.16,1433;Database=rtdb;User Id=user;Password=user123;"
$conn = New-Object System.Data.SqlClient.SqlConnection
$conn.ConnectionString = $connString

try {
    $conn.Open()
    $cmd = $conn.CreateCommand()
    
    # Query to check VLAN/PORT data
    $cmd.CommandText = "SELECT TOP 5 a.ip, a.mac, a.vlan as arp_vlan, a.port as arp_port, c.vlan as mac_vlan, c.port as mac_port, c.swip FROM hw_arp a LEFT JOIN hw_mac c ON a.mac = c.mac WHERE a.ip LIKE '10.113.%'"
    
    $adapter = New-Object System.Data.SqlClient.SqlDataAdapter $cmd
    $dt = New-Object System.Data.DataTable
    $adapter.Fill($dt) | Out-Null
    
    if ($dt.Rows.Count -gt 0) {
        foreach ($row in $dt.Rows) {
            Write-Host "IP: $($row['ip']) | MAC: $($row['mac']) | ARP_VLAN: $($row['arp_vlan']) | ARP_PORT: $($row['arp_port']) | MAC_VLAN: $($row['mac_vlan']) | MAC_PORT: $($row['mac_port']) | SWIP: $($row['swip'])"
        }
    } else {
        Write-Host "No data found for 10.113.%"
    }
    
    $conn.Close()
} catch {
    Write-Host "Error: $($_.Exception.Message)"
}
