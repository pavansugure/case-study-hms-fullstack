import React, { useState } from "react";
import { getStaffById } from "../../api/staffApi";
import { toast } from "react-toastify";

const StaffSearchModal = ({ onClose }) => {
  const [code, setCode] = useState("");
  const [staff, setStaff] = useState(null);

  const handleSearch = async () => {
    try {
      const res = await getStaffById(code);
      setStaff(res.data);
    } catch {
      toast.error("Staff member not found");
    }
  };

  return (
    <div
      style={{
        position: "fixed",
        inset: 0,
        backgroundColor: "rgba(0, 0, 0, 0.5)",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        zIndex: 50,
      }}
    >
      <div
        style={{
          backgroundColor: "white",
          padding: "1.5rem",
          borderRadius: "0.5rem",
          width: "100%",
          maxWidth: "32rem",
          boxShadow: "0 4px 6px rgba(0, 0, 0, 0.1)",
        }}
      >
        <h2 style={{ fontSize: "1.25rem", fontWeight: "bold", marginBottom: "1rem" }}>
          Search Staff by Code
        </h2>
        <input
          type="number"
          placeholder="Enter Staff Code"
          value={code}
          onChange={(e) => setCode(e.target.value)}
          style={{
            border: "1px solid #ccc",
            padding: "0.5rem",
            width: "100%",
            marginBottom: "1rem",
            borderRadius: "4px",
          }}
        />
        <div style={{ display: "flex", justifyContent: "flex-end", gap: "0.5rem", marginBottom: "1rem" }}>
          <button
            onClick={handleSearch}
            style={{
              backgroundColor: "#3B82F6",
              color: "white",
              padding: "0.5rem 1rem",
              border: "none",
              borderRadius: "4px",
              cursor: "pointer",
            }}
            onMouseOver={(e) => (e.target.style.backgroundColor = "#2563EB")}
            onMouseOut={(e) => (e.target.style.backgroundColor = "#3B82F6")}
          >
            Search
          </button>
          <button
            onClick={onClose}
            style={{
              backgroundColor: "#D1D5DB",
              padding: "0.5rem 1rem",
              border: "none",
              borderRadius: "4px",
              cursor: "pointer",
            }}
            onMouseOver={(e) => (e.target.style.backgroundColor = "#9CA3AF")}
            onMouseOut={(e) => (e.target.style.backgroundColor = "#D1D5DB")}
          >
            Close
          </button>
        </div>

        {staff && (
          <div style={{ fontSize: "0.875rem", marginTop: "0.5rem" }}>
            <p><strong>Name:</strong> {staff.employeeName}</p>
            <p><strong>Email:</strong> {staff.email}</p>
            <p><strong>NIC:</strong> {staff.nic}</p>
            <p><strong>Salary:</strong> {staff.salary}</p>
            <p><strong>Age:</strong> {staff.age}</p>
            <p><strong>Occupation:</strong> {staff.occupation}</p>
            <p><strong>Address:</strong> {staff.address}</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default StaffSearchModal;
