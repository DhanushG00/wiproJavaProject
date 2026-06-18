import React, { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import {
  getRevenueAnalytics,
  getTopPizzas,
  getDeliveryPerformance,
  getCustomerTrends,
} from "../services/adminService";
import { getOrders } from "../services/adminService";
import { Bar, Pie } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, ArcElement } from "chart.js";

ChartJS.register(CategoryScale, LinearScale, BarElement, ArcElement);

const AdminDashboard = () => {

  const [orders, setOrders] = useState([]);
  const [revenue, setRevenue] = useState(0);
  const [pizzaData, setPizzaData] = useState({});
  const [deliveryData, setDeliveryData] = useState({});
  const [trendsData, setTrendsData] = useState({});

  useEffect(() => {
    loadData();
  }, []);

 const loadData = async () => {
  try {
    const revenueRes = await getRevenueAnalytics();
    const topPizzaRes = await getTopPizzas();
    const deliveryRes = await getDeliveryPerformance();
    const trendsRes = await getCustomerTrends();
    const ordersRes = await getOrders(); 

    setRevenue(revenueRes.data.totalRevenue);
    setPizzaData(topPizzaRes.data);
    setDeliveryData(deliveryRes.data);
    setTrendsData(trendsRes.data);

    setOrders(ordersRes.data); 

  } catch (err) {
    console.error("Error loading admin data", err);
  }
};

  //  Chart configs

  const pizzaChart = {
    labels: Object.keys(pizzaData),
    datasets: [
      {
        label: "Top Selling Pizzas",
        data: Object.values(pizzaData),
        backgroundColor: "orange",
      },
    ],
  };

  const deliveryChart = {
    labels: Object.keys(deliveryData),
    datasets: [
      {
        label: "Deliveries",
        data: Object.values(deliveryData),
        backgroundColor: ["green", "blue", "red"],
      },
    ],
  };

  const trendsChart = {
    labels: Object.keys(trendsData),
    datasets: [
      {
        label: "Customer Trends",
        data: Object.values(trendsData),
        backgroundColor: "purple",
      },
    ],
  };

  return (
    <>
      <Navbar />

      <div className="container mt-4">
        <h2>AdminDashboard</h2>
        {/* Revenue Card */}
        <div className="row mt-4">
        <div className="col-md-4">
        <div className="card p-3 mb-4 shadow">
          <h4>Total Revenue 💰</h4>
          <h3>₹ {revenue}</h3>
        </div>
        </div>
        </div>

        {/*  Charts Row */}
        <div className="row">

          {/* ✅ Orders List */}
<div className="card p-3 shadow mt-4">
  <h5>All Orders</h5>

  <table className="table table-bordered mt-3">
    <thead>
      <tr>
        <th>Order ID</th>
        <th>User ID</th>
        <th>Items</th>
        <th>Total</th>
        <th>Status</th>
      </tr>
    </thead>

    <tbody>
      {orders.map((order) => (
        <tr key={order.id}>
          <td>{order.id}</td>
          <td>{order.userId}</td>
          <td>
  {order.items.map(item => (
    <div key={item.id}>
      {item.menu.name} x {item.quantity}
    </div>
  ))}
</td>
          <td>₹ {order.totalPrice}</td>
          <td>
            <span className={`badge ${
              order.status === "DELIVERED"
                ? "bg-success"
                : order.status === "PLACED"
                ? "bg-warning"
                : "bg-primary"
            }`}>
              {order.status}
            </span>
          </td>
        </tr>
      ))}
    </tbody>
  </table>
</div>
<br></br>
<br></br>
<h3>          </h3>

          {/* Top Pizza */}
          <div className="col-md-6 mb-4">
            <div className="card p-3 shadow">
              <h5>Top Selling Pizzas</h5>
              <Bar data={pizzaChart} />
            </div>
          </div>

          {/* Delivery Performance */}
          <div className="col-md-6 mb-4">
            <div className="card p-3 shadow">
              <h5>Delivery Performance</h5>
              <Pie data={deliveryChart} />
            </div>
          </div>

        </div>

        {/* Customer Trends */}
        <div className="card p-3 shadow mt-3">
          <h5>Customer Trends</h5>
          <Bar data={trendsChart} />
        </div>

      </div>
    </>
  );
};

export default AdminDashboard;