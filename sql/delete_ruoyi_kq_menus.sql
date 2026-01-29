-- 删除若依官网菜单 (ID 4)
DELETE FROM sys_menu WHERE menu_id = 4;
DELETE FROM sys_menu WHERE menu_name = '若依官网';

-- 删除考勤管理菜单及其子菜单
-- 1. 删除考勤面板 (ID 2004)
DELETE FROM sys_menu WHERE menu_name = '考勤面板';
-- 2. 删除考勤管理 (ID 2003)
DELETE FROM sys_menu WHERE menu_name = '考勤管理';
-- 3. 清理任何包含“考勤”的残留菜单
DELETE FROM sys_menu WHERE menu_name LIKE '%考勤%';
