import React, { useState } from "react";
import ReservationFormModal from "./ReservationFormModal";
import ReservationByCodeModal from "./ReservationByCodeModal";
import ReservationSearchModal from "./ReservationSearchModal";
import ReservationListModal from "./ReservationListModal";

import "../../styles/ReservationActions.css"; // Import the updated styles

const ReservationActions = ({ roles }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [activeOption, setActiveOption] = useState(null); // Track active modal

  const toggleMenu = () => setIsOpen(!isOpen);
  const hasRole = (role) => roles.includes(`ROLE_${role.toUpperCase()}`);

  // Function to open a modal while ensuring others close
  const openOption = (option) => {
    setActiveOption((prevOption) => (prevOption === option ? null : option));
  };

  return (
    <>
      <div className="reservation-actions-container">
        <button onClick={toggleMenu} className="reservation-actions-toggle">
          <span>🛏️ Reservation Management</span>
          <span>{isOpen ? "▲" : "▼"}</span>
        </button>

        {isOpen && (
          <div className="reservation-dropdown">
            {(hasRole("OWNER") || hasRole("RECEP")) && (
              <button className="reservation-option" onClick={() => openOption("add")}>
                ➕ Add Reservation
              </button>
            )}
            {(hasRole("OWNER") || hasRole("MANAGER") || hasRole("RECEP")) && (
              <>
                <button className="reservation-option" onClick={() => openOption("get")}>
                  🔎 Get by Code
                </button>
                <button className="reservation-option" onClick={() => openOption("search")}>
                  📅 Search by Date
                </button>
                <button className="reservation-option" onClick={() => openOption("view")}>
                  📄 View All Reservations
                </button>
              </>
            )}
          </div>
        )}
      </div>

      {/* Ensure only one modal is open at a time */}
      {activeOption === "add" && <ReservationFormModal onClose={() => setActiveOption(null)} />}
      {activeOption === "get" && <ReservationByCodeModal onClose={() => setActiveOption(null)} />}
      {activeOption === "search" && <ReservationSearchModal onClose={() => setActiveOption(null)} />}
      {activeOption === "view" && <ReservationListModal onClose={() => setActiveOption(null)} />}
    </>
  );
};

export default ReservationActions;
