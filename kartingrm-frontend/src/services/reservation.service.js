import http from '../http-common'

const list   = ()        => http.get('/api/reservations')
const create = payload   => http.post('/api/reservations', payload).then(r => r.data)
const cancel = id        => http.patch(`/api/reservations/${id}/cancel`)

export default { list, create, cancel }
