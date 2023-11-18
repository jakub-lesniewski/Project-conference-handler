/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        fmcsWhite: "#f5f5f5",
        fmcsBlack: "#333333",
        fmcsGreen: "#048c04",
        fmcsRed: "#E74C3C",
      },
      height: {
        screen: "100dvh",
      },
      fontFamily: {
        sans: "Lato, sans-serif",
      },
    },
  },
  plugins: [],
};
