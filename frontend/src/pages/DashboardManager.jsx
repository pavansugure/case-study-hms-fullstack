import { useState } from "react";
import { useAuth } from "../auth/AuthContext";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import GuestActions from "../features/guests/GuestActions";
import InventoryActions from "../features/inventory/InventoryActions";
import ReservationActions from "../features/reservations/ReservationActions";
import RoomActions from "../features/rooms/RoomActions";
import StaffActions from "../features/staff/StaffActions";
import PaymentActions from "../features/payment/PaymentActions";

import { LogOut, Home } from "lucide-react";
import "../styles/DashboardManager.css"; 

export default function DashboardManager() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [activeService, setActiveService] = useState(null); // Track active service

  const handleLogout = () => {
    logout();
    toast.success("Logged out!");
    navigate("/login");
  };

  const handleHome = () => {
    navigate("/");
  };

  const toggleService = (service) => {
    setActiveService((prevService) => (prevService === service ? null : service));
  };

  if (!user) return <div className="loading-text">Loading...</div>;

  const roles = user?.role      // returns undefined if user is null/undefined
    ? [`ROLE_${user.role.toUpperCase()}`]
    : Array.isArray(user.roles)
    ? user.roles.map((r) => (typeof r === "string" ? r : r.authority))
    : [];

  return (
    <div className="dashboard-container">
      {/* Navbar */}
      <nav className="navbar">
        <div className="navbar-title">Manager</div>
        <div className="navbar-actions">
          <button onClick={handleHome} className="nav-button" title="Home">
            <Home size={20} /> Home
          </button>
          <button onClick={handleLogout} className="nav-button logout" title="Logout">
            <LogOut size={20} /> Logout
          </button>
        </div>
      </nav>

      {/* Services Navbar */}
      <nav className="services-navbarr">
        {["Guests", "Rooms", "Reservations", "Inventory", "Payments"].map((service) => (
          <button
            key={service}
            className={`service-button ${activeService === service ? "active" : ""}`}
            onClick={() => toggleService(service)}
          >
            {service} 
          </button>
        ))}
      </nav>

      {/* Dropdown Cards */}
      {activeService && (
        <div className="dropdown-container">
          {activeService === "Guests" && <GuestActions roles={roles} />}
          {activeService === "Rooms" && <RoomActions roles={roles} />}
          {activeService === "Reservations" && <ReservationActions roles={roles} />}
          {activeService === "Inventory" && <InventoryActions roles={roles} />}
          {activeService === "Payments" && <PaymentActions roles={roles} />}
        </div>
      )}
    </div>
  );
}
