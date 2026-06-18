import { useNavigate } from "react-router-dom";
import { FaUserCircle, FaShoppingCart } from "react-icons/fa";
import { useSelector } from "react-redux";
import { getRole } from "../utils/auth";
import "./navbar.css";

const Navbar = () => {

  const role = getRole();
  const navigate = useNavigate();
  const username = localStorage.getItem("username");

  const cart = useSelector(state => state.cart);

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <nav className="navbar custom-nav px-5">

      {/* ✅ LOGO */}
      <h3
        className="logo"
        onClick={() => navigate("/customer/menu")}
        style={{ cursor: "pointer" }}
      >
        <span className="smart">Smart</span>
        <span className="pizza">Pizza</span>
      </h3>

      {/* ✅ RIGHT SIDE */}
      <div className="d-flex align-items-center">

        {/* ✅ CART → ONLY FOR CUSTOMER */}
        {role?.toUpperCase() === "CUSTOMER" && (
          <div
            className="cart-icon"
            onClick={() => navigate("/customer/cart")}
            style={{ cursor: "pointer" }}
          >
            <FaShoppingCart size={22} />

            {cart.length > 0 && (
              <span className="cart-count">{cart.length}</span>
            )}
          </div>
        )}

        {/* ✅ USERNAME */}
        <span className="role mx-3">{username}</span>

        {/* ✅ PROFILE ICON */}
        <FaUserCircle size={30} className="profile-icon" />

        {/* ✅ LOGOUT */}
        <button className="btn btn-danger ms-3" onClick={logout}>
          Logout
        </button>

      </div>
    </nav>
  );
};

export default Navbar;