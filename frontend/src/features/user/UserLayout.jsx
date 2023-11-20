import { Outlet } from "react-router-dom";

function UserLayout() {
  return (
    <div className="flex items-center justify-center">
      <Outlet />
    </div>
  );
}

export default UserLayout;
