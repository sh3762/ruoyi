import request from '@/utils/request'

// 查询IP列表
export function listIp(query) {
  return request({
    url: '/module/ip/list',
    method: 'get',
    params: query,
    timeout: 60000 // 增加超时时间到60秒
  })
}

// 查询统计信息
export function getIpStats() {
  return request({
    url: '/module/ip/stats',
    method: 'get',
    timeout: 60000 // 增加超时时间到60秒
  })
}

// 修改备注
export function updateIp(data) {
  return request({
    url: '/module/ip',
    method: 'put',
    data: data
  })
}

// 删除IP
export function delIp(data) {
  return request({
    url: '/module/ip',
    method: 'delete',
    data: data
  })
}
