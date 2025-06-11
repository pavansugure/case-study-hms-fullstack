import { Routes, Route } from "react-router-dom";
import Login from "../pages/Login.jsx";
import DashboardOwner from "../pages/DashboardOwner.jsx";
import DashboardManager from "../pages/DashboardManager.jsx";
import DashboardReceptionist from "../pages/DashboardReceptionist.jsx";
import Home from "../pages/Home.jsx";
import Register from "../pages/Register.jsx";
import PrivateRoute from "../auth/PrivateRoute.jsx";
import Unauthorized from "../pages/Unauthorized.jsx";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/unauthorized" element={<Unauthorized />} />

      <Route
        path="/owner"
        element={
          <PrivateRoute allowedRoles={["owner"]}>
            <DashboardOwner />
          </PrivateRoute>
        }
      />
      <Route
        path="/manager"
        element={
          <PrivateRoute allowedRoles={["manager"]}>
            <DashboardManager />
          </PrivateRoute>
        }
      />
      <Route
        path="/recep"
        element={
          <PrivateRoute allowedRoles={["recep"]}>
            <DashboardReceptionist />
          </PrivateRoute>
        }
      />
    </Routes>
  );
}
