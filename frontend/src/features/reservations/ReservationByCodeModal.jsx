import React, { useState } from 'react';
import { getReservationByCode } from '../../api/reservationApi';
import { toast } from 'react-toastify';
import '../../styles/ReservationByCodeModal.css';

const ReservationByCodeModal = ({ onClose }) => {
  const [code, setCode] = useState('');
  const [data, setData] = useState(null);

  const handleFetch = async () => {
    try {
      const res = await getReservationByCode(code);
      setData(res.data);
    } catch (err) {
      toast.error("Not found.");
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-box">
        <h2 className="heading">Get Reservation by Code</h2>
        <input
          type="text"
          placeholder="Reservation Code"
          onChange={e => setCode(e.target.value)}
          className="input-field"
        />
        <div className="btn-container">
          <button onClick={handleFetch} className="btn-primary">Fetch</button>
          <button onClick={onClose} className="btn">Close</button>
        </div>
        {data && (
          <div className="reservation-details">
            <p><strong>Code:</strong> {data.code}</p>
            <p><strong>Guest ID:</strong> {data.guestId}</p>
            <p><strong>Room ID:</strong> {data.roomId}</p>
            <p><strong>Children:</strong> {data.numberOfChildren}</p>
            <p><strong>Adults:</strong> {data.numberOfAdults}</p>
            <p><strong>Check-In Date:</strong> {data.checkInDate}</p>
            <p><strong>Check-Out Date:</strong> {data.checkOutDate}</p>
            <p><strong>Status:</strong> {data.status}</p>
            <p><strong>Number of Nights:</strong> {data.numberOfNights}</p>
            <p><strong>Amount:</strong> ₹{data.amount}</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default ReservationByCodeModal;
