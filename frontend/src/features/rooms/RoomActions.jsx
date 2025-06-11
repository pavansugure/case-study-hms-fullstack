import React, { useState } from "react";
import RoomFormModal from "./RoomFormModal";
import RoomSearchModal from "./RoomSearchModal";
import RoomListModal from "./RoomListModal";
import CheckAvailabilityModal from "./CheckAvailabilityModal";

import "../../styles/RoomActions.css"; // Import the updated styles

const RoomActions = ({ roles }) => {
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
      <div className="room-actions-container">
        <button onClick={toggleMenu} className="room-actions-toggle">
          <span>🏨 Room Management</span>
          <span>{isOpen ? "▲" : "▼"}</span>
        </button>

        {isOpen && (
          <div className="room-dropdown">
            {(hasRole("OWNER") || hasRole("RECEPTIONIST")) && (
              <button className="room-option" onClick={() => openOption("add")}>
                ➕ Add Room
              </button>
            )}

            {(hasRole("OWNER") || hasRole("MANAGER")) && (
              <>
                <button className="room-option" onClick={() => openOption("view")}>
                  📄 View All Rooms
                </button>
                <button className="room-option" onClick={() => openOption("search")}>
                  🔍 Search Room
                </button>
                <button className="room-option" onClick={() => openOption("availability")}>
                  🏨 Check Room Availability
                </button>
              </>
            )}
          </div>
        )}
      </div>

      {/* Ensure only one modal is open at a time */}
      {activeOption === "add" && <RoomFormModal onClose={() => setActiveOption(null)} />}
      {activeOption === "view" && <RoomListModal onClose={() => setActiveOption(null)} />}
      {activeOption === "search" && <RoomSearchModal onClose={() => setActiveOption(null)} />}
      {activeOption === "availability" && <CheckAvailabilityModal onClose={() => setActiveOption(null)} />}
    </>
  );
};

export default RoomActions;
