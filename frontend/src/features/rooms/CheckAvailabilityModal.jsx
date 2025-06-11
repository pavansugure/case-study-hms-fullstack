import React, { useState } from "react";
import { checkRoomAvailability } from "../../api/roomApi";

function CheckAvailabilityModal({ onClose }) {
  const [roomId, setRoomId] = useState("");
  const [checkInDate, setCheckInDate] = useState("");
  const [checkOutDate, setCheckOutDate] = useState("");
  const [result, setResult] = useState(null);

  const handleCheck = async () => {
    try {
      const isAvailable = await checkRoomAvailability(roomId, checkInDate, checkOutDate);
      setResult(isAvailable ? "Room is available ✅" : "Room is not available ❌");
    } catch (err) {
      setResult("Error checking availability");
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
          maxWidth: "450px",
          boxShadow: "0 10px 25px rgba(0,0,0,0.15)",
          fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
          textAlign: "center",
        }}
      >
        <h2
          style={{
            marginBottom: "25px",
            fontWeight: "700",
            fontSize: "1.5rem",
            color: "#2d3748",
          }}
        >
          Check Room Availability
        </h2>

        <input
          type="number"
          placeholder="Room ID"
          value={roomId}
          onChange={(e) => setRoomId(e.target.value)}
          style={{
            width: "100%",
            padding: "10px 12px",
            marginBottom: "15px",
            borderRadius: "6px",
            border: "1.5px solid #cbd5e0",
            fontSize: "1rem",
            outline: "none",
            transition: "border-color 0.3s ease",
          }}
          onFocus={(e) => (e.target.style.borderColor = "#3182ce")}
          onBlur={(e) => (e.target.style.borderColor = "#cbd5e0")}
        />

        <input
          type="date"
          value={checkInDate}
          onChange={(e) => setCheckInDate(e.target.value)}
          style={{
            width: "100%",
            padding: "10px 12px",
            marginBottom: "15px",
            borderRadius: "6px",
            border: "1.5px solid #cbd5e0",
            fontSize: "1rem",
            outline: "none",
            transition: "border-color 0.3s ease",
          }}
          onFocus={(e) => (e.target.style.borderColor = "#3182ce")}
          onBlur={(e) => (e.target.style.borderColor = "#cbd5e0")}
        />

        <input
          type="date"
          value={checkOutDate}
          onChange={(e) => setCheckOutDate(e.target.value)}
          style={{
            width: "100%",
            padding: "10px 12px",
            marginBottom: "20px",
            borderRadius: "6px",
            border: "1.5px solid #cbd5e0",
            fontSize: "1rem",
            outline: "none",
            transition: "border-color 0.3s ease",
          }}
          onFocus={(e) => (e.target.style.borderColor = "#3182ce")}
          onBlur={(e) => (e.target.style.borderColor = "#cbd5e0")}
        />

        <button
          onClick={handleCheck}
          style={{
            backgroundColor: "#3182ce",
            color: "white",
            padding: "10px 25px",
            border: "none",
            borderRadius: "6px",
            cursor: "pointer",
            fontWeight: "600",
            fontSize: "1rem",
            marginBottom: "15px",
            transition: "background-color 0.25s ease",
          }}
          onMouseEnter={(e) => (e.target.style.backgroundColor = "#2c5282")}
          onMouseLeave={(e) => (e.target.style.backgroundColor = "#3182ce")}
        >
          Check
        </button>

        {result && (
          <p
            style={{
              marginTop: "10px",
              fontWeight: "600",
              color: result.includes("Error") || result.includes("not") ? "#e53e3e" : "#38a169",
            }}
          >
            {result}
          </p>
        )}

        <button
          onClick={onClose}
          style={{
            marginTop: "20px",
            backgroundColor: "#e2e8f0",
            color: "#2d3748",
            padding: "8px 25px",
            borderRadius: "6px",
            border: "none",
            cursor: "pointer",
            fontWeight: "600",
            fontSize: "1rem",
            transition: "background-color 0.25s ease",
          }}
          onMouseEnter={(e) => (e.target.style.backgroundColor = "#cbd5e0")}
          onMouseLeave={(e) => (e.target.style.backgroundColor = "#e2e8f0")}
        >
          Close
        </button>
      </div>
    </div>
  );
}

export default CheckAvailabilityModal;
