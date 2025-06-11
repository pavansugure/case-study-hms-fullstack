import React, { useState } from "react";
import GuestFormModal from "./GuestFormModal";
import GuestSearchModal from "./GuestSearchModal";
import GuestListModal from "./GuestListModal";
import "../../styles/GuestActions.css";

const GuestActions = ({ roles }) => {
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
      <div className="guest-actions-container">
        <button onClick={toggleMenu} className="guest-actions-toggle">
          <span>👤 Guest Management</span>
          <span>{isOpen ? "▲" : "▼"}</span>
        </button>

        {isOpen && (
          <div className="guest-dropdown">
            {(hasRole("OWNER") || hasRole("RECEP")) && (
              <button className="guest-option" onClick={() => openOption("add")}>
                ➕ Add Guest
              </button>
            )}

            {(hasRole("OWNER") || hasRole("MANAGER") || hasRole("RECEP")) && (
              <>
                <button className="guest-option" onClick={() => openOption("view")}>
                  📄 View All Guests
                </button>
                <button className="guest-option" onClick={() => openOption("search")}>
                  🔍 Search Guest
                </button>
              </>
            )}
          </div>
        )}
      </div>

      {/* Ensure only one modal is open at a time */}
      {activeOption === "add" && <GuestFormModal onClose={() => setActiveOption(null)} />}
      {activeOption === "view" && <GuestListModal onClose={() => setActiveOption(null)} />}
      {activeOption === "search" && <GuestSearchModal onClose={() => setActiveOption(null)} />}
    </>
  );
};

export default GuestActions;
