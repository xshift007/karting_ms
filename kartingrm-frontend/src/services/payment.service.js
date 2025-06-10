import http from '../http-common'
const pay   = payload => http.post('/api/payments', payload)
const receipt = id    => http.get(`/api/payments/${id}/receipt`, { responseType:'blob'})
export default { pay, receipt }
