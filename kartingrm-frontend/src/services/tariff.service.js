// src/services/tariff.service.js
import http from '../http-common'

const list   = cfg => http.get('/tariffs', cfg).then(r => r.data)
const update = (rateType, payload, cfg={}) =>
  http.put(`/tariffs/${rateType}`, payload, cfg).then(r => r.data)

export default { list, update }
