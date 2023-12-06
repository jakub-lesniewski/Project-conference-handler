import { Navigate } from "react-router-dom";
import { useAuth } from "./auth";

function ProtectedRoute({ roles, children }) {
  const user = useAuth();

  // no roles means available to everyone
  // if (!roles?.length || roles.includes(user.role)) {
  //   return children;
  // }

  if (user.user) {
    return children;
  }

  return <Navigate to="/" replace={true} />;
}

export default ProtectedRoute;
