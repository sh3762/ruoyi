import request from '@/utils/request'

// 获取系统状态
export function getStatus() {
  return request({
    url: '/zbh/status',
    method: 'get'
  })
}

// 获取云垒服务器列表
export function getYunLeiList(query) {
  return request({
    url: '/zbh/yunlei',
    method: 'get',
    params: query
  })
}

// 获取非正版化软件列表
export function getSoftwareList(query) {
  return request({
    url: '/zbh/software',
    method: 'get',
    params: query
  })
}
