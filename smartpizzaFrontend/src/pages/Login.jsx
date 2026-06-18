import React, { useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import "./login.css";

const Login = () => {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleLogin = async () => {

    // ✅ Validation
    if (username==null) {
      setError("Invalid user format");
      return;
    }

    if (password.length < 6) {
      setError("Password must be at least 6 characters");
      return;
    }

    try {

      const response = await api.post("/auth/login", {
        username,
        password
      });

      const token = response.data.token;
      localStorage.setItem("token", token);

      const roles = JSON.parse(atob(token.split(".")[1])).roles;
      localStorage.setItem("roles", roles);

      if (roles === "ADMIN") navigate("/admin");
      else if (roles === "DELIVERY") navigate("/delivery");
      else navigate("/customer/menu");

    } catch (err) {
      setError("Invalid credentials ❌");
    }
  };

  return (
    <div className="login-container">

      <motion.div
        className="login-card"
        initial={{ opacity: 0, y: 40 }}
        animate={{ opacity: 1, y: 0 }}
      >

        <h2 className="mb-3 text-center">
          <span className="logo1">Smart</span>
          <span className="logo2">Pizza</span>
        </h2>

        <h4 className="text-center mb-4">Login</h4>

        {error && <p className="error">{error}</p>}

        <input
          type="email"
          placeholder="Email"
          className="form-control mb-3"
          onChange={(e) => setUsername(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          className="form-control mb-3"
          onChange={(e) => setPassword(e.target.value)}
        />

        <button
          className="btn btn-warning w-100"
          onClick={handleLogin}
        >
          Login
        </button>

        <p className="mt-3 text-center">
          Don't have an account?{" "}
          <span
            className="link"
            onClick={() => navigate("/register")}
          >
            Register
          </span>
        </p>

      </motion.div>
    </div>
  );
};

export default Login;