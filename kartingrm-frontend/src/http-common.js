import axios from 'axios';

const base = '';

const http = axios.create({
  baseURL: base,
  headers: { 'Content-Type': 'application/json' },
  timeout: 10000
});

export default http;