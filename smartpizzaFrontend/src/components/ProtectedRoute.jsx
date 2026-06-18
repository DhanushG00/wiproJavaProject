import React from "react";
import { Navigate } from "react-router-dom";
import { getRole, isAuthenticated } from "../utils/auth";

const ProtectedRoute = ({ children, allowedRole }) => {

  const role = getRole();

  if (!isAuthenticated()) {
    return <Navigate to="/" />;
  }

  if (allowedRole && role !== allowedRole) {
    return <Navigate to="/unauthorized" />;
  }

  return children;
};

export default ProtectedRoute;