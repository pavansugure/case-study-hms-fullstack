import React, { useState } from "react";
import { addInventory } from "../../api/inventoryApi";
import { toast } from "react-toastify";

const InventoryFormModal = ({ onClose }) => {
  const [item, setItem] = useState({
    code: "",
    name: "",
    description: "",
    unitCost: "",
    quantity: ""
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setItem((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const payload = {
        ...item,
        code: Number(item.code),
        unitCost: Number(item.unitCost),
        quantity: Number(item.quantity),
      };

      await addInventory(payload);
      toast.success("Inventory item added successfully!");
      onClose();
    } catch (error) {
      console.error("Error adding inventory:", error);
      toast.error("Failed to add inventory item.");
    }
  };

  return (
    <div className="modal-overlay">
      <div className="bg-white p-6 rounded-lg w-full max-w-lg shadow-md">
        <form onSubmit={handleSubmit} className="modal-form">
          <input
            name="code"
            type="number"
            placeholder="Enter item code"
            value={item.code}
            onChange={handleChange}
            required
            className="modal-input"
          />
          <input
            name="name"
            placeholder="Enter item name"
            value={item.name}
            onChange={handleChange}
            required
            className="modal-input"
          />
          <input
            name="description"
            placeholder="Enter description"
            value={item.description}
            onChange={handleChange}
            required
            className="modal-input"
          />
          <input
            name="unitCost"
            type="number"
            placeholder="Enter unit cost"
            value={item.unitCost}
            onChange={handleChange}
            required
            className="modal-input"
          />
          <input
            name="quantity"
            type="number"
            placeholder="Enter quantity"
            value={item.quantity}
            onChange={handleChange}
            required
            className="modal-input"
          />
          <div className="modal-buttons">
            <button type="button" onClick={onClose} className="modal-btn cancel">Cancel</button>
            <button type="submit" className="modal-btn submit">Add</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default InventoryFormModal;
