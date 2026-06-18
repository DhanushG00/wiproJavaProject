import { useLocation } from "react-router-dom";
import { useDispatch } from "react-redux";
import Navbar from "../components/Navbar";
import { addToCart } from "../redux/cartSlice";
import { useState } from "react";
import { FaStar } from "react-icons/fa";
import "./details.css";
import pizza1 from "../assets/images/pizza1.jpg";
import pizza2 from "../assets/images/pizza2.jpg";
import pizza3 from "../assets/images/pizza3.jpg";
import pizza4 from "../assets/images/pizza4.jpg";
import pizza5 from "../assets/images/pizza5.jpg";
import pizza6 from "../assets/images/pizza6.jpg";
import pizza7 from "../assets/images/pizza7.jpg";
import pizza8 from "../assets/images/pizza8.jpg";
import pizza9 from "../assets/images/pizza9.jpg";
import pizza10 from "../assets/images/pizza10.jpg";

const PizzaDetails = () => {
  const imageMap = {
  1: pizza1,
  2: pizza2,
  3: pizza3,
  4: pizza4,
  5: pizza5,
  6: pizza6,
  7: pizza7,
  8: pizza8,
  9: pizza9,
  10: pizza10
    };

  const location = useLocation();
  const item = location.state;

  const dispatch = useDispatch();

  const [qty, setQty] = useState(1);

  const increase = () => setQty(qty + 1);
  const decrease = () => {
    if (qty > 1) setQty(qty - 1);
  };

  const handleAdd = () => {
    for (let i = 0; i < qty; i++) {
      dispatch(addToCart(item));
    }
    alert("✅ Added to cart");
  };

  return (
    <>
      <Navbar />

      <div className="container mt-4">

        <div className="row details-card shadow">

          {/* ✅ IMAGE */}
          <div className="col-md-6">
            <img
            className="details-img"
            src={`http://localhost:8088/api/menu/image/${item.imageName}`}
            alt={item.name}
             onError={(e) => {
                  e.target.src = imageMap[item.id];  // fallback to frontend
                    }}
            />

          </div>

          {/* ✅ DETAILS */}
          <div className="col-md-6 p-4">

            <h2>{item.name}</h2>

            <p className="rating">
              <FaStar /> 4.5
            </p>

            <p className="desc">
              Delicious {item.name} made with fresh ingredients. 
             {item.description}
            </p>

            <h3>₹ {item.price}</h3>

            {/* ✅ QUANTITY */}
            <div className="qty-box mt-3">
              <button onClick={decrease}>-</button>
              <span>{qty}</span>
              <button onClick={increase}>+</button>
            </div>

            {/* ✅ ADD BUTTON */}
            <button
              className="btn btn-warning mt-4 w-100"
              onClick={handleAdd}
            >
              Add {qty} to Cart 🛒
            </button>

          </div>

        </div>

      </div>
    </>
  );
};

export default PizzaDetails;