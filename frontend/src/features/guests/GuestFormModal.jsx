import React, { useState } from "react";
import { addGuest } from "../../api/guestApi";
import { toast } from "react-toastify";

import "../../styles/FormModal.css"; // Import the updated styles

const GuestFormModal = ({ onClose }) => {
  const [guest, setGuest] = useState({
    memberCode: "",
    guestName: "",
    phoneNo: "",
    company: "",
    email: "",
    gender: "",
    address: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setGuest((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await addGuest({ ...guest, memberCode: Number(guest.memberCode), phoneNo: Number(guest.phoneNo) });
      toast.success("Guest added successfully!");
      onClose();
    } catch (err) {
      toast.error("Error adding guest");
      console.error(err);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <form onSubmit={handleSubmit} className="modal-form">
          <input className="modal-input" name="memberCode" type="number" placeholder="Enter member code" value={guest.memberCode} onChange={handleChange} required />
          <input className="modal-input" name="guestName" placeholder="Enter guest name" value={guest.guestName} onChange={handleChange} required />
          <input className="modal-input" name="phoneNo" type="number" placeholder="Enter phone number" value={guest.phoneNo} onChange={handleChange} required />
          <input className="modal-input" name="company" placeholder="Enter company name" value={guest.company} onChange={handleChange} required />
          <input className="modal-input" name="email" type="email" placeholder="Enter email" value={guest.email} onChange={handleChange} required />
          <input className="modal-input" name="gender" placeholder="Enter gender" value={guest.gender} onChange={handleChange} required />
          <input className="modal-input" name="address" placeholder="Enter address" value={guest.address} onChange={handleChange} required />
          <div className="modal-buttons">
            <button type="button" onClick={onClose} className="modal-btn cancel">Cancel</button>
            <button type="submit" className="modal-btn submit">Add</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default GuestFormModal;
