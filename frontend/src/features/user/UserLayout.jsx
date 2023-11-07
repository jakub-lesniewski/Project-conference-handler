import { Outlet } from "react-router-dom";

function UserLayout() {
  return (
    <div className="mt flex items-center justify-center">
      <Outlet />
    </div>
  );
}

export default UserLayout;
