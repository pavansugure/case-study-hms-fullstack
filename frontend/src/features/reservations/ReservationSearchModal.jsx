import React, { useState } from 'react';
import { searchReservations } from '../../api/reservationApi';
import { toast } from 'react-toastify';
import '../../styles/ReservationSearchModal.css';

const ReservationSearchModal = ({ onClose }) => {
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [results, setResults] = useState([]);

  const handleSearch = async () => {
    try {
      const res = await searchReservations(startDate, endDate);
      setResults(res.data);
    } catch (err) {
      toast.error("Search failed.");
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-box">
        <h2 className="heading">Search Reservations</h2>
        <input type="date" onChange={(e) => setStartDate(e.target.value)} className="input" />
        <input type="date" onChange={(e) => setEndDate(e.target.value)} className="input" />
        <div className="btn-container">
          <button onClick={handleSearch} className="btn-primary">Search</button>
          <button onClick={onClose} className="btn">Close</button>
        </div>
        {results.length > 0 && (
          <ul className="results-list">
            {results.map(r => (
              <li key={r.code}>{r.code} - {r.status} ({r.checkInDate} ➜ {r.checkOutDate})</li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

export default ReservationSearchModal;
