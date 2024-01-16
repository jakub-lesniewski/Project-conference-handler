import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { AuthProvider } from "./utils/auth";
import React from "react";
import ReactDOM from "react-dom/client";
import LandingLayout from "./features/login/LandingLayout";
import Backoffice from "./features/backoffice/Backoffice";
import ErrorPage from "./ui/ErrorPage";
import Login from "./features/login/Login";
import User, { loader as userLoader } from "./features/user/User";
import ProtectedRoute from "./utils/ProtectedRoute";

import "./index.css";
import { BackofficeProvider } from "./features/backoffice/BackofficeContext";

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
    element: <Backoffice />,
    errorElement: <ErrorPage />,
  },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <BackofficeProvider>
      <AuthProvider>
        <RouterProvider router={router} />
      </AuthProvider>
    </BackofficeProvider>
  </React.StrictMode>,
);
