import request from '@/utils/request'

// 查询综合导航列表
export function listNavigation(query) {
  return request({
    url: '/system/navigation/list',
    method: 'get',
    params: query
  })
}

// 查询综合导航详细
export function getNavigation(id) {
  return request({
    url: '/system/navigation/' + id,
    method: 'get'
  })
}

// 新增综合导航
export function addNavigation(data) {
  return request({
    url: '/system/navigation',
    method: 'post',
    data: data
  })
}

// 修改综合导航
export function updateNavigation(data) {
  return request({
    url: '/system/navigation',
    method: 'put',
    data: data
  })
}

// 删除综合导航
export function delNavigation(id) {
  return request({
    url: '/system/navigation/' + id,
    method: 'delete'
  })
}
