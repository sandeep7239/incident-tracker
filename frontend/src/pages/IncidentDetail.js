import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { incidentsApi } from '../api/incidents';
import LoadingSpinner from '../components/LoadingSpinner';

const SEVERITY_CLASS = {
  SEV1: 'bg-red-100 text-red-800',
  SEV2: 'bg-orange-100 text-orange-800',
  SEV3: 'bg-yellow-100 text-yellow-800',
  SEV4: 'bg-green-100 text-green-800',
};

const STATUS_OPTIONS = ['OPEN', 'MITIGATED', 'RESOLVED'];

function formatDate(iso) {
  if (!iso) return '—';
  return new Date(iso).toLocaleString(undefined, { dateStyle: 'medium', timeStyle: 'short' });
}

export default function IncidentDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [incident, setIncident] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [saving, setSaving] = useState(false);
  const [editStatus, setEditStatus] = useState('');

  useEffect(() => {
    let cancelled = false;
    incidentsApi
      .getById(id)
      .then((data) => {
        if (!cancelled) {
          setIncident(data);
          setEditStatus(data.status);
        }
      })
      .catch((err) => {
        if (!cancelled) {
          setError(err.response?.data?.message || 'Failed to load incident');
        }
      })
      .finally(() => {
        if (!cancelled) setLoading(false);
      });
    return () => { cancelled = true; };
  }, [id]);

  const handleUpdateStatus = async (e) => {
    e.preventDefault();
    if (!incident || editStatus === incident.status) return;
    setSaving(true);
    try {
      const updated = await incidentsApi.update(id, { status: editStatus });
      setIncident(updated);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to update');
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <LoadingSpinner />;
  if (error && !incident) {
    return (
      <div className="rounded-lg bg-red-50 border border-red-200 p-4 text-red-700">
        {error}
        <button onClick={() => navigate('/')} className="ml-4 text-indigo-600 hover:underline">
          Back to list
        </button>
      </div>
    );
  }
  if (!incident) return null;

  return (
    <div className="max-w-3xl">
      <button
        onClick={() => navigate('/')}
        className="text-slate-600 hover:text-slate-900 mb-4 text-sm font-medium"
      >
        Back to incidents
      </button>
      <div className="bg-white rounded-lg border border-slate-200 shadow overflow-hidden">
        <div className="px-6 py-4 border-b border-slate-200">
          <h1 className="text-xl font-semibold text-slate-900">{incident.title}</h1>
          <div className="mt-2 flex flex-wrap gap-2">
            <span className={`inline-flex px-2 py-0.5 rounded text-xs font-medium ${SEVERITY_CLASS[incident.severity] || ''}`}>
              {incident.severity}
            </span>
            <span className="inline-flex px-2 py-0.5 rounded text-xs font-medium bg-slate-100 text-slate-700">
              {incident.service}
            </span>
          </div>
        </div>
        <dl className="px-6 py-4 space-y-3">
          <div>
            <dt className="text-sm font-medium text-slate-500">Status</dt>
            <dd className="mt-1">
              <form onSubmit={handleUpdateStatus} className="flex items-center gap-2">
                <select
                  value={editStatus}
                  onChange={(e) => setEditStatus(e.target.value)}
                  className="rounded border-slate-300 text-sm py-1.5 px-2 border"
                >
                  {STATUS_OPTIONS.map((s) => (
                    <option key={s} value={s}>{s}</option>
                  ))}
                </select>
                <button
                  type="submit"
                  disabled={saving || editStatus === incident.status}
                  className="px-3 py-1.5 bg-indigo-600 text-white text-sm font-medium rounded hover:bg-indigo-700 disabled:opacity-50"
                >
                  {saving ? 'Saving…' : 'Update status'}
                </button>
              </form>
            </dd>
          </div>
          <div>
            <dt className="text-sm font-medium text-slate-500">Owner</dt>
            <dd className="mt-1 text-slate-900">{incident.owner || '—'}</dd>
          </div>
          <div>
            <dt className="text-sm font-medium text-slate-500">Summary</dt>
            <dd className="mt-1 text-slate-900 whitespace-pre-wrap">{incident.summary || '—'}</dd>
          </div>
          <div>
            <dt className="text-sm font-medium text-slate-500">Created</dt>
            <dd className="mt-1 text-slate-600">{formatDate(incident.createdAt)}</dd>
          </div>
          <div>
            <dt className="text-sm font-medium text-slate-500">Updated</dt>
            <dd className="mt-1 text-slate-600">{formatDate(incident.updatedAt)}</dd>
          </div>
        </dl>
      </div>
    </div>
  );
}
