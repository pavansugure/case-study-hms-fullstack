import React, { useState } from "react";
import StaffFormModal from "./StaffFormModal";
import StaffSearchModal from "./StaffSearchModal";
import StaffListModal from "./StaffListModal";

import "../../styles/StaffActions.css"; // Import the updated styles

const StaffActions = ({ roles }) => {
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
      <div className="staff-actions-container">
        <button onClick={toggleMenu} className="staff-actions-toggle">
          <span>👨‍💼 Staff Management</span>
          <span>{isOpen ? "▲" : "▼"}</span>
        </button>

        {isOpen && (
          <div className="staff-dropdown">
            {(hasRole("OWNER") || hasRole("MANAGER")) && (
              <>
                <button className="staff-option" onClick={() => openOption("add")}>
                  ➕ Add Staff
                </button>
                <button className="staff-option" onClick={() => openOption("view")}>
                  📄 View All Staff
                </button>
                <button className="staff-option" onClick={() => openOption("search")}>
                  🔍 Search Staff
                </button>
              </>
            )}
          </div>
        )}
      </div>

      {/* Ensure only one modal is open at a time */}
      {activeOption === "add" && <StaffFormModal onClose={() => setActiveOption(null)} />}
      {activeOption === "view" && <StaffListModal onClose={() => setActiveOption(null)} />}
      {activeOption === "search" && <StaffSearchModal onClose={() => setActiveOption(null)} />}
    </>
  );
};

export default StaffActions;
