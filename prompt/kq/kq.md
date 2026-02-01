读取\prompt\SQLServer2000.md 作为数据库链接标准
将Y:\web\2024\main.php 重构 
所有新增代码保存到d:\ruoyi\ruoyi-module\
接入若依框架d:\ruoyi\
要求美化界面，采用响应式框架，
所有数据库链接调用方法，参考若依下zbh项目，该项目已调通


根据代码分析， kq 模块（考勤模块）连接 10.33.1.8 服务器（数据库 zjz ）时，使用了以下 3 个表 ：

1. ice_clock
   - 用途：查询 ICE 签到列表。
   - 相关代码： KqMapper.selectIceClockList
2. face_hk_log
   - 用途：海康刷脸记录，包括上班、下班和全部日志查询。
   - 相关代码： KqMapper.selectFaceHkLogList 及 KqServiceImpl 中的动态 SQL 查询。