import request from '@/utils/request'

export function getFaceData() {
  return request({
    url: '/kq/face/data',
    method: 'get'
  })
}
