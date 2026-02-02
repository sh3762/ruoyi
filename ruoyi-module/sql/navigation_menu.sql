
-- ----------------------------
-- 菜单 SQL
-- ----------------------------
set @parentId = 2001;

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('综合导航', @parentId, 1, 'navigation', 'info/navigation/index', 1, 0, 'C', '0', '0', 'system:navigation:list', 'guide', 'admin', sysdate(), '', null, '综合导航菜单');

SELECT @menuId := LAST_INSERT_ID();

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('综合导航查询', @menuId, 1, '#', '', 1, 0, 'F', '0', '0', 'system:navigation:query', '#', 'admin', sysdate(), '', null, '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('综合导航新增', @menuId, 2, '#', '', 1, 0, 'F', '0', '0', 'system:navigation:add', '#', 'admin', sysdate(), '', null, '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('综合导航修改', @menuId, 3, '#', '', 1, 0, 'F', '0', '0', 'system:navigation:edit', '#', 'admin', sysdate(), '', null, '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('综合导航删除', @menuId, 4, '#', '', 1, 0, 'F', '0', '0', 'system:navigation:remove', '#', 'admin', sysdate(), '', null, '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES ('综合导航导出', @menuId, 5, '#', '', 1, 0, 'F', '0', '0', 'system:navigation:export', '#', 'admin', sysdate(), '', null, '');
