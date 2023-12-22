import { Outlet } from "react-router-dom";

function LandingLayout() {
  return (
    <main className="flex min-h-screen items-center justify-center">
      <Outlet />
    </main>
  );
}

export default LandingLayout;
