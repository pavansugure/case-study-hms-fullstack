import React, { useState } from "react";
import { addRoom } from "../../api/roomApi";
import { toast } from "react-toastify";

const RoomFormModal = ({ onClose }) => {
  const [room, setRoom] = useState({
    type: "",
    available: true,
    capacity: "",
    price: ""
  });

  const handleChange = (e) => {
    const { name, value, type: inputType, checked } = e.target;
    setRoom((prev) => ({
      ...prev,
      [name]: inputType === "checkbox" ? checked : value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        ...room,
        capacity: parseInt(room.capacity),
        price: parseFloat(room.price)
      };
      await addRoom(payload);
      toast.success("Room added successfully!");
      onClose();
    } catch (error) {
      toast.error("Error adding room");
      console.error(error);
    }
  };

  return (
    <div className="modal-overlay">
      <div> 
        <form onSubmit={handleSubmit} className="modal-form">
          <input
            name="type"
            placeholder="Room Type"
            value={room.type}
            onChange={handleChange}
            required
            className="modal-input"
          />
          <input
            name="capacity"
            type="number"
            placeholder="Capacity"
            value={room.capacity}
            onChange={handleChange}
            required
            className="modal-input"
          />
          <input
            name="price"
            type="number"
            step="0.01"
            placeholder="Price"
            value={room.price}
            onChange={handleChange}
            required
            className="modal-input"
          />
          <label className="modal-checkbox">
            <span style={{color:"Black"}}>Available</span>
            <input name="available" type="checkbox" checked={room.available} onChange={handleChange} />
            
          </label>

          <div className="modal-buttons">
            <button type="button" onClick={onClose} className="modal-btn cancel">Cancel</button>
            <button type="submit" className="modal-btn submit">Add</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default RoomFormModal;
