import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { incidentsApi } from '../api/incidents';

const SEVERITIES = ['SEV1', 'SEV2', 'SEV3', 'SEV4'];
const STATUSES = ['OPEN', 'MITIGATED', 'RESOLVED'];

export default function CreateIncident() {
  const navigate = useNavigate();
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(null);
  const [form, setForm] = useState({
    title: '',
    service: '',
    severity: 'SEV2',
    status: 'OPEN',
    owner: '',
    summary: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setSaving(true);
    try {
      const created = await incidentsApi.create({
        title: form.title.trim(),
        service: form.service.trim(),
        severity: form.severity,
        status: form.status,
        owner: form.owner.trim() || undefined,
        summary: form.summary.trim() || undefined,
      });
      navigate(`/incidents/${created.id}`);
    } catch (err) {
      const msg = err.response?.data?.errors
        ? Object.entries(err.response.data.errors).map(([k, v]) => `${k}: ${v}`).join(', ')
        : err.response?.data?.message || 'Failed to create incident';
      setError(msg);
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="max-w-2xl">
      <h1 className="text-2xl font-bold text-slate-900 mb-4">Create incident</h1>
      <form onSubmit={handleSubmit} className="bg-white rounded-lg border border-slate-200 shadow p-6 space-y-4">
        {error && (
          <div className="rounded-md bg-red-50 border border-red-200 p-3 text-sm text-red-700">
            {error}
          </div>
        )}
        <div>
          <label htmlFor="title" className="block text-sm font-medium text-slate-700 mb-1">
            Title <span className="text-red-500">*</span>
          </label>
          <input
            id="title"
            name="title"
            type="text"
            required
            maxLength={255}
            value={form.title}
            onChange={handleChange}
            className="block w-full rounded-md border-slate-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border py-2 px-3"
            placeholder="e.g. API latency spike"
          />
        </div>
        <div>
          <label htmlFor="service" className="block text-sm font-medium text-slate-700 mb-1">
            Service <span className="text-red-500">*</span>
          </label>
          <input
            id="service"
            name="service"
            type="text"
            required
            maxLength={100}
            value={form.service}
            onChange={handleChange}
            className="block w-full rounded-md border-slate-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border py-2 px-3"
            placeholder="e.g. payment-service"
          />
        </div>
        <div className="grid grid-cols-2 gap-4">
          <div>
            <label htmlFor="severity" className="block text-sm font-medium text-slate-700 mb-1">
              Severity <span className="text-red-500">*</span>
            </label>
            <select
              id="severity"
              name="severity"
              value={form.severity}
              onChange={handleChange}
              className="block w-full rounded-md border-slate-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border py-2 px-3"
            >
              {SEVERITIES.map((s) => (
                <option key={s} value={s}>{s}</option>
              ))}
            </select>
          </div>
          <div>
            <label htmlFor="status" className="block text-sm font-medium text-slate-700 mb-1">
              Status <span className="text-red-500">*</span>
            </label>
            <select
              id="status"
              name="status"
              value={form.status}
              onChange={handleChange}
              className="block w-full rounded-md border-slate-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border py-2 px-3"
            >
              {STATUSES.map((s) => (
                <option key={s} value={s}>{s}</option>
              ))}
            </select>
          </div>
        </div>
        <div>
          <label htmlFor="owner" className="block text-sm font-medium text-slate-700 mb-1">
            Owner (optional)
          </label>
          <input
            id="owner"
            name="owner"
            type="text"
            maxLength={100}
            value={form.owner}
            onChange={handleChange}
            className="block w-full rounded-md border-slate-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border py-2 px-3"
            placeholder="e.g. alice@zeotap.com"
          />
        </div>
        <div>
          <label htmlFor="summary" className="block text-sm font-medium text-slate-700 mb-1">
            Summary (optional)
          </label>
          <textarea
            id="summary"
            name="summary"
            rows={4}
            value={form.summary}
            onChange={handleChange}
            className="block w-full rounded-md border-slate-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border py-2 px-3"
            placeholder="Brief description of the incident"
          />
        </div>
        <div className="flex gap-3 pt-2">
          <button
            type="submit"
            disabled={saving}
            className="px-4 py-2 bg-indigo-600 text-white font-medium rounded-md hover:bg-indigo-700 disabled:opacity-50"
          >
            {saving ? 'Creating…' : 'Create incident'}
          </button>
          <button
            type="button"
            onClick={() => navigate('/')}
            className="px-4 py-2 border border-slate-300 text-slate-700 font-medium rounded-md hover:bg-slate-50"
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}
