import { Link, Outlet } from 'react-router-dom';
import { setStoredToken } from '../api/auth';

export default function Layout() {
  const handleLogout = () => {
    setStoredToken(null);
    window.location.href = '/login';
  };

  return (
    <div className="min-h-screen bg-slate-50">
      <nav className="bg-white border-b border-slate-200 shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between h-14 items-center">
            <div className="flex items-center">
              <Link to="/" className="text-xl font-semibold text-slate-800 hover:text-indigo-600">
                Incident Tracker
              </Link>
              <Link
                to="/"
                className="ml-8 text-slate-600 hover:text-slate-900 px-3 py-2 rounded-md text-sm font-medium"
              >
                Incidents
              </Link>
              <Link
                to="/create"
                className="ml-2 text-slate-600 hover:text-slate-900 px-3 py-2 rounded-md text-sm font-medium"
              >
                Create
              </Link>
            </div>
            <button
              type="button"
              onClick={handleLogout}
              className="text-sm text-slate-600 hover:text-slate-900"
            >
              Logout
            </button>
          </div>
        </div>
      </nav>
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <Outlet />
      </main>
    </div>
  );
}
