import { Navigate } from "react-router-dom";
import { useAuth } from "./auth";

function ProtectedRoute({ children }) {
  const user = useAuth();

  if (user.user) {
    return children;
  }

  return <Navigate to="/" replace={true} />;
}

export default ProtectedRoute;
