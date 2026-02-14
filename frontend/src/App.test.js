import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import App from './App';

// Helper to render with router
function renderWithRouter(ui, { route = '/' } = {}) {
  window.history.pushState({}, 'Test page', route);
  return render(ui);
}

describe('App', () => {
  beforeEach(() => {
    localStorage.clear();
  });

  test('renders login page at /login', () => {
    renderWithRouter(<App />, { route: '/login' });
    expect(screen.getByRole('heading', { name: /sign in/i })).toBeInTheDocument();
    expect(screen.getByLabelText(/username/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/password/i)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /sign in/i })).toBeInTheDocument();
  });

  test('unauthenticated user at / is redirected to login', () => {
    renderWithRouter(<App />, { route: '/' });
    expect(screen.getByRole('heading', { name: /sign in/i })).toBeInTheDocument();
  });

  test('authenticated user at / sees incident list area', () => {
    localStorage.setItem('incident_tracker_token', 'fake-jwt-token');
    renderWithRouter(<App />, { route: '/' });
    expect(screen.getByText(/Incident Tracker/i)).toBeInTheDocument();
    expect(screen.getByRole('link', { name: /incidents/i })).toBeInTheDocument();
    expect(screen.getByRole('link', { name: /create/i })).toBeInTheDocument();
  });
});
