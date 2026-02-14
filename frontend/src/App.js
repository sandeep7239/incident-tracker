import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Layout from './components/Layout';
import ProtectedRoute from './components/ProtectedRoute';
import IncidentList from './pages/IncidentList';
import IncidentDetail from './pages/IncidentDetail';
import CreateIncident from './pages/CreateIncident';
import Login from './pages/Login';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route element={<ProtectedRoute><Layout /></ProtectedRoute>}>
          <Route path="/" element={<IncidentList />} />
          <Route path="/create" element={<CreateIncident />} />
          <Route path="/incidents/:id" element={<IncidentDetail />} />
        </Route>
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
