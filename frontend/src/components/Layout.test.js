import { render, screen } from '@testing-library/react';
import { MemoryRouter, Outlet } from 'react-router-dom';
import Layout from './Layout';

jest.mock('../api/auth', () => ({
  setStoredToken: jest.fn(),
}));

describe('Layout', () => {
  test('renders nav with Incident Tracker title and links', () => {
    render(
      <MemoryRouter>
        <Layout />
      </MemoryRouter>
    );
    expect(screen.getByRole('link', { name: /incident tracker/i })).toBeInTheDocument();
    expect(screen.getByRole('link', { name: /incidents/i })).toBeInTheDocument();
    expect(screen.getByRole('link', { name: /create/i })).toBeInTheDocument();
    expect(screen.getByRole('button', { name: /logout/i })).toBeInTheDocument();
  });
});
