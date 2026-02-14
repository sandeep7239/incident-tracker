/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        sev1: '#dc2626',
        sev2: '#ea580c',
        sev3: '#ca8a04',
        sev4: '#16a34a',
      },
    },
  },
  plugins: [],
};
