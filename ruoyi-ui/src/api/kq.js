import request from '@/utils/request'

export function listIce(query) {
  return request({
    url: '/kq/ice/list',
    method: 'get',
    params: query
  })
}

export function getFaceData() {
  return request({
    url: '/kq/face/data',
    method: 'get'
  })
}

export function listSis() {
  return request({
    url: '/kq/sis/list',
    method: 'get'
  })
}
