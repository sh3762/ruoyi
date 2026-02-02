drop table if exists sys_navigation;
create table sys_navigation (
  id           bigint(20)      not null auto_increment,
  title        varchar(100)    default '',
  url          varchar(500)    default '',
  icon         varchar(255)    default '',
  category     varchar(100)    default '',
  sort         int(4)          default 0,
  status       char(1)         default '0',
  create_by    varchar(64)     default '',
  create_time  datetime,
  update_by    varchar(64)     default '',
  update_time  datetime,
  remark       varchar(500)    default null,
  primary key (id)
) engine=innodb auto_increment=1 comment = 'Navigation Table';
