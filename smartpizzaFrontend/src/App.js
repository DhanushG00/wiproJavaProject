import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Unauthorized from "./pages/Unauthorized";

import AdminDashboard from "./pages/AdminDashboard";
import Tracking from "./pages/Tracking";
import DeliveryDashboard from "./pages/DeliveryDashboard";
import CustomerHome from "./pages/CustomerHome";
import Users from "./pages/Users";
import ProtectedRoute from "./components/ProtectedRoute";
import LandingPage from "./pages/LandingPage";
import Menu from "./pages/Menu";
import Cart from "./pages/Cart";
import Checkout from "./pages/Checkout";
import Payment from "./pages/Payment";
import Register from "./pages/Register";
import PizzaDetails from "./pages/PizzaDetails";
import DeliveryList from "./pages/DeliveryList";
const App = () => {

  return (
    <BrowserRouter>
      <Routes>

        {/*landing page*/}
        <Route path="/customer/landing" element={<LandingPage />} />
        {/* LOGIN */}
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        {/* ADMIN */}
        <Route
          path="/admin"
          element={
            <ProtectedRoute allowedRole="ADMIN">
              <AdminDashboard />
            </ProtectedRoute>
          }
          />
          
          <Route
          path="/admin/users"
          element={
          <ProtectedRoute allowedRole="ADMIN">
          <Users />
          </ProtectedRoute>
          }
          />

        

        {/* DELIVERY */}
        <Route path="/delivery" element={<DeliveryList />} />
        <Route
          path="/delivery/map"
          element={
            <ProtectedRoute allowedRole="DELIVERY">
              <DeliveryDashboard />
            </ProtectedRoute>
          }
        />

        {/* CUSTOMER */}
        <Route path="/customer/menu" element={<Menu />} />
        <Route path="/customer/cart" element={<Cart />} />
        <Route path="/customer/checkout" element={<Checkout />} />
        <Route path="/customer/payment" element={<Payment />} />
        <Route path="/customer/tracking" element={<Tracking />} />
        <Route path="/customer/details" element={<PizzaDetails />} />

        {/* UNAUTHORIZED */}
        <Route path="/unauthorized" element={<Unauthorized />} />

      </Routes>
    </BrowserRouter>
  );
};

export default App;