import { Outlet } from "react-router-dom";

function BackofficeLayout() {
  return (
    <main className="flex h-screen w-screen justify-center">
      <Outlet />
    </main>
  );
}

export default BackofficeLayout;
