import React, { useState } from "react";
import { getGuestById } from "../../api/guestApi";
import { toast } from "react-toastify";

const GuestSearchModal = ({ onClose }) => {
  const [id, setId] = useState("");
  const [guest, setGuest] = useState(null);

  const handleSearch = async () => {
    try {
      const res = await getGuestById(id);
      setGuest(res.data);
    } catch {
      toast.error("Guest not found");
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
        zIndex: 1000,
        padding: "20px",
      }}
    >
      <div
        style={{
          backgroundColor: "#fff",
          padding: "30px",
          borderRadius: "10px",
          width: "100%",
          maxWidth: "500px",
          boxShadow: "0 4px 20px rgba(0, 0, 0, 0.2)",
          fontFamily: "Arial, sans-serif",
        }}
      >
        <h2
          style={{
            marginBottom: "20px",
            fontSize: "1.5rem",
            fontWeight: "bold",
            textAlign: "center",
            color: "#333",
          }}
        >
          Search Guest by ID
        </h2>

        <input
          type="number"
          placeholder="Enter Member Code"
          value={id}
          onChange={(e) => setId(e.target.value)}
          style={{
            width: "100%",
            padding: "10px",
            fontSize: "1rem",
            marginBottom: "15px",
            borderRadius: "6px",
            border: "1.5px solid #ccc",
            outline: "none",
            boxSizing: "border-box",
          }}
        />

        <div style={{ display: "flex", justifyContent: "space-between", marginBottom: "20px" }}>
          <button
            onClick={handleSearch}
            style={{
              padding: "10px 16px",
              backgroundColor: "#3182ce",
              color: "#fff",
              border: "none",
              borderRadius: "6px",
              cursor: "pointer",
              fontWeight: "600",
            }}
          >
            Search
          </button>
          <button
            onClick={onClose}
            style={{
              padding: "10px 16px",
              backgroundColor: "#e53e3e",
              color: "#fff",
              border: "none",
              borderRadius: "6px",
              cursor: "pointer",
              fontWeight: "600",
            }}
          >
            Close
          </button>
        </div>

        {guest && (
          <div style={{ fontSize: "0.95rem", lineHeight: "1.6", color: "#444" }}>
            <p><strong>Name:</strong> {guest.guestName}</p>
            <p><strong>Email:</strong> {guest.email}</p>
            <p><strong>Company:</strong> {guest.company}</p>
            <p><strong>Phone:</strong> {guest.phoneNo}</p>
            <p><strong>Gender:</strong> {guest.gender}</p>
            <p><strong>Address:</strong> {guest.address}</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default GuestSearchModal;
