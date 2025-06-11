import React, { useState } from "react";
import { createOrder } from "../../api/paymentApi";
import { toast } from "react-toastify";
import loadRazorpay from "../../utils/loadRazorpay";
import "../../styles/PaymentFormModal.css";

const PaymentFormModal = ({ onClose }) => {
  const [reservationCode, setReservationCode] = useState("");
  const [invoiceReady, setInvoiceReady] = useState(false);
  const [orderId, setOrderId] = useState("");

  const handlePayment = async () => {
    const isLoaded = await loadRazorpay();
    if (!isLoaded) {
      toast.error("Failed to load Razorpay SDK.");
      return;
    }

    try {
      const { data } = await createOrder(reservationCode);
      const options = {
        key: "rzp_test_wUaIBmPT9SjMYe",
        amount: data.amount,
        currency: "INR",
        name: "Hotel Booking",
        description: "Reservation Payment",
        order_id: data.orderId,
        handler: async function (response) {
          toast.success("Payment successful!");
          setOrderId(response.razorpay_order_id);
          setInvoiceReady(true);
        },
        theme: {
          color: "#1e40af",
        },
      };

      const rzp = new window.Razorpay(options);
      rzp.open();
    } catch (err) {
      toast.error("Payment failed!");
      console.error(err);
    }
  };

  const handleDownloadInvoice = async () => {
    try {
      const invoiceResponse = await fetch(`http://localhost:8091/pay/downloadInvoice/${orderId}`, {
        method: "GET",
      });

      if (!invoiceResponse.ok) {
        throw new Error("Failed to fetch invoice");
      }

      const invoiceBlob = await invoiceResponse.blob();
      const invoiceUrl = window.URL.createObjectURL(invoiceBlob);
      const link = document.createElement("a");
      link.href = invoiceUrl;
      link.download = "Invoice.pdf";
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (err) {
      console.error("Error downloading invoice:", err);
      toast.error("Could not fetch invoice. Try again later.");
    }
  };

  return (
    <div className="payment-modal-overlay">
      <div className="payment-modal-container">
        <h2 className="payment-modal-title">Reservation Payment</h2>

        {!invoiceReady ? (
          <>
            <input
              type="text"
              placeholder="Enter Reservation Code"
              value={reservationCode}
              onChange={(e) => setReservationCode(e.target.value)}
              className="payment-input"
              required
            />
            <div className="payment-btn-group">
              <button onClick={handlePayment} className="payment-btn-primary">
                Pay Now
              </button>
              <button onClick={onClose} className="payment-btn-secondary">
                Cancel
              </button>
            </div>
          </>
        ) : (
          <div className="payment-success">
            <p className="payment-success-message">Payment successful!</p>
            <button onClick={handleDownloadInvoice} className="payment-btn-primary">
              Download Invoice
            </button>
            <button onClick={onClose} className="payment-btn-secondary">
              Close
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default PaymentFormModal;
