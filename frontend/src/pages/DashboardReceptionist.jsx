import { useState } from "react";
import { useAuth } from "../auth/AuthContext";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import GuestActions from "../features/guests/GuestActions";
import ReservationActions from "../features/reservations/ReservationActions";
import PaymentActions from "../features/payment/PaymentActions";

import { LogOut, Home } from "lucide-react";
import "../styles/DashboardReceptionist.css"; // Updated stylesheet for Receptionist role

export default function DashboardReceptionist() {
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

  const roles = user?.role
    ? [`ROLE_${user.role.toUpperCase()}`]
    : Array.isArray(user.roles)
    ? user.roles.map((r) => (typeof r === "string" ? r : r.authority))
    : [];

  return (
    <div className="dashboard-container">
      {/* Navbar */}
      <nav className="navbar">
        <div className="navbar-title">Receptionist</div>
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
      <nav className="services-navbar">
        {["Guests", "Reservations", "Payments"].map((service) => (
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
          {activeService === "Reservations" && <ReservationActions roles={roles} />}
          {activeService === "Payments" && <PaymentActions roles={roles} />}
        </div>
      )}
    </div>
  );
}
