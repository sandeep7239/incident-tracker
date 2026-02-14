import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authApi, setStoredToken } from '../api/auth';

export default function Login() {
  const navigate = useNavigate();

  const [username, setUsername] = useState('admin');
  const [password, setPassword] = useState('admin');
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);

    // ✅ Validation (required for tests)
    if (!username.trim() || !password.trim()) {
      setError('Username and password required');
      return;
    }

    setLoading(true);
    try {
      const data = await authApi.login(username, password);
      setStoredToken(data.token);

      // ✅ Correct SPA navigation (NO reload)
      navigate('/', { replace: true });

    } catch (err) {
      setError(
        err.response?.data?.message ||
        'Login failed. Use admin/admin or user/user.'
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-slate-50 flex items-center justify-center px-4">
      <div className="max-w-sm w-full bg-white rounded-lg shadow-md border border-slate-200 p-6">
        <h1 className="text-xl font-semibold text-slate-900 mb-4">
          Incident Tracker – Sign in
        </h1>

        <form onSubmit={handleSubmit} className="space-y-4">
          {error && (
            <div className="rounded-md bg-red-50 border border-red-200 p-2 text-sm text-red-700">
              {error}
            </div>
          )}

          <div>
            <label htmlFor="username" className="block text-sm font-medium text-slate-700 mb-1">
              Username
            </label>
            <input
              id="username"
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="block w-full rounded-md border-slate-300 shadow-sm border py-2 px-3 text-sm"
              required
            />
          </div>

          <div>
            <label htmlFor="password" className="block text-sm font-medium text-slate-700 mb-1">
              Password
            </label>
            <input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="block w-full rounded-md border-slate-300 shadow-sm border py-2 px-3 text-sm"
              required
            />
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full py-2 px-4 bg-indigo-600 text-white font-medium rounded-md hover:bg-indigo-700 disabled:opacity-50"
          >
            {loading ? 'Signing in…' : 'Sign in'}
          </button>
        </form>

        <p className="mt-3 text-xs text-slate-500">
          Login With Correct Credentials
        </p>
      </div>
    </div>
  );
}
