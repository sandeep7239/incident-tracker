import { Link } from 'react-router-dom';

const SEVERITY_CLASS = {
  SEV1: 'bg-red-100 text-red-800',
  SEV2: 'bg-orange-100 text-orange-800',
  SEV3: 'bg-yellow-100 text-yellow-800',
  SEV4: 'bg-green-100 text-green-800',
};

const STATUS_CLASS = {
  OPEN: 'bg-amber-100 text-amber-800',
  MITIGATED: 'bg-blue-100 text-blue-800',
  RESOLVED: 'bg-slate-100 text-slate-700',
};

function formatDate(iso) {
  if (!iso) return '—';
  const d = new Date(iso);
  return d.toLocaleString(undefined, { dateStyle: 'short', timeStyle: 'short' });
}

export default function IncidentTable({ incidents, sortBy, sortDir, onSort, loading }) {
  const th = (field, label) => (
    <th
      scope="col"
      className="px-4 py-3 text-left text-xs font-medium text-slate-500 uppercase tracking-wider cursor-pointer hover:bg-slate-100 select-none"
      onClick={() => onSort(field)}
    >
      <span className="flex items-center gap-1">
        {label}
        {sortBy === field && (sortDir === 'asc' ? ' ↑' : ' ↓')}
      </span>
    </th>
  );

  if (loading) {
    return (
      <div className="overflow-hidden rounded-lg border border-slate-200 bg-white shadow">
        <table className="min-w-full divide-y divide-slate-200">
          <thead className="bg-slate-50">
            <tr>
              {th('title', 'Title')}
              {th('service', 'Service')}
              {th('severity', 'Severity')}
              {th('status', 'Status')}
              {th('owner', 'Owner')}
              {th('createdAt', 'Created')}
              <th scope="col" className="px-4 py-3 text-right text-xs font-medium text-slate-500 uppercase">
                Actions
              </th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td colSpan={7} className="px-4 py-12 text-center text-slate-500">
                Loading…
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    );
  }

  if (!incidents || incidents.length === 0) {
    return (
      <div className="rounded-lg border border-slate-200 bg-white shadow p-12 text-center text-slate-500">
        No incidents found. Try adjusting filters or create one.
      </div>
    );
  }

  return (
    <div className="overflow-hidden rounded-lg border border-slate-200 bg-white shadow">
      <table className="min-w-full divide-y divide-slate-200">
        <thead className="bg-slate-50">
          <tr>
            {th('title', 'Title')}
            {th('service', 'Service')}
            {th('severity', 'Severity')}
            {th('status', 'Status')}
            {th('owner', 'Owner')}
            {th('createdAt', 'Created')}
            <th scope="col" className="px-4 py-3 text-right text-xs font-medium text-slate-500 uppercase">
              Actions
            </th>
          </tr>
        </thead>
        <tbody className="bg-white divide-y divide-slate-200">
          {incidents.map((inc) => (
            <tr key={inc.id} className="hover:bg-slate-50">
              <td className="px-4 py-3 text-sm text-slate-900 max-w-xs truncate" title={inc.title}>
                {inc.title}
              </td>
              <td className="px-4 py-3 text-sm text-slate-600">{inc.service}</td>
              <td className="px-4 py-3">
                <span
                  className={`inline-flex px-2 py-0.5 rounded text-xs font-medium ${SEVERITY_CLASS[inc.severity] || 'bg-slate-100'}`}
                >
                  {inc.severity}
                </span>
              </td>
              <td className="px-4 py-3">
                <span
                  className={`inline-flex px-2 py-0.5 rounded text-xs font-medium ${STATUS_CLASS[inc.status] || 'bg-slate-100'}`}
                >
                  {inc.status}
                </span>
              </td>
              <td className="px-4 py-3 text-sm text-slate-600">{inc.owner || '—'}</td>
              <td className="px-4 py-3 text-sm text-slate-500">{formatDate(inc.createdAt)}</td>
              <td className="px-4 py-3 text-right">
                <Link
                  to={`/incidents/${inc.id}`}
                  className="text-indigo-600 hover:text-indigo-800 font-medium text-sm"
                >
                  View
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
