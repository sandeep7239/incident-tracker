import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { BrowserRouter, MemoryRouter } from 'react-router-dom';
import Login from './Login';

// Mock the auth API
jest.mock('../api/auth', () => ({
  authApi: {
    login: jest.fn(),
  },
  setStoredToken: jest.fn(),
}));

const mockAuth = require('../api/auth');

function renderLogin() {
  return render(
    <MemoryRouter>
      <Login />
    </MemoryRouter>
  );
}

describe('Login', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('renders login form with default credentials', () => {
    renderLogin();
    expect(screen.getByRole('heading', { name: /sign in/i })).toBeInTheDocument();
    expect(screen.getByLabelText(/username/i)).toHaveValue('admin');
    expect(screen.getByLabelText(/password/i)).toHaveValue('admin');
    expect(screen.getByRole('button', { name: /sign in/i })).toBeInTheDocument();
  });

  test('shows validation when username is empty', async () => {
    renderLogin();
    const username = screen.getByLabelText(/username/i);
    const submit = screen.getByRole('button', { name: /sign in/i });
    await userEvent.clear(username);
    await userEvent.click(submit);
    expect(mockAuth.authApi.login).not.toHaveBeenCalled();
  });

  test('calls login API and setStoredToken on successful submit', async () => {
    mockAuth.authApi.login.mockResolvedValue({ token: 'jwt-123', type: 'Bearer', username: 'admin', expiresInMs: 86400000 });
    const user = userEvent.setup();
    renderLogin();
    await user.click(screen.getByRole('button', { name: /sign in/i }));
    await waitFor(() => {
      expect(mockAuth.authApi.login).toHaveBeenCalledWith('admin', 'admin');
    });
    await waitFor(() => {
      expect(mockAuth.setStoredToken).toHaveBeenCalledWith('jwt-123');
    });
  });

  test('shows error message when login fails', async () => {
    mockAuth.authApi.login.mockRejectedValue(new Error('Invalid credentials'));
    const user = userEvent.setup();
    renderLogin();
    await user.click(screen.getByRole('button', { name: /sign in/i }));
    await waitFor(() => {
      expect(screen.getByText(/login failed|invalid/i)).toBeInTheDocument();
    });
  });
});
