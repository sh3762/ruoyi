  服务器地址 10.33.1.16 
- 用户名 : user
- 密码 : user123
- 库名 : rtdb
# SQL Server 2000 交互兼容规则
本文件定义了生成与 SQL Server 2000 交互的连接指令和 T-SQL 语句时必须遵循的兼容规则，所有生成的代码需确保可直接在 SQL Server 2000 环境中执行，无语法或特性冲突。

## 1. 连接字符串格式
必须使用 OLE DB 兼容格式，示例：
Provider=SQLOLEDB;Data Source = 10.33.1.16；Initial Catalog = ；User ID = 用户名；Password = 密码；

## 2. 语法特性限制
- 禁止使用 SQL Server 2005 及以后的新特性：
  - 禁用 CTE（`WITH` 子句）、`PIVOT/UNPIVOT`
  - 禁用 `TRY_CAST`/`TRY_CONVERT`、`VARCHAR(MAX)` 类型
- 日期函数仅允许使用旧版支持的函数：
  - 允许：`GETDATE()`、`DATEADD`、`DATEDIFF`
  - 禁用：`SYSDATETIME`、`DATEFROMPARTS` 等 2005+ 新增日期函数
- 字符串类型规范：
  - 优先使用 `VARCHAR(8000)`（最大长度 8000）
  - 大文本使用 `TEXT` 类型时需严格遵循 SQL Server 2000 语法
- JOIN/子查询规范：
  - 保持 ANSI 标准格式
  - 避免复杂嵌套导致的执行异常

## 3. 函数与对象兼容
- 存储过程、触发器的创建语法必须符合 SQL Server 2000 规范
- 参数定义和执行逻辑禁止依赖 SQL Server 2005+ 版本特性

以下是测试成功的链接字段
# 定义连接字符串 (遵循 SQLServer2000.md 规范)
$connString = "Provider=SQLOLEDB;Data Source=10.33.1.16;Initial Catalog=rtdb;User ID=user;Password=user123;"

# 创建 OLE DB 连接对象
$conn = New-Object System.Data.OleDb.OleDbConnection($connString)

try {
    Write-Host "正在尝试连接到 10.33.1.16 (SQL Server 2000)..."
    $conn.Open()
    Write-Host "✅ 连接成功！(Connection Successful!)" -ForegroundColor Green
    # 可选：打印服务器版本以确认
    $cmd = $conn.CreateCommand()
    $cmd.CommandText = "SELECT @@VERSION"
    $version = $cmd.ExecuteScalar()
    Write-Host "服务器版本: $version" -ForegroundColor Gray
    $conn.Close()
} catch {
    Write-Host "❌ 连接失败 (Connection Failed):" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
}
