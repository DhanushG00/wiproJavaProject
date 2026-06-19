import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import "../styles/Register.css";

const Register = () => {

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [roles, setRoles] = useState("CUSTOMER");

  const navigate = useNavigate();

  const handleRegister = async () => {

    // Validation
    if (!username==null||username<6) {
      alert("Invalid Username");
      return;
    }

    if (password.length < 6) {
      alert("Password must be at least 6 chars");
      return;
    }

    try {
      await api.post("/auth/register", {
        username: username,
        password: password,
        roles: roles  
      });

      alert(" Registration Successful");
      navigate("/");

    } catch (err) {
      alert("Registration failed");
    }
  };

  return (
    <div className="register-container">
      <div className="register-card">

        <h3 className="text-center mb-3">Register</h3>

        <input
          className="form-control mb-2"
          placeholder="Username"
          onChange={e => setUsername(e.target.value)}
        />

        <input
          type="password"
          className="form-control mb-2"
          placeholder="Password"
          onChange={e => setPassword(e.target.value)}
        />

        {/* Role Select */}
        <select
          className="form-control mb-3"
          onChange={e => setRoles(e.target.value)}
        >
          <option value="CUSTOMER">Customer</option>
          <option value="ADMIN">Admin</option>
          <option value="DELIVERY">Delivery</option>
        </select>

        <button
          className="btn btn-success w-100"
          onClick={handleRegister}
        >
          Register
        </button>

      </div>
    </div>
  );
};

export default Register;