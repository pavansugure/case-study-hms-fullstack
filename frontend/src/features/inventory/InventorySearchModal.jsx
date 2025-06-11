import React, { useState } from "react";
import { getInventoryByCode } from "../../api/inventoryApi";
import { toast } from "react-toastify";

const InventorySearchModal = ({ onClose }) => {
  const [code, setCode] = useState("");
  const [item, setItem] = useState(null);

  const handleSearch = async () => {
    try {
      const res = await getInventoryByCode(code);
      setItem(res.data);
    } catch {
      toast.error("Inventory item not found");
    }
  };

  return (
    <div
      style={{
        position: "fixed",
        inset: 0,
        backgroundColor: "rgba(0,0,0,0.5)",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        zIndex: 9999,
      }}
    >
      <div
        style={{
          backgroundColor: "white",
          padding: "20px",
          borderRadius: "10px",
          width: "400px",
          boxShadow: "0 4px 8px rgba(0,0,0,0.2)",
        }}
      >
        <h2 style={{ marginBottom: "15px" }}>Search Inventory by Code</h2>
        <input
          type="number"
          placeholder="Enter Code"
          value={code}
          onChange={(e) => setCode(e.target.value)}
          style={{
            width: "100%",
            border: "1px solid #ccc",
            padding: "10px",
            borderRadius: "6px",
            marginBottom: "12px",
            fontSize: "16px",
          }}
        />
        <div style={{ display: "flex", gap: "10px", marginBottom: "15px" }}>
          <button
            onClick={handleSearch}
            style={{
              flex: 1,
              backgroundColor: "#3b82f6",
              color: "white",
              border: "none",
              borderRadius: "6px",
              padding: "10px",
              cursor: "pointer",
              fontWeight: "bold",
              fontSize: "16px",
              transition: "background-color 0.3s ease",
            }}
            onMouseOver={e => (e.currentTarget.style.backgroundColor = "#2563eb")}
            onMouseOut={e => (e.currentTarget.style.backgroundColor = "#3b82f6")}
          >
            Search
          </button>
          <button
            onClick={onClose}
            style={{
              flex: 1,
              backgroundColor: "#d1d5db",
              color: "#374151",
              border: "none",
              borderRadius: "6px",
              padding: "10px",
              cursor: "pointer",
              fontWeight: "bold",
              fontSize: "16px",
              transition: "background-color 0.3s ease",
            }}
            onMouseOver={e => (e.currentTarget.style.backgroundColor = "#9ca3af")}
            onMouseOut={e => (e.currentTarget.style.backgroundColor = "#d1d5db")}
          >
            Close
          </button>
        </div>

        {item && (
          <div
            style={{
              marginTop: "10px",
              fontSize: "14px",
              lineHeight: "1.5",
              color: "#111827",
              backgroundColor: "#f3f4f6",
              padding: "12px",
              borderRadius: "6px",
              border: "1px solid #e5e7eb",
            }}
          >
            <p><strong>Name:</strong> {item.name}</p>
            <p><strong>Description:</strong> {item.description}</p>
            <p><strong>Unit Cost:</strong> ₹{item.unitCost}</p>
            <p><strong>Quantity:</strong> {item.quantity}</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default InventorySearchModal;
