import React, { useState } from "react";
import InventoryFormModal from "./InventoryFormModal";
import InventorySearchModal from "./InventorySearchModal";
import InventoryListModal from "./InventoryListModal";

import "../../styles/InventoryActions.css"; 

const InventoryActions = ({ roles }) => {
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
      <div className="inventory-actions-container">
        <button onClick={toggleMenu} className="inventory-actions-toggle">
          <span>📦 Inventory Management</span>
          <span>{isOpen ? "▲" : "▼"}</span>
        </button>

        {isOpen && (
          <div className="inventory-dropdown">
            {(hasRole("OWNER")) && (
              <button className="inventory-option" onClick={() => openOption("add")}>
                ➕ Add Inventory
              </button>
            )}

            {(hasRole("OWNER") || hasRole("MANAGER")) && (
              <>
                <button className="inventory-option" onClick={() => openOption("view")}>
                  📄 View Inventory
                </button>
                <button className="inventory-option" onClick={() => openOption("search")}>
                  🔍 Search Inventory
                </button>
              </>
            )}
          </div>
        )}
      </div>

      {/* Ensure only one modal is open at a time */}
      {activeOption === "add" && <InventoryFormModal onClose={() => setActiveOption(null)} />}
      {activeOption === "view" && <InventoryListModal onClose={() => setActiveOption(null)} />}
      {activeOption === "search" && <InventorySearchModal onClose={() => setActiveOption(null)} />}
    </>
  );
};

export default InventoryActions;
