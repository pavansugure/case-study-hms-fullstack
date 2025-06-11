import React, { useState } from "react";
import { addStaff } from "../../api/staffApi";
import { toast } from "react-toastify";
import "../../styles/StaffFormModal.css"

const StaffFormModal = ({ onClose }) => {
  const [staff, setStaff] = useState({
    code: "",
    employeeName: "",
    address: "",
    nic: "",
    salary: "",
    age: "",
    occupation: "",
    email: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setStaff((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await addStaff({
        ...staff,
        code: Number(staff.code),
        nic: Number(staff.nic),
        salary: Number(staff.salary),
        age: Number(staff.age),
      });
      toast.success("Staff member added successfully!");
      onClose();
    } catch (err) {
      toast.error("Error adding staff member");
      console.error(err);
    }
  };

  return (
    <div className="modal-overlay">
      <div style={{padding: "20px", borderRadius: "10px", width: "100%", marginLeft:"50px", marginTop:"45px" }}>  
        <form onSubmit={handleSubmit} className="modal-form">
          <input className="modal-input" name="code" type="number" placeholder="Code" value={staff.code} onChange={handleChange} required />
          <input className="modal-input" name="employeeName" placeholder="Employee Name" value={staff.employeeName} onChange={handleChange} required />
          <input className="modal-input" name="address" placeholder="Address" value={staff.address} onChange={handleChange} required />
          <input className="modal-input" name="nic" type="number" placeholder="NIC (10 digits)" value={staff.nic} onChange={handleChange} required />
          <input className="modal-input" name="salary" type="number" placeholder="Salary" value={staff.salary} onChange={handleChange} required />
          <input className="modal-input" name="age" type="number" placeholder="Age" value={staff.age} onChange={handleChange} required />
          <input className="modal-input" name="occupation" placeholder="Occupation" value={staff.occupation} onChange={handleChange} required />
          <input className="modal-input" name="email" type="email" placeholder="Email" value={staff.email} onChange={handleChange} required />
          <div className="modal-buttons">
            <button type="submit" className="modal-btn submit">Add</button>
            <button type="button" onClick={onClose} className="modal-btn cancel">Cancel</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default StaffFormModal;
