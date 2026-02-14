import { Navigate, useLocation } from 'react-router-dom';
import { getStoredToken } from '../api/auth';

export default function ProtectedRoute({ children }) {
  const location = useLocation();
  const token = getStoredToken();
  if (!token) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }
  return children;
}
