export default function Filters({ search, setSearch, service, setService, severity, setSeverity, status, setStatus, services }) {
  return (
    <div className="bg-white rounded-lg border border-slate-200 shadow p-4 mb-6">
      <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-5">
        <div className="lg:col-span-2">
          <label htmlFor="search" className="block text-sm font-medium text-slate-700 mb-1">
            Search (title, service, owner)
          </label>
          <input
            id="search"
            type="text"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            placeholder="Type to search…"
            className="block w-full rounded-md border-slate-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border py-2 px-3"
          />
        </div>
        <div>
          <label htmlFor="service" className="block text-sm font-medium text-slate-700 mb-1">
            Service
          </label>
          <select
            id="service"
            value={service}
            onChange={(e) => setService(e.target.value)}
            className="block w-full rounded-md border-slate-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border py-2 px-3"
          >
            <option value="">All</option>
            {services.map((s) => (
              <option key={s} value={s}>
                {s}
              </option>
            ))}
          </select>
        </div>
        <div>
          <label htmlFor="severity" className="block text-sm font-medium text-slate-700 mb-1">
            Severity
          </label>
          <select
            id="severity"
            value={severity}
            onChange={(e) => setSeverity(e.target.value)}
            className="block w-full rounded-md border-slate-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border py-2 px-3"
          >
            <option value="">All</option>
            <option value="SEV1">SEV1</option>
            <option value="SEV2">SEV2</option>
            <option value="SEV3">SEV3</option>
            <option value="SEV4">SEV4</option>
          </select>
        </div>
        <div>
          <label htmlFor="status" className="block text-sm font-medium text-slate-700 mb-1">
            Status
          </label>
          <select
            id="status"
            value={status}
            onChange={(e) => setStatus(e.target.value)}
            className="block w-full rounded-md border-slate-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm border py-2 px-3"
          >
            <option value="">All</option>
            <option value="OPEN">OPEN</option>
            <option value="MITIGATED">MITIGATED</option>
            <option value="RESOLVED">RESOLVED</option>
          </select>
        </div>
      </div>
    </div>
  );
}
