import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import "./landing.css";

const LandingPage = () => {

  const navigate = useNavigate();

  return (
    <div className="landing">

      {/* ✅ NAVBAR */}
      <div className="nav">

        <h2 className="logo">
          <span>Smart</span>
          <span className="pizza">Pizza</span>
        </h2>

        <div>
          <button
            className="btn btn-outline-light me-2"
            onClick={() => navigate("/")}
          >
            Login
          </button>

          <button
            className="btn btn-warning"
            onClick={() => navigate("/register")}
          >
            Sign Up
          </button>
        </div>

      </div>

      {/* ✅ HERO CONTENT */}
      <motion.div
        className="hero"
        initial={{ opacity: 0, y: 40 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ duration: 1 }}
      >

        <h1>Life's Better with Pizza, Order Now!</h1>

        <p>Order your favorite pizza anytime, anywhere 🍕</p>

        <div className="hero-buttons">
          <button
            className="btn btn-warning"
            onClick={() => navigate("/customer/menu")}
          >
            Explore Menu
          </button>

          <button
            className="btn btn-outline-light"
            onClick={() => navigate("/")}
          >
            Order Now
          </button>
        </div>

      </motion.div>

    </div>
  );
};

export default LandingPage;