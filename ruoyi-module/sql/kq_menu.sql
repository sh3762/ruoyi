-- 菜单 SQL
-- 1. 考勤管理目录
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2003, '考勤管理', 0, 10, 'kq', null, 1, 0, 'M', '0', '0', '', 'timer', 'admin', sysdate(), '', null, '考勤管理目录');

-- 2. 考勤面板菜单
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2004, '考勤面板', 2003, 1, 'board', 'kq/index', 1, 0, 'C', '0', '0', 'kq:view', 'dashboard', 'admin', sysdate(), '', null, '考勤面板');
