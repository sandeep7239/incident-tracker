import axios from 'axios';

const API_BASE = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';
const TOKEN_KEY = 'incident_tracker_token';

export function getStoredToken() {
  return localStorage.getItem(TOKEN_KEY);
}

export function setStoredToken(token) {
  if (token) localStorage.setItem(TOKEN_KEY, token);
  else localStorage.removeItem(TOKEN_KEY);
}

export const authApi = {
  login(username, password) {
    return axios
      .post(`${API_BASE}/auth/login`, { username, password })
      .then((r) => r.data);
  },
};
