import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
        // rewrite: (path) => path.replace(/^\/api,""),
      },
    },
  },
  build: {
    manifest: true,
    rollupOptions: {
      input: "/frontend/src/main.jsx",
    },
  },
});

// Toll the Great Bell Once!
// Pull the Lever forward to engage the

// Piston and Pump...

// Toll the Great Bell Twice!

// With push of Button fire the Engine

// And spark Turbine into life...

// Toll the Great Bell Thrice!

// Sing Praise to the

// God of All Machines
// -Excerpt
