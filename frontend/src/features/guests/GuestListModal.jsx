import React, { useEffect, useState } from "react";
import { getAllGuests, deleteGuest, updateGuest } from "../../api/guestApi";
import { toast } from "react-toastify";
import "../../styles/ListModal.css";

const GuestListModal = ({ onClose }) => {
  const [guests, setGuests] = useState([]);
  const [editingGuestCode, setEditingGuestCode] = useState(null);
  const [editedGuest, setEditedGuest] = useState({});

  const fetchGuests = () => {
    getAllGuests()
      .then((res) => setGuests(res.data))
      .catch((err) => {
        toast.error("Failed to fetch guests");
        console.error(err);
      });
  };

  useEffect(() => {
    fetchGuests();
  }, []);

  const handleEditClick = (guest) => {
    setEditingGuestCode(guest.memberCode);
    setEditedGuest({ ...guest });
  };

  const handleCancelEdit = () => {
    setEditingGuestCode(null);
    setEditedGuest({});
  };

  const handleSaveEdit = () => {
    updateGuest(editedGuest)
      .then(() => {
        toast.success("Guest updated successfully");
        fetchGuests();
        handleCancelEdit();
      })
      .catch((err) => {
        toast.error("Update failed");
        console.error(err);
      });
  };

  const handleDelete = (memberCode) => {
    if (window.confirm("Are you sure you want to delete this guest?")) {
      deleteGuest(memberCode)
        .then(() => {
          toast.success("Guest deleted");
          fetchGuests();
        })
        .catch((err) => {
          toast.error("Failed to delete guest");
          console.error(err);
        });
    }
  };

  const handleChange = (e) => {
    setEditedGuest({ ...editedGuest, [e.target.name]: e.target.value });
  };

  const originalGuest = guests.find((g) => g.memberCode === editingGuestCode);
  const isEdited = JSON.stringify(originalGuest) !== JSON.stringify(editedGuest);

  return (
    <div className="listmodal-overlay">
      <div className="listmodal-container">

        {guests.length === 0 ? (
          <p>No guests found.</p>
        ) : (
          <table className="listmodal-table">
            <thead>
              <tr>
                <th>Member Code</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Gender</th>
                <th>Company</th>
                <th>Address</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {guests.map((guest) => (
                <tr key={guest.memberCode}>
                  <td>{guest.memberCode}</td>

                  <td>
                    {editingGuestCode === guest.memberCode ? (
                      <input
                        type="text"
                        name="guestName"
                        value={editedGuest.guestName}
                        onChange={handleChange}
                        className="listmodal-input"
                      />
                    ) : (
                      guest.guestName
                    )}
                  </td>

                  <td>
                    {editingGuestCode === guest.memberCode ? (
                      <input
                        type="email"
                        name="email"
                        value={editedGuest.email}
                        onChange={handleChange}
                        className="listmodal-input"
                      />
                    ) : (
                      guest.email
                    )}
                  </td>

                  <td>
                    {editingGuestCode === guest.memberCode ? (
                      <input
                        type="number"
                        name="phoneNo"
                        value={editedGuest.phoneNo}
                        onChange={handleChange}
                        className="listmodal-input"
                      />
                    ) : (
                      guest.phoneNo
                    )}
                  </td>

                  <td>
                    {editingGuestCode === guest.memberCode ? (
                      <select
                        name="gender"
                        value={editedGuest.gender}
                        onChange={handleChange}
                        className="listmodal-input"
                      >
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                      </select>
                    ) : (
                      guest.gender
                    )}
                  </td>

                  <td>
                    {editingGuestCode === guest.memberCode ? (
                      <input
                        type="text"
                        name="company"
                        value={editedGuest.company}
                        onChange={handleChange}
                        className="listmodal-input"
                      />
                    ) : (
                      guest.company
                    )}
                  </td>

                  <td>
                    {editingGuestCode === guest.memberCode ? (
                      <input
                        type="text"
                        name="address"
                        value={editedGuest.address}
                        onChange={handleChange}
                        className="listmodal-input"
                      />
                    ) : (
                      guest.address
                    )}
                  </td>

                  <td>
                    {editingGuestCode === guest.memberCode ? (
                      <>
                        <button
                          className="listmodal-btn listmodal-btn-save"
                          onClick={handleSaveEdit}
                          disabled={!isEdited}
                        >
                          ✅ Save
                        </button>
                        <button
                          className="listmodal-btn listmodal-btn-cancel"
                          onClick={handleCancelEdit}
                        >
                          ❌ Cancel
                        </button>
                      </>
                    ) : (
                      <>
                        <button
                          className="listmodal-btn listmodal-btn-edit"
                          onClick={() => handleEditClick(guest)}
                        >
                          ✏️ Edit
                        </button>
                        <button
                          className="listmodal-btn listmodal-btn-delete"
                          onClick={() => handleDelete(guest.memberCode)}
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
        )}
        <button className="listmodal-close-btn" onClick={onClose}>
          Close
        </button>
      </div>
    </div>
  );
};

export default GuestListModal;