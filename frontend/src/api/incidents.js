import axios from 'axios';
import { getStoredToken, setStoredToken } from './auth';

const API_BASE = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const client = axios.create({
  baseURL: API_BASE,
  headers: { 'Content-Type': 'application/json' },
});

client.interceptors.request.use((config) => {
  const token = getStoredToken();
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

client.interceptors.response.use(
  (r) => r,
  (err) => {
    if (err.response?.status === 401) {
      setStoredToken(null);
      window.location.href = '/login';
    }
    return Promise.reject(err);
  }
);

export const incidentsApi = {
  list(params) {
    return client.get('/incidents', { params }).then((r) => r.data);
  },
  getById(id) {
    return client.get(`/incidents/${id}`).then((r) => r.data);
  },
  create(data) {
    return client.post('/incidents', data).then((r) => r.data);
  },
  update(id, data) {
    return client.patch(`/incidents/${id}`, data).then((r) => r.data);
  },
};

export default client;
