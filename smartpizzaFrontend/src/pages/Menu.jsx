import { useEffect, useState } from "react";
import api from "../services/api";
import Navbar from "../components/Navbar";
import { useDispatch } from "react-redux";
import { addToCart } from "../redux/cartSlice";
import { motion } from "framer-motion";
import { FaStar } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import "./menu.css";

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


const Menu = () => {

  const [menu, setMenu] = useState([]);
  const dispatch = useDispatch();
  const navigate=useNavigate();
  
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

  useEffect(() => {
    fetchMenu();
  }, []);

  const fetchMenu = async () => {
    const res = await api.get("/api/menu/all");
    setMenu(res.data);
  };

  const handleAddToCart = (item) => {
    dispatch(addToCart(item));
  };

  return (
    <>
      <Navbar />

      <div className="container mt-4">

        <h2 className="mb-4">Explore Our Menu</h2>

        <div className="row">

          {menu.map((item) => (
            <div className="col-md-3 mb-4" key={item.id}>

              <motion.div
                whileHover={{ scale: 1.05 }}
                className="pizza-card shadow"
                onClick={() => navigate("/customer/details", { state: item })}
              >

                <img
                  src={`http://localhost:8088/static/images/${item.imageName}`}
                  
                  onError={(e) => {
                  e.target.src = imageMap[item.id];  // fallback to frontend
                    }}
                  className="pizza-img"
                  alt={item.name}
                  />

                <div className="p-3">

                  <h5>{item.name}</h5>

                  <p className="rating">
                    <FaStar /> 4.5
                  </p>

                  <p className="price">₹ {item.price}</p>

                  <button
                    className="btn btn-warning w-100"
                    onClick={() => handleAddToCart(item)}
                  >
                    Add to Cart
                  </button>

                </div>

              </motion.div>

            </div>
          ))}

        </div>

      </div>
    </>
  );
};

export default Menu;