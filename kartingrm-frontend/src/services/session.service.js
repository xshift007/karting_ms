import http from '../http-common'

const weekly = (from, to, cfg = {}) =>
  http.get('/api/sessions/availability', { params:{ from, to }, ...cfg })

const getAll = (cfg = {})            => http.get('/api/sessions', cfg)
const create = (payload, cfg = {})   => http.post('/api/sessions', payload, cfg).then(r => r.data)
const update = (id, payload, cfg={}) => http.put(`/api/sessions/${id}`, payload, cfg).then(r => r.data)
const remove = (id, cfg = {})        => http.delete(`/api/sessions/${id}`, cfg)

export default { weekly, getAll, create, update, delete: remove }
