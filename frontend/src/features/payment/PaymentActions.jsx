import React, { useState } from "react";
import PaymentFormModal from "./PaymentFormModal";
import "../../styles/PaymentActions.css";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const PaymentActions = () => {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [reservationCode, setReservationCode] = useState("");

  const handleDownloadInvoice = async () => {
    try {
      if (!reservationCode) {
        toast.error("Enter a valid reservation code to fetch the invoice.");
        return;
      }

      const response = await fetch(`http://localhost:8091/pay/downloadInvoice/${reservationCode}`, {
        method: "GET",
      });

      if (!response.ok) {
        throw new Error("Failed to fetch invoice");
      }

      const blob = await response.blob();
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download = "Invoice.pdf";
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error("Error downloading invoice:", error);
      toast.error("Could not fetch invoice. Try again later.");
    }
  };

  return (
    <>
      <div className="payment-actions-container">
        <button
          onClick={() => setIsDropdownOpen(!isDropdownOpen)}
          className="payment-actions-toggle"
        >
          <span>💳 Payment Management</span>
          <span>{isDropdownOpen ? "▲" : "▼"}</span>
        </button>

        {isDropdownOpen && (
          <div className="payment-dropdown">
            <button
              className="payment-option"
              onClick={() => {
                setIsDropdownOpen(false); // Close dropdown
                setIsModalOpen(true);     // Open modal
              }}
            >
              💳 Make a Payment
            </button>
          </div>
        )}
      </div>

      {isModalOpen && <PaymentFormModal onClose={() => setIsModalOpen(false)} />}
    </>
  );
};

export default PaymentActions;
