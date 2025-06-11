import React, { useEffect, useState } from "react";
import { getAllStaff, deleteStaff, updateStaff } from "../../api/staffApi";
import { toast } from "react-toastify";
import "../../styles/ListModal.css"

const StaffListModal = ({ onClose }) => {
  const [staffList, setStaffList] = useState([]);
  const [editingCode, setEditingCode] = useState(null);
  const [editedStaff, setEditedStaff] = useState({});

  const fetchStaff = () => {
    getAllStaff()
      .then((res) => setStaffList(res.data))
      .catch((err) => {
        toast.error("Failed to fetch staff");
        console.error(err);
      });
  };

  useEffect(() => {
    fetchStaff();
  }, []);

  const handleEditClick = (staff) => {
    setEditingCode(staff.employeeCode);
    setEditedStaff({ ...staff });
  };

const handleSaveEdit = () => {
  const originalStaff = staffList.find(
    (staff) => staff.employeeCode === editingCode
  );

  const isEdited =
    JSON.stringify(originalStaff) !== JSON.stringify(editedStaff);

  if (!isEdited) {
    toast.info("No changes made.");
    setEditingCode(null);
    return;
  }

  updateStaff(editedStaff)
    .then(() => {
      toast.success("Staff updated successfully");
      setEditingCode(null);
      fetchStaff();
    })
    .catch((err) => {
      toast.error("Update failed");
      console.error(err);
    });
};


  const handleDelete = (employeeCode) => {
    if (window.confirm("Are you sure you want to delete this staff member?")) {
      deleteStaff(employeeCode)
        .then(() => {
          toast.success("Staff deleted");
          fetchStaff();
        })
        .catch((err) => {
          toast.error("Failed to delete staff");
          console.error(err);
        });
    }
  };

  const handleChange = (e) => {
    setEditedStaff({ ...editedStaff, [e.target.name]: e.target.value });
  };

  return (
    <div className="listmodal-overlay">
      <div className="listmodal-container">

        {staffList.length === 0 ? (
          <p>No staff found.</p>
        ) : (
          <table className="listmodal-table">
            <thead>
              <tr>
                <th>Code</th>
                <th>Name</th>
                <th>Email</th>
                <th>NIC</th>
                <th>Salary</th>
                <th>Age</th>
                <th>Occupation</th>
                <th>Address</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {staffList.map((staff) => (
                <tr key={staff.employeeCode}>
                  <td>{staff.code}</td>

                  <td>
                    {editingCode === staff.employeeCode ? (
                      <input
                        type="text"
                        name="employeeName"
                        value={editedStaff.employeeName}
                        onChange={handleChange}
                        className="listmodal-input"
                      />
                    ) : (
                      staff.employeeName
                    )}
                  </td>

                  <td>
                    {editingCode === staff.employeeCode ? (
                      <input
                        type="email"
                        name="email"
                        value={editedStaff.email}
                        onChange={handleChange}
                        className="listmodal-input"
                      />
                    ) : (
                      staff.email
                    )}
                  </td>

                  <td>
                    {editingCode === staff.employeeCode ? (
                      <input
                        type="text"
                        name="nic"
                        value={editedStaff.nic}
                        onChange={handleChange}
                        className="listmodal-input"
                      />
                    ) : (
                      staff.nic
                    )}
                  </td>

                  <td>
                    {editingCode === staff.employeeCode ? (
                      <input
                        type="number"
                        name="salary"
                        value={editedStaff.salary}
                        onChange={handleChange}
                        className="listmodal-input"
                      />
                    ) : (
                      staff.salary
                    )}
                  </td>

                  <td>
                    {editingCode === staff.employeeCode ? (
                      <input
                        type="number"
                        name="age"
                        value={editedStaff.age}
                        onChange={handleChange}
                        className="listmodal-input"
                      />
                    ) : (
                      staff.age
                    )}
                  </td>

                  <td>
                    {editingCode === staff.employeeCode ? (
                      <input
                        type="text"
                        name="occupation"
                        value={editedStaff.occupation}
                        onChange={handleChange}
                        className="listmodal-input"
                      />
                    ) : (
                      staff.occupation
                    )}
                  </td>

                  <td>
                    {editingCode === staff.employeeCode ? (
                      <input
                        type="text"
                        name="address"
                        value={editedStaff.address}
                        onChange={handleChange}
                        className="listmodal-input"
                      />
                    ) : (
                      staff.address
                    )}
                  </td>

                  <td>
                    {editingCode === staff.employeeCode ? (
                      <>
                        <button
                          className="listmodal-btn listmodal-btn-save"
                          onClick={handleSaveEdit}
                          disabled={!isEdited}
                        >
                          ✅ Save
                        </button>
                      </>
                    ) : (
                      <>
                        <button
                          className="listmodal-btn listmodal-btn-edit"
                          onClick={() => handleEditClick(staff)}
                        >
                          ✏️ Edit
                        </button>
                        <button
                          className="listmodal-btn listmodal-close-btn"
                          onClick={() => handleDelete(staff.employeeCode)}
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

export default StaffListModal;
