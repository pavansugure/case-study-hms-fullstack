import React, { useEffect, useState } from "react";
import {
  getAllRooms,
  getAllAvailableRooms,
  deleteRoom,
  updateRoom
} from "../../api/roomApi";
import { toast } from "react-toastify";

const RoomListModal = ({ onClose }) => {
  const [rooms, setRooms] = useState([]);
  const [editingRoom, setEditingRoom] = useState(null);
  const [showAvailableOnly, setShowAvailableOnly] = useState(false);

  const fetchRooms = async () => {
    try {
      const res = showAvailableOnly
        ? await getAllAvailableRooms()
        : await getAllRooms();
      setRooms(res.data);
    } catch (err) {
      toast.error("Failed to fetch rooms");
    }
  };

  useEffect(() => {
    fetchRooms();
  }, [showAvailableOnly]);

  const handleEdit = (room) => setEditingRoom({ ...room });

  const handleCancel = () => setEditingRoom(null);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setEditingRoom((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value
    }));
  };

const handleUpdate = async () => {
  const originalRoom = rooms.find((room) => room.id === editingRoom.id);

  const isEdited =
    JSON.stringify(originalRoom) !== JSON.stringify(editingRoom);

  if (!isEdited) {
    toast.info("No changes detected.");
    handleCancel();
    return;
  }

  try {
    const payload = {
      ...editingRoom,
      capacity: parseInt(editingRoom.capacity),
      price: parseFloat(editingRoom.price)
    };
    await updateRoom(payload);
    toast.success("Room updated");
    fetchRooms();
    handleCancel();
  } catch (err) {
    toast.error("Update failed");
  }
};

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure?")) {
      try {
        await deleteRoom(id);
        toast.success("Room deleted");
        fetchRooms();
      } catch (err) {
        toast.error("Failed to delete room");
      }
    }
  };

  const isEdited =
  editingRoom &&
  rooms.some(
    (r) =>
      r.id === editingRoom.id &&
      JSON.stringify(r) !== JSON.stringify(editingRoom)
  );

  return (
    <div className="listmodal-overlay">
      <div className="listmodal-container">
        <div className="filter-container">
          <label className="checkbox-label">
            <span>Show available rooms only</span>
            <input type="checkbox" checked={showAvailableOnly}
              onChange={() => setShowAvailableOnly((prev) => !prev)} />
          </label>
        </div>

        <table className="listmodal-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Type</th>
              <th>Capacity</th>
              <th>Price</th>
              <th>Available</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {rooms.map((room) => (
              <tr key={room.id}>
                <td>{room.id}</td>
                <td>
                  {editingRoom?.id === room.id ? (
                    <input
                      className="listmodal-input"
                      name="type"
                      value={editingRoom.type}
                      onChange={handleChange}
                    />
                  ) : (
                    room.type
                  )}
                </td>
                <td>
                  {editingRoom?.id === room.id ? (
                    <input
                      className="listmodal-input"
                      name="capacity"
                      type="number"
                      value={editingRoom.capacity}
                      onChange={handleChange}
                    />
                  ) : (
                    room.capacity
                  )}
                </td>
                <td>
                  {editingRoom?.id === room.id ? (
                    <input
                      className="listmodal-input"
                      name="price"
                      type="number"
                      step="0.01"
                      value={editingRoom.price}
                      onChange={handleChange}
                    />
                  ) : (
                    room.price
                  )}
                </td>
                <td>
                  {editingRoom?.id === room.id ? (
                    <label className="flex justify-center items-center gap-1">
                      <input
                        name="available"
                        type="checkbox"
                        checked={editingRoom.available}
                        onChange={handleChange}
                      />
                      <span>Available</span>
                    </label>
                  ) : room.available ? (
                    "Yes"
                  ) : (
                    "No"
                  )}
                </td>
                <td>
                  {editingRoom?.id === room.id ? (
                    <>
                      <button
                        onClick={handleUpdate}
                        className="listmodal-btn listmodal-btn-save"
                        disabled={!isEdited}
                      >
                        ✅ Save
                      </button>
                      <button
                        onClick={handleCancel}
                        className="listmodal-btn listmodal-btn-cancel"
                      >
                        ❌ Cancel
                      </button>
                    </>
                  ) : (
                    <>
                      <button
                        onClick={() => handleEdit(room)}
                        className="listmodal-btn listmodal-btn-edit"
                      >
                        ✏️ Edit
                      </button>
                      <button
                        onClick={() => handleDelete(room.id)}
                        className="listmodal-btn listmodal-btn-delete"
                      >
                       🗑️ Delete
                      </button>
                    </>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <button onClick={onClose} className="listmodal-close-btn">Close</button>
      </div>
    </div>
  );
};

export default RoomListModal;
