import { useState, useEffect, useCallback } from 'react';
import { incidentsApi } from '../api/incidents';
import IncidentTable from '../components/IncidentTable';
import Pagination from '../components/Pagination';
import Filters from '../components/Filters';
import LoadingSpinner from '../components/LoadingSpinner';

const PAGE_SIZE = 10;
const DEBOUNCE_MS = 400;
const SERVICES = [
  'payment-service', 'auth-service', 'user-service', 'order-service', 'inventory-service',
  'notification-service', 'analytics-service', 'api-gateway', 'search-service', 'reporting-service',
];

export default function IncidentList() {
  const [data, setData] = useState({ content: [], page: 0, size: PAGE_SIZE, totalElements: 0, totalPages: 0, first: true, last: false });
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);
  const [search, setSearch] = useState('');
  const [searchInput, setSearchInput] = useState('');
  const [service, setService] = useState('');
  const [severity, setSeverity] = useState('');
  const [status, setStatus] = useState('');
  const [sortBy, setSortBy] = useState('createdAt');
  const [sortDir, setSortDir] = useState('desc');

  const fetchIncidents = useCallback(async () => {
    setLoading(true);
    try {
      const res = await incidentsApi.list({
        page,
        size: PAGE_SIZE,
        search: search || undefined,
        service: service || undefined,
        severity: severity || undefined,
        status: status || undefined,
        sortBy: sortBy || undefined,
        sortDir,
      });
      setData(res);
    } catch (err) {
      console.error(err);
      setData({ content: [], page: 0, size: PAGE_SIZE, totalElements: 0, totalPages: 0, first: true, last: false });
    } finally {
      setLoading(false);
    }
  }, [page, search, service, severity, status, sortBy, sortDir]);

  useEffect(() => {
    fetchIncidents();
  }, [fetchIncidents]);

  useEffect(() => {
    const t = setTimeout(() => setSearch(searchInput), DEBOUNCE_MS);
    return () => clearTimeout(t);
  }, [searchInput]);

  useEffect(() => {
    setPage(0);
  }, [search, service, severity, status]);

  const handleSort = (field) => {
    const nextDir = sortBy === field && sortDir === 'asc' ? 'desc' : 'asc';
    setSortBy(field);
    setSortDir(nextDir);
    setPage(0);
  };

  return (
    <div>
      <h1 className="text-2xl font-bold text-slate-900 mb-4">Incidents</h1>
      <Filters
        search={searchInput}
        setSearch={setSearchInput}
        service={service}
        setService={setService}
        severity={severity}
        setSeverity={setSeverity}
        status={status}
        setStatus={setStatus}
        services={SERVICES}
      />
      <IncidentTable
        incidents={data.content}
        sortBy={sortBy}
        sortDir={sortDir}
        onSort={handleSort}
        loading={loading}
      />
      {!loading && data.content.length > 0 && (
        <Pagination
          page={data.page}
          totalPages={data.totalPages}
          totalElements={data.totalElements}
          size={data.size}
          onPageChange={setPage}
        />
      )}
    </div>
  );
}
