import { render, screen } from '@testing-library/react';
import { MemoryRouter, Navigate } from 'react-router-dom';
import ProtectedRoute from './ProtectedRoute';

jest.mock('../api/auth', () => ({
  getStoredToken: jest.fn(),
}));

const mockGetStoredToken = require('../api/auth').getStoredToken;

function renderProtectedRoute(hasToken = false) {
  mockGetStoredToken.mockReturnValue(hasToken ? 'fake-token' : null);
  return render(
    <MemoryRouter initialEntries={['/']}>
      <ProtectedRoute>
        <div>Protected content</div>
      </ProtectedRoute>
    </MemoryRouter>
  );
}

describe('ProtectedRoute', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('renders children when token exists', () => {
    renderProtectedRoute(true);
    expect(screen.getByText('Protected content')).toBeInTheDocument();
  });

  test('does not render children when no token', () => {
    renderProtectedRoute(false);
    expect(screen.queryByText('Protected content')).not.toBeInTheDocument();
  });
});
