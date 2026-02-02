任务目标： 在 RuoYi-Vue3 框架中开发一个响应式“综合导航页”模块。

1. 数据库与后端要求：

表设计： 在 slave 数据库 中创建 sys_navigation 表，包含字段：id (主键), title (名称), url (链接), icon (图标/图片地址), category (分类), sort (排序), status (状态 0正常 1停用)。

后端开发： 使用 RuoYi 代码生成规范，创建对应的 Entity, Mapper, Service, Controller。

权限控制： 确保 Controller 接口包含 @PreAuthorize 权限注解，支持增删改查。

2. 前端展示页面（用户视图）：

布局： 采用 响应式网格布局 (Responsive Grid)。大屏显示 4-6 列，平板 3 列，手机端自动切换为 1-2 列。

视觉风格： 借鉴简约设计风格。每一个导航项使用 el-card 封装，悬浮时有阴影提升效果 (hover-shadow)。

分类显示： 支持按“分类”自动分组展示，同一页面垂直滚动查看所有类别。

移动端优化： * 图标与文字垂直居中。

点击区域适配触屏，防止误触。

隐藏不必要的装饰元素，保持清爽。

3. 前端管理功能（交互设计）：

管理入口： 在导航页右上角添加“管理”按钮（仅限有权限的用户可见），点击后弹出 el-dialog 或切换为编辑模式。

操作交互： * 添加/修改： 使用表单弹窗，图标支持选择 RuoYi 内置的 svg-icon 或输入图片 URL。

删除： 二次确认提醒。

菜单集成： 自动生成 SQL 脚本或说明，将菜单挂载到“信息中心” (Information Center) 目录下。

4. 代码存放路径：

后端：ruoyi-modules/ruoyi-system 

前端：src/views/info/navigation/index.vue。
