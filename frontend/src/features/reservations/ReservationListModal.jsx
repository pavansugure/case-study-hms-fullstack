import React, { useEffect, useState } from 'react';
import { getAllReservations, deleteReservation } from '../../api/reservationApi';
import { toast } from 'react-toastify';

const ReservationListModal = ({ onClose }) => {
  const [reservations, setReservations] = useState([]);

  const fetchAll = async () => {
    try {
      const res = await getAllReservations();
      setReservations(res.data);
    } catch (err) {
      toast.error("Failed to load reservations");
    }
  };

  const handleDelete = async (code) => {
    try {
      await deleteReservation(code);
      toast.success("Deleted successfully");
      fetchAll(); // refresh list
    } catch {
      toast.error("Failed to delete");
    }
  };

  useEffect(() => {
    fetchAll();
  }, []);

  return (
    <div className="listmodal-overlay">
      <div className="listmodal-container">
        <div className="overflow-auto">
          <table className="listmodal-table">
            <thead>
              <tr>
                <th>Code</th>
                <th>Guest ID</th>
                <th>Room ID</th>
                <th>Status</th>
                <th>Check-In</th>
                <th>Check-Out</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {reservations.map(r => (
                <tr key={r.code}>
                  <td>{r.code}</td>
                  <td>{r.guestId}</td>
                  <td>{r.roomId}</td>
                  <td>{r.status}</td>
                  <td>{r.checkInDate}</td>
                  <td>{r.checkOutDate}</td>
                  <td>
                    <button
                      className="listmodal-btn listmodal-btn-delete"
                      onClick={() => handleDelete(r.code)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
              {reservations.length === 0 && (
                <tr>
                  <td colSpan="7" className="text-center">No reservations found.</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
        <button className="listmodal-close-btn" onClick={onClose}>Close</button>
      </div>
    </div>
  );
};

export default ReservationListModal;
