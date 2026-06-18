import { useLocation } from "react-router-dom";
import Navbar from "../components/Navbar";
import api from "../services/api";
import { useDispatch } from "react-redux";
import { clearCart } from "../redux/cartSlice";
import { useNavigate } from "react-router-dom";
import { getUserId } from "../utils/auth";

const Payment = () => {

  const navigate=useNavigate();
  const location = useLocation();
  const orderData = location.state;
  const userId=getUserId();

  const dispatch = useDispatch();

  const handlePayment = async () => {

  const res = await api.post("/api/payment/create", {
    orderId: 1,
    amount: orderData.total
  });

  const options = {
    key: "rzp_test_T0m8aOmLRiKPxN",
    amount: orderData.total * 100,
    currency: "INR",
    name: "SmartPizza",

    handler: async function () {
      
      alert("✅ Payment Successful");

      await api.post(`/api/order/place/${userId}`, {
        userId: {userId},
        totalAmount: orderData.total,
        status: "PAID",
        items: orderData.items
      });

      navigate("/customer/tracking", { state: orderData });

      dispatch(clearCart());
    }  
  };

  const rzp = new window.Razorpay(options);
  rzp.open();
};

  return (
    <>
      <Navbar />

      <div className="container mt-4">

        <h3>💳 Payment</h3>

        <div className="card p-4 shadow">

          <p><b>Total Amount:</b> ₹ {orderData.total}</p>

          <button
            className="btn btn-danger w-100"
            onClick={handlePayment}
          >
            Pay Now
          </button>

        </div>
      </div>
    </>
  );
};

export default Payment;