import React, { useState } from "react";
import { getRoomById } from "../../api/roomApi";
import { toast } from "react-toastify";

const RoomSearchModal = ({ onClose }) => {
  const [id, setId] = useState("");
  const [room, setRoom] = useState(null);

  const handleSearch = async () => {
    try {
      const res = await getRoomById(id);
      setRoom(res.data);
    } catch (err) {
      toast.error("Room not found");
      setRoom(null);
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
          boxShadow: "0 8px 20px rgba(0,0,0,0.15)",
          fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
        }}
      >
        <h2
          style={{
            fontSize: "1.5rem",
            fontWeight: "700",
            marginBottom: "20px",
            color: "#2d3748",
          }}
        >
          Search Room by ID
        </h2>

        <input
          type="number"
          placeholder="Enter Room ID"
          value={id}
          onChange={(e) => setId(e.target.value)}
          style={{
            width: "100%",
            padding: "10px 12px",
            fontSize: "1rem",
            border: "1.5px solid #cbd5e0",
            borderRadius: "6px",
            marginBottom: "20px",
            outline: "none",
            transition: "border-color 0.3s ease",
          }}
          onFocus={(e) => (e.target.style.borderColor = "#3182ce")}
          onBlur={(e) => (e.target.style.borderColor = "#cbd5e0")}
        />

        <div style={{ display: "flex", justifyContent: "flex-end", gap: "12px" }}>
          <button
            onClick={handleSearch}
            style={{
              backgroundColor: "#3182ce",
              color: "white",
              padding: "8px 18px",
              border: "none",
              borderRadius: "6px",
              fontWeight: "600",
              cursor: "pointer",
              transition: "background-color 0.25s ease",
            }}
            onMouseEnter={(e) => (e.target.style.backgroundColor = "#2c5282")}
            onMouseLeave={(e) => (e.target.style.backgroundColor = "#3182ce")}
          >
            Search
          </button>
          <button
            onClick={onClose}
            style={{
              backgroundColor: "#e2e8f0",
              color: "#2d3748",
              padding: "8px 18px",
              border: "none",
              borderRadius: "6px",
              fontWeight: "600",
              cursor: "pointer",
              transition: "background-color 0.25s ease",
            }}
            onMouseEnter={(e) => (e.target.style.backgroundColor = "#cbd5e0")}
            onMouseLeave={(e) => (e.target.style.backgroundColor = "#e2e8f0")}
          >
            Close
          </button>
        </div>

        {room && (
          <div
            style={{
              marginTop: "25px",
              padding: "20px",
              backgroundColor: "#f7fafc",
              borderRadius: "8px",
              boxShadow: "inset 0 0 5px rgba(0,0,0,0.05)",
              color: "#2d3748",
              fontSize: "1rem",
              lineHeight: "1.5",
            }}
          >
            <p>
              <strong>ID:</strong> {room.id}
            </p>
            <p>
              <strong>Type:</strong> {room.type}
            </p>
            <p>
              <strong>Capacity:</strong> {room.capacity}
            </p>
            <p>
              <strong>Price:</strong> ₹{room.price}
            </p>
            <p>
              <strong>Available:</strong> {room.available ? "Yes" : "No"}
            </p>
          </div>
        )}
      </div>
    </div>
  );
};

export default RoomSearchModal;
