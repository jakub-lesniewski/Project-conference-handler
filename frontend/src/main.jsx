import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { AuthProvider } from "./utils/auth";
import React from "react";
import ReactDOM from "react-dom/client";
import LandingLayout from "./ui/LandingLayout";
import ErrorPage from "./ui/ErrorPage";
import Login from "./features/login/Login";
import User, { loader as userLoader } from "./features/user/User";
import Backoffice from "./features/backoffice/Backoffice";
import BackofficeLayout from "./features/backoffice/BackofficeLayout";
import ProtectedRoute from "./utils/ProtectedRoute";
import "./index.css";

const router = createBrowserRouter([
  {
    path: "/",
    element: <LandingLayout />,
    children: [
      {
        path: "/",
        element: <Login />,
      },
      {
        path: "/user/:userId",
        element: (
          <ProtectedRoute>
            <User />
          </ProtectedRoute>
        ),
        loader: userLoader,
      },
    ],
    errorElement: <ErrorPage />,
  },

  {
    path: "/backoffice",
    element: <BackofficeLayout />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "/backoffice",
        element: <Backoffice />,
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  </React.StrictMode>,
);
