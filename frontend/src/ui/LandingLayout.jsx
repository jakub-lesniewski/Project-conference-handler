import { Outlet } from "react-router-dom";

function LandingLayout() {
  return (
    <div className="flex min-h-screen items-center justify-center">
      <Outlet />
    </div>
  );
}

export default LandingLayout;
