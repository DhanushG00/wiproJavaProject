import { useSelector, useDispatch } from "react-redux";
import {
  increaseQty,
  decreaseQty,
  removeItem
} from "../redux/cartSlice";
import Navbar from "../components/Navbar";
import { useNavigate } from "react-router-dom";
import { getUserId } from "../utils/auth";
import api from "../services/api";

const Cart = () => {

  const cart = useSelector(state => state.cart);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const subtotal = cart.reduce(
    (sum, item) => sum + item.price * item.quantity, 0
  );

const handleCheckout = async () => {

  const userId = getUserId();

  if (!userId) {
    alert("User not logged in ");
    return;
  }

  try {

    for (let item of cart) {

      await api.post(
        `/api/cart/add/${userId}/${item.id}/${item.quantity}`
      );
    }

    navigate("/customer/checkout");

  } catch (error) {
    console.error(error);
    alert("Failed to save cart ❌");
  }
};

  return (
    <>
      <Navbar />

      <div className="container mt-4">

        <h3>🛒 Your Cart</h3>

        {cart.length === 0 && <p>No items in cart</p>}

        {cart.map(item => (
          <div key={item.id} className="card p-3 mb-3">

            <div className="d-flex justify-content-between">

              <div>
                <h5>{item.name}</h5>
                <p>₹ {item.price}</p>
              </div>

              <div className="text-end">

                {/* ✅ QUANTITY CONTROLS */}
                <button onClick={() => dispatch(decreaseQty(item.id))}>-</button>

                <span className="mx-2">{item.quantity}</span>

                <button onClick={() => dispatch(increaseQty(item.id))}>+</button>

                <br />

                <button
                  className="btn btn-danger mt-2"
                  onClick={() => dispatch(removeItem(item.id))}
                >
                  Remove
                </button>

              </div>

            </div>

          </div>
        ))}

        <hr />

        <h4>Subtotal: ₹ {subtotal}</h4>

        <button
          className="btn btn-success"
          onClick={handleCheckout}
        >
          Proceed to Checkout ✅
        </button>

      </div>
    </>
  );
};

export default Cart;