import React, { useState } from 'react';
import { addReservation } from '../../api/reservationApi';
import { toast } from 'react-toastify';

const ReservationFormModal = ({ onClose }) => {
  const [form, setForm] = useState({
    code: '', guestId: '', roomId: '',
    numberOfChildren: 0, numberOfAdults: 1,
    checkInDate: '', checkOutDate: '',
    status: ''
  });

  const numericFields = ['guestId', 'roomId', 'numberOfChildren', 'numberOfAdults'];

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: numericFields.includes(name) ? parseInt(value) || 0 : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const dataToSend = {
      ...form,
      guestId: parseInt(form.guestId),    
      roomId: parseInt(form.roomId),
      numberOfChildren: parseInt(form.numberOfChildren),
      numberOfAdults: parseInt(form.numberOfAdults),
      checkInDate: form.checkInDate,
      checkOutDate: form.checkOutDate
    };

    try {
      await addReservation(dataToSend);
      toast.success("Reservation added successfully!");
      onClose();
    } catch (err) {
      console.error("Error adding reservation:", err.response?.data || err.message);
      toast.error("Failed to add reservation.");
    }
  };

  return (
    <div className="modal-overlay">
      <div> 
        <form onSubmit={handleSubmit} className="modal-form">
          {["Enter code", "Enter guest Id", "Enter room Id", "Enter status"].map((field) => (
            <input
              key={field}
              name={field}
              placeholder={field}
              value={form[field]}
              onChange={handleChange}
              required
              className="modal-input"
            />
          ))}
          <input
            name="numberOfChildren"
            type="number"
            min={0}
            placeholder="Enter Children"
            value={form.numberOfChildren}
            onChange={handleChange}
            className="modal-input"
          />
          <input
            name="numberOfAdults"
            type="number"
            min={1}
            placeholder="Adults"
            value={form.numberOfAdults}
            onChange={handleChange}
            className="modal-input"
          />
          <input
            name="checkInDate"
            type="date"
            value={form.checkInDate}
            onChange={handleChange}
            className="modal-input"
            required
          />
          <input
            name="checkOutDate"
            type="date"
            value={form.checkOutDate}
            onChange={handleChange}
            className="modal-input"
            required
          />
          <div className="modal-buttons">
            <button type="button" onClick={onClose} className="modal-btn cancel">Cancel</button>
            <button type="submit" className="modal-btn submit">Submit</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ReservationFormModal;
