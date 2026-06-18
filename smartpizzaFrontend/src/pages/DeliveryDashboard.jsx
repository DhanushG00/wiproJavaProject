import React, { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import {
  getDelivery,
  updateLocation,
  markDelivered,
} from "../services/deliveryService";

import "leaflet/dist/leaflet.css";
import { MapContainer, TileLayer, Marker } from "react-leaflet";
import L from "leaflet";
import { useLocation, useNavigate } from "react-router-dom";

// ✅ Fix leaflet marker icon
delete L.Icon.Default.prototype._getIconUrl;
L.Icon.Default.mergeOptions({
  iconUrl: "https://unpkg.com/leaflet@1.9.3/dist/images/marker-icon.png",
});

const DeliveryDashboard = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const orderId = location.state?.orderId;

  const [delivery, setDelivery] = useState(null);
  const [loading, setLoading] = useState(false);

  // ✅ Fetch delivery details
  const fetchDelivery = async () => {
    if (!orderId) return;

    setLoading(true);

    try {
      const res = await getDelivery(orderId);
      setDelivery(res.data);
    } catch (err) {
      console.error(err);
      setDelivery(null);
    } finally {
      setLoading(false);
    }
  };

  // ✅ Auto-fetch on load + every 5 sec
  useEffect(() => {
    if (!orderId) return;

    fetchDelivery();

    const interval = setInterval(fetchDelivery, 5000);
    return () => clearInterval(interval);
  }, [orderId]);

  // ✅ Update current location
  const handleUpdateLocation = () => {
    if (!orderId) return;

    navigator.geolocation.getCurrentPosition(
      async (position) => {
        const data = {
          orderId: Number(orderId),
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
        };

        await updateLocation(data);
        fetchDelivery();
      },
      () => alert("❌ Location permission denied")
    );
  };

  // ✅ Mark delivered
  const handleDelivered = async () => {
    if (!orderId) return;

    await markDelivered(orderId);
    fetchDelivery();

    alert("✅ Delivery completed");

    // ✅ Redirect back to list after delivery
    navigate("/delivery");
  };

  // ✅ Check if agent reached customer location
  const isReached = () => {
    if (!delivery) return false;

    const diff =
      Math.abs(delivery.latitude - 13.0827) +
      Math.abs(delivery.longitude - 80.2707);

    return diff < 0.01;
  };

  return (
    <>
      <Navbar />

      <div className="container mt-4">
        <h3>🚚 Delivery Tracking</h3>

        {/* ✅ No orderId case */}
        {!orderId && (
          <div className="alert alert-warning mt-3">
            No order selected ❌
          </div>
        )}

        {/* ✅ Loading */}
        {loading && <p>Loading delivery details...</p>}

        {/* ✅ Delivery Info */}
        {delivery && (
          <>
            <div className="card p-3 mt-3 shadow">
              <p><b>Order ID:</b> {delivery.orderId}</p>

              <p>
                <b>Status:</b>{" "}
                <span className={
                  delivery.status === "DELIVERED"
                    ? "text-success"
                    : delivery.status === "ACCEPTED"
                    ? "text-primary"
                    : "text-warning"
                }>
                  {delivery.status}
                </span>
              </p>

              <button
                className="btn btn-warning me-2"
                onClick={handleUpdateLocation}
              >
                Update Location 📍
              </button>

              {delivery.status !== "DELIVERED" && (
                <button
                  className="btn btn-success"
                  onClick={handleDelivered}
                >
                  Mark Delivered ✅
                </button>
              )}

              {isReached() && (
                <p className="text-success mt-2">
                  ✅ You have reached customer location
                </p>
              )}
            </div>

            {/* ✅ MAP */}
            {delivery.latitude && delivery.longitude && (
              <div className="mt-4" style={{ height: "400px" }}>
                <MapContainer
                  center={[delivery.latitude, delivery.longitude]}
                  zoom={13}
                  style={{ height: "100%", width: "100%" }}
                >
                  <TileLayer
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                  />

                  {/* ✅ Agent marker */}
                  <Marker
                    position={[delivery.latitude, delivery.longitude]}
                  />

                  {/* ✅ Customer marker (static for now) */}
                  <Marker position={[13.0827, 80.2707]} />

                </MapContainer>
              </div>
            )}
          </>
        )}

        {/* ✅ Empty state */}
        {!delivery && !loading && orderId && (
          <p className="mt-3 text-muted">
            No delivery data found ❌
          </p>
        )}
      </div>
    </>
  );
};

export default DeliveryDashboard;