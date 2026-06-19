import { useSelector } from "react-redux";
import Navbar from "../components/Navbar";
import { useNavigate } from "react-router-dom";
import jsPDF from "jspdf";

const Checkout = () => {

  const cart = useSelector(state => state.cart);
  const navigate = useNavigate();

  const subtotal = cart.reduce(
    (sum, item) => sum + item.price * item.quantity, 0
  );

  const gst = subtotal * 0.05;     // ✅ 5% GST
  const delivery = 50;             // ✅ fixed delivery

  const total = subtotal + gst + delivery;

  const handlePayment = () => {
    const orderData = {
      items: cart,
      subtotal,
      gst,
      delivery,
      total
    };

    navigate("/customer/payment", { state: orderData });
  };

  const handleDownloadPDF = () => {
  const doc = new jsPDF();

  let y = 20;

  // ✅ TITLE
  doc.setFontSize(20);
  doc.text("SmartPizza Invoice", 105, y, { align: "center" });

  y += 15;

  doc.setFontSize(12);

  // ✅ ITEM HEADER
  doc.text("Item", 10, y);
  doc.text("Qty", 120, y);
  doc.text("Amount", 170, y, { align: "right" });

  y += 5;
  doc.line(10, y, 200, y);

  y += 10;

  // ✅ ITEMS LOOP
  cart.forEach((item) => {
    doc.text(item.name, 10, y);
    doc.text(String(item.quantity), 120, y);
    doc.text(`Rs. ${item.price * item.quantity}`, 200, y, { align: "right" });

    y += 10;
  });

  // ✅ LINE
  y += 5;
  doc.line(10, y, 200, y);

  y += 10;

  // ✅ BILL DETAILS
  doc.text("Subtotal", 10, y);
  doc.text(`Rs. ${subtotal.toFixed(2)}`, 200, y, { align: "right" });

  y += 8;
  doc.text("GST (5%)", 10, y);
  doc.text(`Rs. ${gst.toFixed(2)}`, 200, y, { align: "right" });

  y += 8;
  doc.text("Delivery", 10, y);
  doc.text(`Rs. ${delivery}`, 200, y, { align: "right" });

  y += 10;

  doc.line(10, y, 200, y);

  y += 10;

  // ✅ TOTAL (BOLD)
  doc.setFontSize(14);
  doc.text("Total", 10, y);
  doc.text(`Rs. ${total.toFixed(2)}`, 200, y, { align: "right" });

  y += 15;

  // ✅ FOOTER
  doc.setFontSize(10);
  doc.text("Thank you for ordering with SmartPizza!", 105, y, { align: "center" });

  // ✅ SAVE
  doc.save("invoice.pdf");
};


  return (
    <>
      <Navbar />

      <div className="container mt-4">

        <h3>🧾 Invoice / Checkout</h3>

        <div className="card p-4 shadow">

          {/* ✅ ITEM LIST */}
          {cart.map(item => (
            <div key={item.id} className="d-flex justify-content-between mb-2">
              <span>{item.name} x {item.quantity}</span>
              <span>Rs. {item.price * item.quantity}</span>
            </div>
          ))}

          <hr />

          {/* ✅ BILL DETAILS */}
          <div className="d-flex justify-content-between">
            <span>Subtotal</span>
            <span>Rs. {subtotal.toFixed(2)}</span>
          </div>

          <div className="d-flex justify-content-between">
            <span>GST (5%)</span>
            <span>Rs. {gst.toFixed(2)}</span>
          </div>

          <div className="d-flex justify-content-between">
            <span>Delivery</span>
            <span>Rs. {delivery}</span>
          </div>

          <hr />

          <div className="d-flex justify-content-between fw-bold">
            <span>Total</span>
            <span>Rs. {total.toFixed(2)}</span>
          </div>

          {/* ✅ PAY BUTTON */}
          <button
            className="btn btn-success w-100 mt-3"
            onClick={handlePayment}
          >
            Proceed to Pay 💳
          </button>

          <button
            className="btn btn-success w-100 mt-3"
            onClick={handleDownloadPDF}
          >
            Download Invoice 📄
          </button>

        </div>
      </div>
    </>
  );
};

export default Checkout;
