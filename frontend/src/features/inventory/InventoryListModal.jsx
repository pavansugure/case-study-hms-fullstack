import React, { useEffect, useState } from "react";
import {
  getAllInventory,
  updateInventory,
  deleteInventory,
} from "../../api/inventoryApi";
import { toast } from "react-toastify";

const InventoryListModal = ({ onClose }) => {
  const [inventory, setInventory] = useState([]);
  const [editItem, setEditItem] = useState(null);
  const [originalItem, setOriginalItem] = useState(null);

  const fetchInventory = () => {
    getAllInventory()
      .then((res) => setInventory(res.data))
      .catch((err) => {
        toast.error("Failed to fetch inventory");
        console.error(err);
      });
  };

  useEffect(() => {
    fetchInventory();
  }, []);

  const handleEdit = (item) => {
    setEditItem({ ...item });
    setOriginalItem({ ...item });
  };

  const handleUpdate = () => {
    if (!editItem) return;

    updateInventory(editItem)
      .then(() => {
        toast.success("Inventory updated successfully");
        setEditItem(null);
        setOriginalItem(null);
        fetchInventory();
      })
      .catch((err) => {
        toast.error("Failed to update inventory");
        console.error(err);
      });
  };

  const handleDelete = (code) => {
    if (window.confirm("Are you sure you want to delete this item?")) {
      deleteInventory(code)
        .then(() => {
          toast.success("Item deleted");
          fetchInventory();
        })
        .catch((err) => {
          toast.error("Failed to delete item");
          console.error(err);
        });
    }
  };

  const isEdited =
    JSON.stringify(originalItem) !== JSON.stringify(editItem);

  return (
    <div className="listmodal-overlay">
      <div className="listmodal-container">
        {inventory.length === 0 ? (
          <p>No inventory items found.</p>
        ) : (
          <table className="listmodal-table">
            <thead>
              <tr>
                <th>Item Code</th>
                <th>Name</th>
                <th>Category</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {inventory.map((item) => (
                <tr key={item.code}>
                  <td>{item.code}</td>
                  <td>
                    {editItem?.code === item.code ? (
                      <input
                        className="listmodal-input"
                        value={editItem.name}
                        onChange={(e) =>
                          setEditItem({ ...editItem, name: e.target.value })
                        }
                      />
                    ) : (
                      item.name
                    )}
                  </td>
                  <td>
                    {editItem?.code === item.code ? (
                      <input
                        className="listmodal-input"
                        value={editItem.description}
                        onChange={(e) =>
                          setEditItem({ ...editItem, description: e.target.value })
                        }
                      />
                    ) : (
                      item.description
                    )}
                  </td>
                  <td>
                    {editItem?.code === item.code ? (
                      <input
                        className="listmodal-input"
                        type="number"
                        value={editItem.quantity}
                        onChange={(e) =>
                          setEditItem({ ...editItem, quantity: parseInt(e.target.value) })
                        }
                      />
                    ) : (
                      item.quantity
                    )}
                  </td>
                  <td>
                    {editItem?.code === item.code ? (
                      <input
                        className="listmodal-input"
                        type="number"
                        value={editItem.unitCost}
                        onChange={(e) =>
                          setEditItem({ ...editItem, unitCost: parseFloat(e.target.value) })
                        }
                      />
                    ) : (
                      item.unitCost
                    )}
                  </td>
                  <td>
                    {editItem?.code === item.code ? (
                      <>
                        <button
                          className="listmodal-btn listmodal-btn-save"
                          onClick={handleUpdate}
                          disabled={!isEdited}
                        >
                          ✅ Save
                        </button>
                        <button
                          className="listmodal-btn listmodal-btn-cancel"
                          onClick={() => {
                            setEditItem(null);
                            setOriginalItem(null);
                          }}
                        >
                          ❌ Cancel
                        </button>
                      </>
                    ) : (
                      <>
                        <button
                          className="listmodal-btn listmodal-btn-edit"
                          onClick={() => handleEdit(item)}
                        >
                          ✏️ Edit
                        </button>
                        <button
                          className="listmodal-btn listmodal-btn-delete"
                          onClick={() => handleDelete(item.code)}
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

export default InventoryListModal;
