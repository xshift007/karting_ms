import axios from 'axios';

const base = import.meta.env.VITE_API_BASE_URL;

const http = axios.create({
  baseURL: base,
  headers: { 'Content-Type': 'application/json' },
  timeout: 10000
});

export default http;
