import { useSelector } from "react-redux";
import Navbar from "../components/Navbar";
import { useNavigate } from "react-router-dom";

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
              <span>₹ {item.price * item.quantity}</span>
            </div>
          ))}

          <hr />

          {/* ✅ BILL DETAILS */}
          <div className="d-flex justify-content-between">
            <span>Subtotal</span>
            <span>₹ {subtotal.toFixed(2)}</span>
          </div>

          <div className="d-flex justify-content-between">
            <span>GST (5%)</span>
            <span>₹ {gst.toFixed(2)}</span>
          </div>

          <div className="d-flex justify-content-between">
            <span>Delivery</span>
            <span>₹ {delivery}</span>
          </div>

          <hr />

          <div className="d-flex justify-content-between fw-bold">
            <span>Total</span>
            <span>₹ {total.toFixed(2)}</span>
          </div>

          {/* ✅ PAY BUTTON */}
          <button
            className="btn btn-success w-100 mt-3"
            onClick={handlePayment}
          >
            Proceed to Pay 💳
          </button>

        </div>
      </div>
    </>
  );
};

export default Checkout;
